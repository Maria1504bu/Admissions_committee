package dao.implementation;

import dao.AlreadyExistException;
import dao.ColumnLabel;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.GradeDao;
import models.Candidate;
import models.Grade;
import models.Subject;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fulfill crud operations for Grade entity with the db
 */
public class GradeDaoImpl implements GradeDao {
    private static final Logger LOG = Logger.getLogger(GradeDaoImpl.class);

    private static final String INSERT_GRADE_QUERY =
            "INSERT INTO grades(candidate_id, subject_id, grade)VALUES(?, ?);";
    private static final String INSERT_SET_APPL_GRADE_QUERY =
            "INSERT INTO applications_grades(application_id, grade_id)VALUE" +
                    "((SELECT MAX(id) FROM applications)," +
                    "(SELECT MAX(id) FROM grades));";
    //todo: won`t work because id is not yet.
    private static final String GET_GRADE_BY_ID_QUERY =
            "SELECT * FROM grades gr WHERE gr.id = ?";
    private static final String GET_ALL_GRADES_QUERY =
            "SELECT * FROM grades gr";
    private static final String GET_CANDIDATE_GRADES_QUERY =
            "SELECT * FROM grades gr WHERE gr.candidate_id = ?";
    private static final String GET_APPL_GRADES_QUERY = "SELECT gr.* FROM grades gr INNER JOIN faculties_subjects fs ON gr.subject_id = fs.subject_id " +
            "INNER JOIN applications a ON fs.faculty_id = a.faculty_id AND gr.candidate_id = a.login_id " +
            "WHERE a.id = ?";
    private static final String UPDATE_GRADE_QUERY =
            "UPDATE grades SET (grade) VALUE (?)";
    //todo: won`t work because id is not yet.
    private static final String DELETE_GRADE_QUERY =
            "DELETE FROM grades WHERE id = ?";

    private final DataSource dataSource;

    public GradeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Grade getById(int id) throws DaoException {
        LOG.debug("Start getting grade by id ==> " + id);
        Grade grade = null;
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(GET_GRADE_BY_ID_QUERY)) {
                LOG.trace("Resources are created");
                prStatement.setInt(1, id);

                try (ResultSet resultSet = prStatement.executeQuery()) {
                    GradeDaoImpl.GradeMapper mapper = new GradeDaoImpl.GradeMapper();
                    while (resultSet.next()) {
                        grade = mapper.mapEntity(resultSet);
                    }
                }
                connection.commit();
                LOG.trace("Changes  at db was committed");
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Cannot find grade with id ==>" + id, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
        LOG.debug("Searched grade ==>" + grade);
        return grade;
    }

    @Override
    public List<Grade> findAll() throws DaoException {
        List<Grade> grades = new ArrayList<>();
        LOG.debug("Start finding all grades");
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_ALL_GRADES_QUERY)) {
                LOG.trace("Resources are created");

                GradeDaoImpl.GradeMapper mapper = new GradeDaoImpl.GradeMapper();
                while (resultSet.next()) {
                    grades.add(mapper.mapEntity(resultSet));
                }

                connection.commit();
                LOG.trace("Changes  at db was committed");
            } catch (SQLException e) {
                throw new DaoException("Cannot find all grades", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
        LOG.debug("Found grades ==>" + grades);
        return grades;
    }

    @Override
    public List<Grade> getCandidatesGrades(int candidatesId) {
        List<Grade> grades = new ArrayList<>();
        LOG.debug("Start finding candidate grades");
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(GET_CANDIDATE_GRADES_QUERY)) {
                LOG.trace("Resources are created");
                prStatement.setInt(1, candidatesId);

                try (ResultSet resultSet = prStatement.executeQuery()) {
                    GradeMapper mapper = new GradeMapper();
                    while (resultSet.next()) {
                        grades.add(mapper.mapEntity(resultSet));
                    }
                }

                connection.commit();
                LOG.trace("Changes  at db was committed");
            } catch (SQLException e) {
                throw new DaoException("Cannot find candidate grades", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
        LOG.debug("Found grades ==>" + grades);
        return grades;
    }

    @Override
    public List<Grade> getApplGrades(int applId) {
        List<Grade> grades = new ArrayList<>();
        LOG.debug("Start finding application grades");
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(GET_APPL_GRADES_QUERY)) {
                LOG.trace("Resources are created");
                prStatement.setInt(1, applId);

                try (ResultSet resultSet = prStatement.executeQuery()) {
                    GradeMapper mapper = new GradeMapper();
                    while (resultSet.next()) {
                        grades.add(mapper.mapEntity(resultSet));
                    }
                }

                connection.commit();
                LOG.trace("Changes  at db was committed");
            } catch (SQLException e) {
                throw new DaoException("Cannot find application grades", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
        LOG.debug("Found grades ==>" + grades);
        return grades;
    }

    @Override
    public void save(Connection connection, Grade grade) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving grade ==> " + grade);
        try (PreparedStatement prStatement = connection.prepareStatement(INSERT_GRADE_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, grade.getSubject().getId());
            prStatement.setInt(2, grade.getGrade());

            boolean saved = prStatement.executeUpdate() == 1;
            LOG.debug("Grade " + grade + " is saved ? ==>" + saved);
            if (!saved) {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of grade " + grade);
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Grade is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save grade ==> " + grade, e);
        }
    }

    @Override
    public void createApplGradesSet(Connection connection) throws WrongExecutedQueryException, AlreadyExistException {
        LOG.debug("Start saving set with applId and gradeId ");
        try (Statement statement = connection.createStatement()) {
            LOG.trace("Resources are created");

            boolean saved = statement.executeUpdate(INSERT_SET_APPL_GRADE_QUERY) == 1;
            LOG.debug("Set is saved ? ==>" + saved);
            if (!saved) {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of set ");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Set is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save set with applId and gradeId", e);
        }
    }

    /**
     * Upgrade grade value
     *
     * @param grade entity
     */
    @Override
    public void update(Grade grade) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating grade");
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(UPDATE_GRADE_QUERY)) {
                LOG.trace("Resources are created");
                prStatement.setInt(1, grade.getGrade());

                boolean updated = prStatement.executeUpdate() == 1;
                LOG.debug("Grade " + grade + " is updated ? ==>" + updated);
                if (updated) {
                    connection.commit();
                    LOG.trace("Changes at db was committed");
                } else {
                    connection.rollback();
                    LOG.trace("Changes at db is rollback");
                    throw new WrongExecutedQueryException("Operation is rollback! Wrong data of grade " + grade);
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new AlreadyExistException("Similar grade already exist", e);
            } catch (SQLException e) {
                throw new DaoException("Cannot update grade ==> " + grade, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
    }

    /**
     * Delete grade instance by id
     *
     * @param id of grade entity
     * @return true if succeed
     */
    @Override
    public void delete(int id) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start deleting grade");
        try (Connection connection = getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(DELETE_GRADE_QUERY)) {
                LOG.trace("Resources are created");
                prStatement.setInt(1, id);

                boolean deleted = prStatement.executeUpdate() == 1;
                LOG.debug("Grade with id" + id + " is deleted ? ==>" + deleted);
                if (deleted) {
                    connection.commit();
                    LOG.trace("Changes at db was committed");
                } else {
                    connection.rollback();
                    LOG.trace("Changes at db is rollback");
                    throw new WrongExecutedQueryException("Operation is rollback! Wrong grade`s id " + id);
                }
            } catch (SQLException e) {
                throw new DaoException("Cannot delete grade with id ==> " + id, e);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot close connection");
        }
    }

    /**
     * Extracts an grade from the result set row.
     */
    private static class GradeMapper implements EntityMapper<Grade> {

        @Override
        public Grade mapEntity(ResultSet rs) {
            Grade grade = new Grade();
            Subject subject = new Subject();
            Candidate candidate;
            try {
                grade.setGrade(rs.getInt(ColumnLabel.GRADE_VALUE.getName()));

                subject.setId(rs.getInt(ColumnLabel.GRADE_SUBJ_ID.getName()));
                grade.setSubject(subject);
                candidate = Candidate.builder().id(rs.getInt(ColumnLabel.GRADE_CANDIDATE_ID.getName())).build();
                grade.setCandidate(candidate);
                LOG.debug("Extracted grade ==> " + grade);
            } catch (SQLException e) {
                LOG.error("Cannot extract grade from ResultSet", e);
            }
            LOG.debug("Extracted grade from ResultSet ==> " + grade);
            return grade;
        }
    }
}


