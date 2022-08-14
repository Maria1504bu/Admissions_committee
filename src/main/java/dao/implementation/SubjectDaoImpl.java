package dao.implementation;

import dao.AlreadyExistException;
import dao.ColumnLabel;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.SubjectDao;
import models.Subject;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

public class SubjectDaoImpl implements SubjectDao {
    private static final Logger LOG = Logger.getLogger(SubjectDaoImpl.class);

    private static final String GET_SUBJECT_BY_ID_QUERY = "SELECT su.id, su.duration, sl.name " +
            "FROM subjects su " +
            "INNER JOIN subjects_languages sl ON su.id = sl.subjects_id " +
            "INNER JOIN languages la ON la.id = sl.languages_id " +
            "WHERE su.id = ?";

    private static final String GET_SUBJECT_BY_FACULTY_ID_QUERY = "SELECT su.id, su.duration, GROUP_CONCAT(sl.name SEPARATOR '; ') as name " +
            "FROM faculties fa, faculties_subjects fs, subjects su, subjects_languages sl, languages la " +
            "WHERE fa.id = fs.faculties_id AND fs.subjects_id = su.id AND su.id = sl.subjects_id AND sl.languages_id = la.id " +
            "AND la.lang_code = ? AND fa.id = ?;";
    private static final String GET_ALL_SUBJECTS_QUERY = "SELECT su.id, su.duration, GROUP_CONCAT(sl.name SEPARATOR '; ') as name " +
            "FROM subjects su " +
            "INNER JOIN subjects_languages sl ON su.id = sl.subjects_id " +
            "INNER JOIN languages la ON la.id = sl.languages_id ";
    private final static String INSERT_SUBJECT_QUERY = "INSERT INTO subjects (duration)" +
            "VALUE (?); INSERT INTO subjects_languages (`subjects_id`, `languages_id`, `name`) " +
            "VALUES " +
            "((SELECT MAX(id) FROM subjects), (SELECT id FROM languages WHERE lang_code = 'en'), ?), " +
            "((SELECT MAX(id) FROM subjects), (SELECT id FROM languages WHERE lang_code = 'uk'), ?]) ";

    private static final String UPDATE_SUBJECT_QUERY = "UPDATE subjects SET duration = ? " +
            " WHERE id = ?";

    private static final String UPDATE_SUBJECT_LANG_SET_QUERY = "UPDATE subjects_languages SET name = ? " +
            "WHERE subjects_id = ? AND languages_id = ?";
    private static final String DELETE_SUBJECT_QUERY = "DELETE FROM subjects WHERE id = ?";


    private final DataSource dataSource;

    public SubjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Subject getById(int id) throws DaoException {
        LOG.debug("Start getting subject by id ==> " + id);
        Subject subject = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(GET_SUBJECT_BY_ID_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);
            LOG.trace("set parameter into prepareStatement" + id);
            try (ResultSet resultSet = prStatement.executeQuery()) {
                SubjectMapper mapper = new SubjectMapper();
                while (resultSet.next()) {
                    subject = mapper.mapEntity(resultSet);
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find subject with id ==>" + id, e);
        }
        LOG.debug("Searched subject ==>" + subject);
        return subject;
    }


    //TODO:delete if dont use
//    @Override
//    public Subject getByName(String name) throws DaoException {
//        LOG.debug("Start getting subject by name ==> " + name);
//        Subject subject = null;
//        try (Connection connection = getConnection();
//             PreparedStatement prStatement = connection.prepareStatement(GET_SUBJECT_BY_NAME_QUERY)) {
//            LOG.trace("Resources are created");
//            prStatement.setString(1, name);
//            try (ResultSet resultSet = prStatement.executeQuery()) {
//                SubjectMapper mapper = new SubjectMapper();
//                while (resultSet.next()) {
//                    subject = mapper.mapEntity(resultSet);
//                }
//            }
//            connection.commit();
//            LOG.trace("Changes  at db was committed");
//        } catch (SQLException e) {
//            throw new DaoException("Cannot find subject with name ==>" + name, e);
//        }
//        LOG.debug("Searched subject ==>" + subject);
//        return subject;
//    }


    //todo: at service filter lang
    @Override
    public List<Subject> findAll() throws DaoException {
        List<Subject> subjects = new ArrayList<>();
        LOG.debug("Start searching all subjects");
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SUBJECTS_QUERY);) {
            LOG.trace("Resources are created");
            SubjectMapper mapper = new SubjectMapper();
            while (resultSet.next()) {
                subjects.add(mapper.mapEntity(resultSet));
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find all subjects", e);
        }
        LOG.debug("Searched subjects ==>" + subjects);
        return subjects;
    }

    @Override
    public List<Subject> findAllByFacultyId(String lang, int facultyId) throws DaoException {
        LOG.debug("Start searching candidates subjects");
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(GET_SUBJECT_BY_FACULTY_ID_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, lang);
            prStatement.setInt(2, facultyId);

            SubjectMapper mapper = new SubjectMapper();
            try (ResultSet resultSet = prStatement.executeQuery()) {
                while (resultSet.next()) {
                    subjects.add(mapper.mapEntity(resultSet));
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find faculty`s subjects by id ==> " + facultyId, e);
        }
        LOG.debug("Searched subjects by facultyId:" + subjects);
        return subjects;
    }

    @Override
    public void save(Subject subject) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving subject ==> " + subject);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_SUBJECT_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, subject.getCourseDuration());
            prStatement.setString(2, subject.getNameList().get(0));
            prStatement.setString(3, subject.getNameList().get(1));

            boolean saved = prStatement.executeUpdate() == 3;
            LOG.debug("subject " + subject + " is saved ? ==>" + saved);
            if (saved) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of subject " + subject);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Subject is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save subject ==> " + subject, e);
        }
    }

    @Override
    public void update(Subject subject) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating subject");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(UPDATE_SUBJECT_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, subject.getCourseDuration());
            prStatement.setInt(2, subject.getId());

            boolean updated = prStatement.executeUpdate() == 1;
            LOG.debug("Subject " + subject + " is updated ? ==>" + updated);

            if (updated) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of subject " + subject);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Similar subject already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot update subject ==> " + subject, e);
        }
    }

    @Override
    public void updateLangSet(Subject subject) throws DaoException {
        LOG.debug("Start updating subject");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(UPDATE_SUBJECT_LANG_SET_QUERY)) {
            LOG.trace("Resources are created");
            int i = 0;
            for (String subjectName : subject.getNameList()) {
                try {
                    prStatement.setString(1, subjectName);
                    prStatement.setInt(2, subject.getId());
                    prStatement.setInt(3, ++i);
                    boolean updated = prStatement.executeUpdate() == 1;
                    LOG.debug("Subject name " + subjectName + " is updated ? ==>" + updated);
                } catch (SQLIntegrityConstraintViolationException e) {
                    LOG.debug("Subject name with this parameters already exist", e);
                    continue;
                }
            }
            connection.commit();
            LOG.trace("Changes at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot update subject ==> " + subject, e);
        }
    }

    @Override
    public void delete(int id) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start deleting subject");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(DELETE_SUBJECT_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);

            boolean deleted = prStatement.executeUpdate() == 1;
            LOG.debug("Subject with id" + id + " removed ? => " + deleted);
            if (deleted) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong subject`s id " + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot delete subject with id==> " + id, e);
        }
    }

    /**
     * Extract subject from ResultSet
     */
    private static class SubjectMapper implements EntityMapper<Subject> {
        @Override
        public Subject mapEntity(ResultSet rs) {
            Subject subject = new Subject();
            try {
                subject.setId(rs.getInt(ColumnLabel.SUBJECT_ID.getName()));
                subject.setCourseDuration(rs.getInt(ColumnLabel.SUBJECT_DURATION.getName()));
                subject.getNameList().addAll(Arrays.asList(rs.getString(ColumnLabel.SUBJECT_NAME.getName()).split(Pattern.quote(";"))));
            } catch (SQLException e) {
                LOG.error("Cannot extract subject from ResultSet", e);
            }
            LOG.debug("Extracted subject from ResultSet ==> " + subject);
            return subject;
        }
    }
}
