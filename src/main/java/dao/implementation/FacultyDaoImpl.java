package dao.implementation;

import dao.AlreadyExistException;
import dao.ColumnLabel;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.FacultyDao;
import models.Faculty;
import models.Language;
import models.Subject;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FacultyDaoImpl implements FacultyDao {
    private static final Logger LOG = Logger.getLogger(FacultyDaoImpl.class);

    private static final String GET_FACULTY_BY_ID_QUERY = "SELECT f.id, f.budget_places, f.total_places " +
            "FROM faculties f WHERE f.id = ?";

    private static final String GET_ALL_FACULTIES_ORDER_BY_QUERY = "SELECT f.id, f.budget_places, f.total_places, fl.name " +
            "FROM faculties f " +
            "INNER JOIN faculties_languages fl ON f.id = fl.faculty_id " +
            "INNER JOIN languages l ON l.id = fl.language_id WHERE l.lang_code = ? " +
            "ORDER BY ";

    private static final String GET_FACULTY_NAME_QUERY = "SELECT la.lang_code,  fl.name FROM faculties_languages fl " +
            "INNER JOIN  faculties f ON f.id = fl.faculty_id " +
            "INNER JOIN languages la ON la.id = fl.language_id " +
            "WHERE faculty_id = ?";
    private final static String INSERT_FACULTY_QUERY = "INSERT INTO faculties (budget_places, total_places) VALUES (?, ?);";
    private static final String INSERT_FACULTY_LANG_QUERY = "INSERT INTO faculties_languages (faculty_id, language_id, name) " +
            "VALUE ((SELECT MAX(id) FROM faculties), (SELECT id FROM languages WHERE lang_code = ?), ?) ";
    private static final String INSERT_SUBJECTS_TO_FACULTY =
            "INSERT INTO faculties_subjects (faculty_id, subject_id) VALUES ((SELECT MAX(id) FROM faculties), ?);";


    private static final String UPDATE_FACULTY_QUERY =
            "UPDATE faculties SET budget_places = ?, total_places = ? " +
                    "WHERE id = ?;";

    private static final String UPDATE_FACULTY_LANG_SET_QUERY =
            "UPDATE faculties_languages SET name = ? " +
                    "WHERE faculty_id = ? AND language_id = (SELECT id FROM languages WHERE lang_code = ?);";

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
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(GET_FACULTY_BY_ID_QUERY)) {
                LOG.trace("Resources are created");
                prStatement.setInt(1, id);

                try (ResultSet resultSet = prStatement.executeQuery()) {
                    FacultyMapper mapper = new FacultyMapper();
                    while (resultSet.next()) {
                        faculty = mapper.mapEntity(resultSet);
                    }
                }
                getNames(connection, faculty);
                connection.commit();
                LOG.trace("Changes  at db was committed");
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot find faculty with id ==>" + id, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
        LOG.debug("Searched faculty ==>" + faculty);
        return faculty;
    }

    @Override
    public List<Faculty> getAllOrderBy(String lang, String orderBy) throws DaoException {
        List<Faculty> faculties = new ArrayList<>();
        LOG.debug("Start to find all faculties with name by language ==> " + lang + " and sort it by ==> " + orderBy);
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(GET_ALL_FACULTIES_ORDER_BY_QUERY + orderBy)) {
                LOG.trace("Resources are created");
                prStatement.setString(1, lang);
                LOG.trace("Set language to prepareStatement ==> " + lang);
                ResultSet resultSet = prStatement.executeQuery();

                FacultyMapper mapper = new FacultyMapper();
                while (resultSet.next()) {
                    faculties.add(mapper.mapEntity(resultSet));
                }

                for(Faculty faculty : faculties){
                    getNames(connection, faculty);
                }
                connection.commit();
                LOG.trace("Changes  at db was committed");
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot find all faculties", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
        LOG.debug("Found faculties ==>" + faculties);
        return faculties;
    }

    private void getNames(Connection connection, Faculty faculty) throws DaoException, SQLException {
        LOG.debug("Start getting subject name to faculty ==> " + faculty);
        try (PreparedStatement prStatement = connection.prepareStatement(GET_FACULTY_NAME_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, faculty.getId());
            try (ResultSet resultSet = prStatement.executeQuery()) {
                FacultyMapper mapper = new FacultyMapper();
                while (resultSet.next()) {
                    mapper.addName(resultSet, faculty);
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            connection.rollback();
            throw new DaoException("Cannot find subject name to faculty ==>" + faculty, e);
        }
        LOG.debug("Searched faculty ==>" + faculty);
    }
    @Override
    public void save(Faculty faculty) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving faculty ==> " + faculty);
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_FACULTY_QUERY)
            ) {
                LOG.trace("Resources are created");
                statement.setInt(1, faculty.getBudgetPlaces());
                statement.setInt(2, faculty.getTotalPlaces());
                if (statement.executeUpdate() != 1) {
                    connection.rollback();
                    LOG.trace("Changes at db is rollback");
                    throw new WrongExecutedQueryException("Operation is rollback! Wrong data of faculty places " + faculty);
                }
                LOG.trace("First query went successful");

                saveFacultyNames(connection, faculty);
                addSubjectsToFaculty(connection, faculty.getSubjectList());
            } catch (SQLIntegrityConstraintViolationException e) {
                connection.rollback();
                throw new AlreadyExistException("Faculty is already exist", e);
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot save faculty ==> " + faculty, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
    }

    private void saveFacultyNames(Connection connection, Faculty faculty) throws DaoException {
        LOG.debug("Start saving faculty names");
        try (PreparedStatement prStatement = connection.prepareStatement(INSERT_FACULTY_LANG_QUERY)) {
            LOG.trace("Resources are created");
            for (Map.Entry<Language, String> facultyName : faculty.getNames().entrySet()) {
                try {
                    prStatement.setString(1, facultyName.getKey().name().toLowerCase());
                    prStatement.setString(2, facultyName.getValue());
                    boolean saved = prStatement.executeUpdate() == 1;
                    LOG.debug("Faculty name " + facultyName + " is saved ? ==>" + saved);
                } catch (SQLIntegrityConstraintViolationException e) {
                    LOG.debug("Faculty name with this parameters already exist", e);
                    throw new DaoException("Faculty name with this parameters already exist", e);
                }
            }
            connection.commit();
            LOG.trace("Changes at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot save faculty name ==> " + faculty, e);
        }
    }


    private void addSubjectsToFaculty(Connection connection, List<Subject> subjects) throws
            WrongExecutedQueryException, DaoException {
        LOG.debug("Start adding subjects to faculty ");
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
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            throw new DaoException("Cannot add subjects to faculty", e);
        }

    }

    @Override
    public void update(Faculty faculty) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating faculty");
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(UPDATE_FACULTY_QUERY)) {
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
                connection.rollback();
                throw new AlreadyExistException("Similar faculty already exist", e);
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot update faculty ==> " + faculty, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
    }

    private void updateLangSet(Connection connection, Faculty faculty) throws DaoException {
        LOG.debug("Start updating faculty");

        try (PreparedStatement prStatement = connection.prepareStatement(UPDATE_FACULTY_LANG_SET_QUERY)) {
            LOG.trace("Resources are created");
        int i = 0;
            for (Map.Entry<Language, String> facultyName : faculty.getNames().entrySet()) {
                try {
                    prStatement.setString(1, facultyName.getValue());
                    prStatement.setInt(2, faculty.getId());
                    prStatement.setString(3, facultyName.getKey().name().toLowerCase());
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
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(DELETE_FACULTY_QUERY)) {
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
                connection.rollback();
                throw new DaoException("Cannot delete faculty with id ==> " + id, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
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
                faculty.setBudgetPlaces(rs.getInt(ColumnLabel.FACULTY_BUDGET_PLACES.getName()));
                faculty.setTotalPlaces(rs.getInt(ColumnLabel.FACULTY_TOTAL_PLACES.getName()));
            } catch (SQLException e) {
                LOG.error("Cannot extract Faculty from ResultSet", e);
            }
            LOG.debug("Extracted Faculty from ResultSet ==> " + faculty);
            return faculty;
        }

        public void addName(ResultSet resultSet, Faculty faculty) {
            try {
                Language lang = (Enum.valueOf(Language.class, resultSet.getString(ColumnLabel.LANG_CODE.getName()).toUpperCase()));
                String name = resultSet.getString(ColumnLabel.FACULTY_NAME.getName());
                faculty.getNames().put(lang, name);
            } catch (SQLException e) {
                LOG.error("Cannot extract subject from ResultSet", e);
            }
        }
    }
}
