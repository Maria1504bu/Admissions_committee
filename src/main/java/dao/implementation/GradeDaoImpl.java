package dao.implementation;

import dao.AlreadyExistException;
import dao.ColumnLabel;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.GradeDao;
import models.Grade;
import models.Subject;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Fulfill crud operations for Grade entity with the db
 */
public class GradeDaoImpl implements GradeDao {
    private static final Logger LOG = Logger.getLogger(GradeDaoImpl.class);

    private static final String INSERT_GRADE_QUERY =
            "INSERT INTO grades(subjects_id, grade)VALUES(?, ?);";
    private static final String INSERT_SET_APPL_GRADE_QUERY =
            "INSERT INTO applications_grades(applications_id, grades_id)VALUES(?,?);";
    private static final String GET_GRADE_BY_ID_QUERY =
            "SELECT gr.id, gr.grade, su.id as subj_id, su.duration, GROUP_CONCAT(sl.name SEPARATOR '; ') as name " +
                    "FROM grades gr " +
                    "INNER JOIN subjects su ON su.id = gr.subjects_id " +
                    "INNER JOIN subjects_languages sl ON sl.subject_id = su.id " +
                    "WHERE gr.id = ?";
    private static final String GET_ALL_GRADES_QUERY =
            "SELECT gr.id, gr.grade, su.id as subj_id, su.duration, GROUP_CONCAT(sl.name SEPARATOR '; ') as name " +
                    "FROM grades gr " +
                    "INNER JOIN subjects su ON su.id = gr.subject_id " +
                    "INNER JOIN subjects_languages sl ON sl.subject_id = su.id ";
    private static final String UPDATE_GRADE_QUERY =
            "UPDATE grades SET (grade) VALUE (?)";
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
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(GET_GRADE_BY_ID_QUERY)) {
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
            throw new DaoException("Cannot find grade with id ==>" + id, e);
        }
        LOG.debug("Searched grade ==>" + grade);
        return grade;
    }

    @Override
    public List<Grade> findAll() throws DaoException {
        List<Grade> faculties = new ArrayList<>();
        LOG.debug("Finding all faculties ...");
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_GRADES_QUERY)) {
            LOG.trace("Resources are created");

            GradeDaoImpl.GradeMapper mapper = new GradeDaoImpl.GradeMapper();
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
    public void save(Grade grade) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving grade ==> " + grade);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_GRADE_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, grade.getSubject().getId());
            prStatement.setInt(2, grade.getGrade());

            boolean saved = prStatement.executeUpdate() == 1;
            LOG.debug("Grade " + grade + " is saved ? ==>" + saved);
            if (saved) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
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
    public void createApplGradesSet(int applId, int gradeId) throws WrongExecutedQueryException, AlreadyExistException{
        LOG.debug("Start saving set with applId " + applId + " and gradeId " + gradeId);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_SET_APPL_GRADE_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, applId);
            prStatement.setInt(2, gradeId);

            boolean saved = prStatement.executeUpdate() == 1;
            LOG.debug("Set is saved ? ==>" + saved);
            if (saved) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of set ");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Set is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save set with applId " + applId + " and gradeId " + gradeId, e);
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
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(UPDATE_GRADE_QUERY)) {
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
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(DELETE_GRADE_QUERY)) {
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
    }

    /**
     * Extracts an grade from the result set row.
     */
    private static class GradeMapper implements EntityMapper<Grade> {
        @Override
        public Grade mapEntity(ResultSet rs) {
            try {
                Grade grade = new Grade();
                Subject subject = new Subject();
                grade.setId(rs.getInt(ColumnLabel.GRADE_ID.getName()));
                grade.setGrade(rs.getInt(ColumnLabel.GRADE_VALUE.getName()));

                subject.setId(rs.getInt("subj_id"));
                subject.setCourseDuration(rs.getInt(ColumnLabel.SUBJECT_DURATION.getName()));
                subject.getNameList().addAll(Arrays.asList(rs.getString(ColumnLabel.FACULTY_NAME.getName()).split(Pattern.quote("; "))));
                grade.setSubject(subject);
                return grade;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
