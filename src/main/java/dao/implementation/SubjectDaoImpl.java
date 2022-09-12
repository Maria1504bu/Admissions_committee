package dao.implementation;

import dao.AlreadyExistException;
import dao.ColumnLabel;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.SubjectDao;
import models.Language;
import models.Subject;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectDaoImpl implements SubjectDao {
    private static final Logger LOG = Logger.getLogger(SubjectDaoImpl.class);

    private static final String GET_SUBJECT_BY_ID_QUERY = "SELECT su.id, su.maxGrade FROM subjects su " +
            "WHERE su.id = ?";

    private static final String GET_SUBJECT_BY_FACULTY_ID_QUERY = "SELECT su.id, su.maxGrade " +
            "FROM faculties fa, faculties_subjects fs, subjects su " +
            "WHERE fa.id = fs.faculty_id AND fs.subject_id = su.id AND su.id = sl.subject_id " +
            "AND fa.id = ?;";
    private static final String GET_ALL_SUBJECTS_QUERY = "SELECT su.id, su.maxGrade " +
            "FROM subjects su;";

    private static final String GET_SUBJECT_NAME_QUERY = "SELECT la.lang_code, sl.name FROM subjects_languages sl " +
            "INNER JOIN  subjects su ON su.id = sl.subject_id " +
            "INNER JOIN languages la ON la.id = sl.language_id " +
            "WHERE su.id = ?";
    private static final String INSERT_SUBJECT_QUERY = "INSERT INTO subjects (maxGrade) VALUE (?); ";

    private static final String INSERT_SUBJECT_LANG_QUERY = "INSERT INTO subjects_languages (`subject_id`, `language_id`, `name`) " +
            "VALUE ((SELECT MAX(id) FROM subjects), (SELECT id FROM languages WHERE lang_code = ?), ?) ";

    private static final String UPDATE_SUBJECT_QUERY = "UPDATE subjects SET maxGrade = ? " +
            " WHERE id = ?";

    private static final String UPDATE_SUBJECT_LANG_SET_QUERY = "UPDATE subjects_languages SET name = ? " +
            "WHERE subject_id = ? AND language_id = (SELECT id FROM languages WHERE lang_code = ?)";
    private static final String DELETE_SUBJECT_QUERY = "DELETE FROM subjects WHERE id = ?";
    private static final String ROLLBACK_CHANGES = "Changes at db is rollback";


    private final DataSource dataSource;

    public SubjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DaoException("Cannot create connection", e);
        }
        return connection;
    }

    @Override
    public Subject getById(int id) throws DaoException {
        LOG.debug("Start getting subject by id ==> " + id);
        Subject subject = null;
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(GET_SUBJECT_BY_ID_QUERY)) {
                LOG.trace("Resources are created");
                prStatement.setInt(1, id);
                LOG.trace("set parameter into prepareStatement" + id);
                try (ResultSet resultSet = prStatement.executeQuery()) {
                    SubjectMapper mapper = new SubjectMapper();
                    while (resultSet.next()) {
                        subject = mapper.mapEntity(resultSet);
                    }
                }
                getNames(connection, subject);
                connection.commit();
                LOG.trace("Changes  at db was committed");
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot find subject with id ==>" + id, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
        LOG.debug("Searched subject ==>" + subject);
        return subject;
    }


    private Subject getNames(Connection connection, Subject subject) throws DaoException, SQLException {
        LOG.debug("Start getting subject name to subject ==> " + subject);
        try (PreparedStatement prStatement = connection.prepareStatement(GET_SUBJECT_NAME_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, subject.getId());
            try (ResultSet resultSet = prStatement.executeQuery()) {
                SubjectMapper mapper = new SubjectMapper();
                while (resultSet.next()) {
                    mapper.addName(resultSet, subject);
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            connection.rollback();
            throw new DaoException("Cannot find subject name to subject ==>" + subject, e);
        }
        LOG.debug("Searched subject ==>" + subject);
        return subject;
    }

    @Override
    public List<Subject> findAll() throws DaoException {
        List<Subject> subjects = new ArrayList<>();
        LOG.debug("Start searching all subjects");
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(GET_ALL_SUBJECTS_QUERY);) {
                LOG.trace("Resources are created");
                SubjectMapper mapper = new SubjectMapper();
                while (resultSet.next()) {
                    subjects.add(mapper.mapEntity(resultSet));
                }
                for(Subject subject : subjects){
                    getNames(connection, subject);
                }
                connection.commit();
                LOG.trace("Changes  at db was committed");
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot find all subjects", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create connection", e);
        }
        LOG.debug("Searched subjects ==>" + subjects);
        return subjects;
    }

    @Override
    public List<Subject> findAllByFacultyId(int facultyId) throws DaoException {
        LOG.debug("Start searching candidates subjects");
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (
                    PreparedStatement prStatement = connection.prepareStatement(GET_SUBJECT_BY_FACULTY_ID_QUERY)) {
                LOG.trace("Resources are created");
                prStatement.setInt(1, facultyId);

                SubjectMapper mapper = new SubjectMapper();
                try (ResultSet resultSet = prStatement.executeQuery()) {
                    while (resultSet.next()) {
                        subjects.add(mapper.mapEntity(resultSet));
                    }
                }
                for(Subject subject : subjects){
                    getNames(connection, subject);
                }
                connection.commit();
                LOG.trace("Changes  at db was committed");
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot find faculty`s subjects by id ==> " + facultyId, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create connection", e);
        }
        LOG.debug("Searched subjects by facultyId:" + subjects);
        return subjects;
    }

    @Override
    public void save(Subject subject) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving subject ==> " + subject);
        try (Connection connection = getConnection()) {
            try (PreparedStatement subjStatement = connection.prepareStatement(INSERT_SUBJECT_QUERY)) {
                LOG.trace("Resources are created");
                subjStatement.setInt(1, subject.getMaxGrade());

                if (subjStatement.executeUpdate() != 1) {
                    connection.rollback();
                    LOG.trace("Changes at db is rollback");
                    throw new WrongExecutedQueryException("Operation is rollback! Wrong data of subject " + subject);
                }
                LOG.trace("First query went successful");

                saveSubjectNames(connection, subject);
                connection.commit();
                LOG.trace("Changes at db was committed");
            } catch (SQLIntegrityConstraintViolationException e) {
                connection.rollback();
                throw new AlreadyExistException("Subject is already exist", e);
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot save subject ==> " + subject, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create connection", e);
        }
    }

    private void saveSubjectNames(Connection connection, Subject subject) throws DaoException {
        LOG.debug("Start saving subject names");
        try (PreparedStatement prStatement = connection.prepareStatement(INSERT_SUBJECT_LANG_QUERY)) {
            LOG.trace("Resources are created");
            for (Map.Entry<Language, String> subjectName : subject.getNames().entrySet()) {
                try {
                    prStatement.setString(1, subjectName.getKey().name().toLowerCase());
                    prStatement.setString(2, subjectName.getValue());
                    boolean saved = prStatement.executeUpdate() == 1;
                    LOG.debug("Subject name " + subjectName + " is saved ? ==>" + saved);
                } catch (SQLIntegrityConstraintViolationException e) {
                    LOG.debug("Subject name with this parameters already exist", e);
                    throw new DaoException("Subject name with this parameters already exist", e);
                }
            }
            connection.commit();
            LOG.trace("Changes at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot save subject name ==> " + subject, e);
        }
    }

    @Override
    public void update(Subject subject) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating subject");
        try (Connection connection = getConnection()) {
            try (
                    PreparedStatement prStatement = connection.prepareStatement(UPDATE_SUBJECT_QUERY)) {
                LOG.trace("Resources are created");
                prStatement.setInt(1, subject.getMaxGrade());
                prStatement.setInt(2, subject.getId());

                boolean updated = prStatement.executeUpdate() == 1;
                LOG.debug("Subject " + subject + " is updated ? ==>" + updated);

                if (!updated) {
                    connection.rollback();
                    LOG.trace(ROLLBACK_CHANGES);
                    throw new WrongExecutedQueryException("Operation is rollback! Wrong data of subject " + subject);
                }
                updateLangSet(connection, subject);
            } catch (SQLIntegrityConstraintViolationException e) {
                connection.rollback();
                LOG.trace(ROLLBACK_CHANGES);
                throw new AlreadyExistException("Similar subject already exist", e);
            } catch (SQLException e) {
                connection.rollback();
                LOG.trace(ROLLBACK_CHANGES);
                throw new DaoException("Cannot update subject ==> " + subject, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create connection", e);
        }
    }

    private void updateLangSet(Connection connection, Subject subject) throws DaoException {
        LOG.debug("Start updating subject");
        try (PreparedStatement prStatement = connection.prepareStatement(UPDATE_SUBJECT_LANG_SET_QUERY)) {
            LOG.trace("Resources are created");
            for (Map.Entry<Language, String> subjectName : subject.getNames().entrySet()) {
                try {
                    prStatement.setString(1, subjectName.getKey().name().toLowerCase());
                    prStatement.setInt(2, subject.getId());
                    prStatement.setString(3, subjectName.getValue());
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
        try (Connection connection = getConnection()) {
            try (
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
                    LOG.trace(ROLLBACK_CHANGES);
                    throw new WrongExecutedQueryException("Operation is rollback! Wrong subject`s id " + id);
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot delete subject with id==> " + id, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create connection", e);
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
                subject.setMaxGrage(rs.getInt(ColumnLabel.SUBJECT_MAX_GRADE.getName()));
            } catch (SQLException e) {
                LOG.error("Cannot extract subject from ResultSet", e);
            }
            LOG.debug("Extracted subject from ResultSet ==> " + subject);
            return subject;
        }

        public void addName(ResultSet resultSet, Subject subject) {
            try {
                Language lang = (Enum.valueOf(Language.class, resultSet.getString(ColumnLabel.LANG_CODE.getName()).toUpperCase()));
                String name = resultSet.getString(ColumnLabel.SUBJECT_NAME.getName());
                subject.getNames().put(lang, name);
            } catch (SQLException e) {
                LOG.error("Cannot extract subject from ResultSet", e);
            }
        }
    }
}
