package dao;

import models.Exam;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamDaoImpl implements ExamDao {
    private static final Logger LOG = Logger.getLogger(ExamDaoImpl.class);

    private static final String FIND_EXAM_BY_ID_QUERY = "SELECT name " +
            "FROM exams " +
            "WHERE id = ?";

    private static final String FIND_EXAM_BY_NAME_QUERY = "SELECT id, name " +
            "FROM exams " +
            "WHERE name = ?";

    private static final String FIND_EXAM_BY_CANDIDATE_ID_QUERY = "SELECT e.id, e.name, ce.mark FROM candidates_exams ce, exams e " +
            "WHERE ce.candidate_id = ? AND e.id = ce.exam_id";
    private static final String FIND_ALL_EXAMS_QUERY = "SELECT * FROM exams";
    private final static String INSERT_EXAM_QUERY = "INSERT INTO exams (name)" +
            "VALUE (?)";
    private static final String INSERT_CANDIDATES_EXAM_QUERY = "INSERT INTO candidates_exams " +
            "VALUES (?, ?, ?)";
    private static final String DELETE_EXAM_QUERY = "DELETE FROM exams WHERE id = ?";
    private static final String UPDATE_EXAM_QUERY = "UPDATE exams SET name = ? " +
            " WHERE id = ?";

    private final DataSource dataSource;

    public ExamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Exam getById(int id) throws DaoException {
        LOG.debug("Start getting exam by id ==> " + id);
        Exam exam = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(FIND_EXAM_BY_ID_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);
            LOG.trace("set parameter into prepareStatement" + id);
            try (ResultSet resultSet = prStatement.executeQuery()) {
                ExamMapper mapper = new ExamMapper();
                while (resultSet.next()) {
                    exam = mapper.mapEntity(resultSet);
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find exam with id ==>" + id, e);
        }
        LOG.debug("Searched exam ==>" + exam);
        return exam;
    }

    @Override
    public Exam getByName(String name) throws DaoException {
        LOG.debug("Start getting exam by name ==> " + name);
        Exam exam = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(FIND_EXAM_BY_NAME_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, name);
            try (ResultSet resultSet = prStatement.executeQuery()) {
                ExamMapper mapper = new ExamMapper();
                while (resultSet.next()) {
                    exam = mapper.mapEntity(resultSet);
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find exam with name ==>" + name, e);
        }
        LOG.debug("Searched exam ==>" + exam);
        return exam;
    }

    @Override
    public List<Exam> findAll() throws DaoException {
        List<Exam> exams = new ArrayList<>();
        LOG.debug("Start searching all exams");
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_EXAMS_QUERY)) {
            LOG.trace("Resources are created");
            ExamMapper mapper = new ExamMapper();
            while (resultSet.next()) {
                exams.add(mapper.mapEntity(resultSet));
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find all exams", e);
        }
        LOG.debug("Searched exams ==>" + exams);
        return exams;
    }

    @Override
    public Map<String, Integer> findAllByCandidatesId(int id) throws DaoException {
        LOG.debug("Start searching candidates exams");
        Map<String, Integer> exams = new HashMap<>();
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(FIND_EXAM_BY_CANDIDATE_ID_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);
            try (ResultSet resultSet = prStatement.executeQuery()) {
                while (resultSet.next()) {
                    exams.put(resultSet.getString(ColumnLabel.EXAM_NAME.getName()),
                            resultSet.getInt(ColumnLabel.CANDIDATES_EXAM_MARK.getName()));
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find candidates exams by id ==> " + id, e);
        }
        LOG.debug("Searched candidates exams:" + exams.entrySet());
        return exams;
    }

    public boolean addCandidatesExam(int candidatesId, int examId, int mark) throws DaoException {
        LOG.debug("Start adding new candidates exam to db");
        boolean added;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_CANDIDATES_EXAM_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, candidatesId);
            prStatement.setInt(2, examId);
            prStatement.setInt(3, mark);
            added = prStatement.executeUpdate() == 1;

            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot add candidates exams with candidates id ==> " + candidatesId +
                    "examId => " + examId + ", mark = " + mark, e);
        }
        LOG.debug("New candidates exam is added ? ==> " + added);
        return added;
    }


    @Override
    public void save(Exam exam) throws WrongExecutedQueryException, AlreadyExistException,  DaoException {
        LOG.debug("Start saving exam ==> " + exam);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_EXAM_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, exam.getName());

            boolean saved = prStatement.executeUpdate() == 1;
            LOG.debug("exam " + exam + " is saved ? ==>" + saved);
            if (saved) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of exam " + exam);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Exam is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save exam ==> " + exam, e);
        }
    }

    @Override
    public void update(Exam exam) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating exam");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(UPDATE_EXAM_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, exam.getName());
            prStatement.setInt(2, exam.getId());

            boolean updated = prStatement.executeUpdate() == 1;
            LOG.debug("Exam " + exam + " is updated ? ==>" + updated);

            if (updated) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of exam " + exam);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Similar exam already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot update exam ==> " + exam, e);
        }
    }

    @Override
    public void delete(Exam exam) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start deleting exam");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(DELETE_EXAM_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, exam.getId());

            boolean deleted = prStatement.executeUpdate() == 1;
            LOG.debug("Exam " + exam + " removed ? => " + deleted);
            if (deleted) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of exam " + exam);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot delete exam ==> " + exam, e);
        }
    }

    /**
     * Extract exam from ResultSet
     */
    private static class ExamMapper implements EntityMapper<Exam> {
        @Override
        public Exam mapEntity(ResultSet rs) {
            Exam exam = new Exam();
            try {
                exam.setId(rs.getInt(ColumnLabel.EXAM_ID.getName()));
                exam.setName(rs.getString(ColumnLabel.EXAM_NAME.getName()));
            } catch (SQLException e) {
                LOG.error("Cannot extract exam from ResultSet", e);
            }
            LOG.debug("Extracted exam from ResultSet ==> " + exam);
            return exam;
        }
    }
}
