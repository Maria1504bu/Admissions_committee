package dao.implementation;

import dao.AlreadyExistException;
import dao.ColumnLabel;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.FacultyDao;
import models.Faculty;
import models.Subject;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class FacultyDaoImpl implements FacultyDao {
    private static final Logger LOG = Logger.getLogger(FacultyDaoImpl.class);

    private static final String GET_FACULTY_BY_ID_QUERY = "SELECT f.id, f.budget_places, f.total_places, GROUP_CONCAT(fl.name SEPARATOR '; ') AS name " +
            "FROM faculties f " +
            "INNER JOIN faculties_languages fl ON f.id = fl.faculty_id " +
            //"INNER JOIN languages l ON l.id = fl.languages_id " +
            "WHERE f.id = ?";

    private static final String GET_ALL_FACULTIES_ORDER_BY_QUERY = "SELECT f.id, f.budget_places, f.total_places, fl.name " +
            "FROM faculties f " +
            "INNER JOIN faculties_languages fl ON f.id = fl.faculty_id " +
            "INNER JOIN languages l ON l.id = fl.language_id WHERE l.lang_code = ? " +
            "ORDER BY ";
    private final static String INSERT_FACULTY_QUERY = "INSERT INTO faculties (budget_places, total_places) VALUES (?, ?);";
    private static final String INSERT_FACULTY_LANG_QUERY = "INSERT INTO faculties_languages (faculty_id, language_id, name) " +
            "VALUES " +
            "((SELECT MAX(id) FROM faculties), (SELECT id FROM languages WHERE lang_code = 'en'), ?), " +
            "((SELECT MAX(id) FROM faculties), (SELECT id FROM languages WHERE lang_code = 'uk'), ?) ";
    private static final String INSERT_SUBJECTS_TO_FACULTY =
            "INSERT INTO faculties_subjects (faculty_id, subject_id) VALUES ((SELECT MAX(id) FROM faculties), ?);";


    private static final String UPDATE_FACULTY_QUERY =
            "UPDATE faculties SET budget_places = ?, total_places = ? " +
                    "WHERE id = ?;";

    private static final String UPDATE_FACULTY_LANG_SET_QUERY =
            "UPDATE faculties_languages SET name = ? " +
                    "WHERE faculty_id = ? AND language_id = ?;";

    private static final String DELETE_FACULTY_QUERY =
            "DELETE FROM faculties WHERE id = ?;";
    private final DataSource dataSource;

    public FacultyDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Faculty getById(int id) throws DaoException {
        LOG.debug("Start getting faculty by id ==> " + id);
        Faculty faculty = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(GET_FACULTY_BY_ID_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);

            try (ResultSet resultSet = prStatement.executeQuery()) {
                FacultyMapper mapper = new FacultyMapper();
                while (resultSet.next()) {
                    faculty = mapper.mapEntity(resultSet);
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find faculty with id ==>" + id, e);
        }
        LOG.debug("Searched faculty ==>" + faculty);
        return faculty;
    }

    @Override
    public List<Faculty> getAllOrderBy(String lang, String orderBy) throws DaoException {
        List<Faculty> faculties = new ArrayList<>();
        LOG.debug("Start to find all faculties with name by language ==> " + lang + " and sort it by ==> " + orderBy);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(GET_ALL_FACULTIES_ORDER_BY_QUERY + orderBy)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, lang);
            LOG.trace("Set language to prepareStatement ==> " + lang);
            ResultSet resultSet = prStatement.executeQuery();

            FacultyMapper mapper = new FacultyMapper();
            while (resultSet.next()) {
                faculties.add(mapper.mapEntity(resultSet));
            }

            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find all faculties", e);
        }
        LOG.debug("Found faculties ==>" + faculties);
        return faculties;
    }

    @Override
    public void save(Faculty faculty) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving faculty ==> " + faculty);
        try (Connection connection = getConnection();
             PreparedStatement facultyStatement = connection.prepareStatement(INSERT_FACULTY_QUERY);
             PreparedStatement langStatement = connection.prepareStatement(INSERT_FACULTY_LANG_QUERY);
             ) {
            LOG.trace("Resources are created");
            facultyStatement.setInt(1, faculty.getBudgetPlaces());
            facultyStatement.setInt(2, faculty.getTotalPlaces());
            if(facultyStatement.executeUpdate() != 1){
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of faculty places " + faculty);
            }
            LOG.trace("First query went successful");

            langStatement.setString(1, faculty.getNamesList().get(0));
            langStatement.setString(2, faculty.getNamesList().get(1));

            if (langStatement.executeUpdate() != 2) {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of faculty names " + faculty);
            }

            addSubjectsToFaculty(connection, faculty.getSubjectList());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Faculty is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save faculty ==> " + faculty, e);
        }
    }


    private void addSubjectsToFaculty(Connection connection, List<Subject> subjects) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start adding subjects to faculty " );
        int savedRows = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUBJECTS_TO_FACULTY)) {
            LOG.trace("Resources are created");
            LOG.trace("Subjects required to faculty ==> " + subjects.size());
            for (Subject subject : subjects) {
                try {
                    preparedStatement.setInt(1, subject.getId());
                    preparedStatement.executeUpdate();
                    savedRows++;
                    LOG.trace("Saved subjects required to faculty ==> " + savedRows + " last with id " + subject.getId());
                } catch (SQLIntegrityConstraintViolationException e) {
                    continue;
                }
            }
            if (savedRows == subjects.size()) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback!");
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot add subjects to faculty", e);
        }

    }

    @Override
    public void update(Faculty faculty) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating faculty");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(UPDATE_FACULTY_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, faculty.getBudgetPlaces());
            prStatement.setInt(2, faculty.getTotalPlaces());
            prStatement.setInt(3, faculty.getId());

            boolean updated = prStatement.executeUpdate() == 1;
            LOG.debug("Faculty " + faculty + " is updated ? ==>" + updated);
            if (!updated) {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of faculty " + faculty);
            }
            updateLangSet(connection, faculty);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Similar faculty already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot update faculty ==> " + faculty, e);
        }
    }

    private void updateLangSet(Connection connection, Faculty faculty) throws DaoException {
        LOG.debug("Start updating faculty");
        try (PreparedStatement prStatement = connection.prepareStatement(UPDATE_FACULTY_LANG_SET_QUERY)) {
            LOG.trace("Resources are created");
            int i = 0;
            for (String facultyName : faculty.getNamesList()) {
                try {
                    prStatement.setString(1, facultyName);
                    prStatement.setInt(2, faculty.getId());
                    prStatement.setInt(3, ++i);
                    boolean updated = prStatement.executeUpdate() == 1;
                    LOG.debug("Faculty name " + facultyName + " is updated ? ==>" + updated);
                } catch (SQLIntegrityConstraintViolationException e) {
                    LOG.debug("Faculty name with this parameters already exist", e);
                    continue;
                }
            }
            connection.commit();
            LOG.trace("Changes at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot update faculty ==> " + faculty, e);
        }
    }

    @Override
    public void delete(int id) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start deleting faculty");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(DELETE_FACULTY_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);

            boolean deleted = prStatement.executeUpdate() == 1;
            LOG.debug("Faculty with id" + id + " is deleted ? ==>" + deleted);
            if (deleted) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong faculty`s id " + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot delete faculty with id ==> " + id, e);
        }
    }


    /**
     * Extract faculty from ResultSet
     */
    private static class FacultyMapper implements EntityMapper<Faculty> {
        @Override
        public Faculty mapEntity(ResultSet rs) {
            Faculty faculty = new Faculty();
            try {
                faculty.setId(rs.getInt(ColumnLabel.FACULTY_ID.getName()));
                faculty.getNamesList().addAll(Arrays.asList(rs.getString(ColumnLabel.FACULTY_NAME.getName()).split(Pattern.quote(";"))));
                faculty.setBudgetPlaces(rs.getInt(ColumnLabel.FACULTY_BUDGET_PLACES.getName()));
                faculty.setTotalPlaces(rs.getInt(ColumnLabel.FACULTY_TOTAL_PLACES.getName()));
            } catch (SQLException e) {
                LOG.error("Cannot extract Faculty from ResultSet", e);
            }
            LOG.debug("Extracted Faculty from ResultSet ==> " + faculty);
            return faculty;
        }
    }
}
