package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Exam;
import org.apache.log4j.Logger;
import util.EntityMapper;

public class ExamDao implements CrudDao<Exam> {
    private static final Logger logger = Logger.getLogger(ExamDao.class);

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

    @Override
    public Exam getById(int id) throws DaoException {
        logger.debug("Start getting exam by id ==> " + id);
        Exam exam = null;
        Connection conn = null;
        PreparedStatement prStatement = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(FIND_EXAM_BY_ID_QUERY);
            prStatement.setInt(1, id);
            rs = prStatement.executeQuery();

            ExamMapper mapper = new ExamMapper();
            while (rs.next()) {
                exam = mapper.mapEntity(rs);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find exam by id ==> " + id, e);
            throw new DaoException("Cannot find exam with id ==>" + id, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, rs);
        }
        logger.debug("Searched exam ==>" + exam);
        return exam;
    }

    public Exam getByName(String name) throws DaoException {
        logger.debug("Start getting exam by name ==> " + name);
        Exam exam = null;
        Connection conn = null;
        PreparedStatement prStatement = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(FIND_EXAM_BY_NAME_QUERY);
            prStatement.setString(1, name);
            rs = prStatement.executeQuery();

            ExamMapper mapper = new ExamMapper();
            while (rs.next()) {
                exam = mapper.mapEntity(rs);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find exam by name ==> " + name, e);
            throw new DaoException("Cannot find exam with name ==>" + name, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, rs);
        }
        logger.debug("Searched exam ==>" + exam);
        return exam;
    }

    @Override
    public List<Exam> findAll() throws DaoException {
        List<Exam> exams = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        logger.debug("Start searching all exams");
        try {
            conn = DBManager.getInstance().getConnection();
            statement = conn.createStatement();
            rs = statement.executeQuery(FIND_ALL_EXAMS_QUERY);

            exams = new ArrayList<>();
            ExamMapper mapper = new ExamMapper();
            while (rs.next()) {
                exams.add(mapper.mapEntity(rs));
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find all exams", e);
            throw new DaoException("Cannot find all exams", e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, statement, rs);
        }
        return exams;
    }

    public Map<String, Integer> findAllByCandidatesId(int id) throws DaoException {
        logger.debug("Start searching candidates exams");
        Connection conn = null;
        PreparedStatement prStatement = null;
        ResultSet resultSet = null;
        Map<String, Integer> exams = null;
        EntityMapper mapper = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(FIND_EXAM_BY_CANDIDATE_ID_QUERY);
            prStatement.setInt(1, id);
            resultSet = prStatement.executeQuery();

            exams = new HashMap<>();
            mapper = new ExamMapper();
            while (resultSet.next()) {
                exams.put(resultSet.getString(ColumnLabel.EXAM_NAME.getName()),
                        resultSet.getInt(ColumnLabel.CANDIDATES_EXAM_MARK.getName()));
            }
        } catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find candidates exams by id ==> " + id, e);
            throw new DaoException("Cannot find candidates exams by id ==> " + id, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, resultSet);
        }
        logger.debug("Searched candidates exams:" + exams.entrySet());
        return exams;
    }

    public boolean addCandidatesExam(int candidatesId, int examId, int mark) throws DaoException{
        logger.debug("Start adding new candidates exam to db");
        Connection conn = null;
        PreparedStatement prStatement = null;
        boolean added = false;
        try{
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(INSERT_CANDIDATES_EXAM_QUERY);
            prStatement.setInt(1, candidatesId);
            prStatement.setInt(2, examId);
            prStatement.setInt(3, mark);
            added = prStatement.executeUpdate() == 1;
        } catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot add candidates exam with candidates id ==> " + candidatesId +
                    "examId => " + examId + ", mark = " + mark, e);
            throw new DaoException("Cannot add candidates exams with candidates id ==> " + candidatesId +
                    "examId => " + examId + ", mark = " + mark, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
        }
        logger.debug("New candidates exam is added ? ==> " + added);
        return added;
    }



    @Override
    public void save(Exam exam) throws DaoException {
        logger.debug("Start saving exam ==> " + exam);
        Connection conn = null;
        PreparedStatement prStatement = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(INSERT_EXAM_QUERY);
            prStatement.setString(1, exam.getName());
            int saved = prStatement.executeUpdate();
            logger.debug("exam " + exam + " is saved ? ==>" + (saved == 1));
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot save exam ==> " + exam, e);
            throw new DaoException("Cannot save exam ==> " + exam, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
        }
    }

    @Override
    public void update(Exam exam) throws DaoException {
        logger.debug("Start updating exam");
        Connection conn = null;
        PreparedStatement prStatement = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(UPDATE_EXAM_QUERY);
            prStatement.setString(1, exam.getName());
            prStatement.setInt(2, exam.getId());
            int updatedFaculties = prStatement.executeUpdate();
            logger.debug(updatedFaculties == 1 ? "exam is updated" : "Something went wrong");
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot update exam ==> " + exam, e);
            throw new DaoException("Cannot update exam ==> " + exam, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        Connection conn = null;
        PreparedStatement prStatement = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(DELETE_EXAM_QUERY);
            prStatement.setInt(1, id);
            int deleted = prStatement.executeUpdate();
            logger.debug("exam with id " + id + " removed ? => " + (deleted == 1));
            prStatement.close();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot delete exam with id ==> " + id, e);
            throw new DaoException("Cannot delete exam with id ==> " + id, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
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
            logger.error("Cannot extract exam from ResultSet", e);
        }
        logger.debug("Extracted exam from ResultSet ==> " + exam);
        return exam;
    }
}
}
