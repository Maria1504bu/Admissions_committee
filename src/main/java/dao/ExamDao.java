package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Exam;
import org.apache.log4j.Logger;
import util.EntityMapper;

public class ExamDao implements CrudDao<Exam> {
    private static final Logger logger = Logger.getLogger(ExamDao.class);

    private static final String FIND_EXAM_BY_ID_QUERY = "SELECT name, mark " +
            "FROM exams " +
            "WHERE id = ?";

    private static final String FIND_EXAM_BY_CANDIDATE_ID_QUERY = "SELECT e.id, e.name, e.mark FROM candidates_exams ce, exams e " +
            "WHERE ce.candidate_id = ? AND e.id = ce.exam_id";
    private static final String FIND_ALL_EXAMS_QUERY = "SELECT name, mark FROM exams";
    private final static String INSERT_EXAM_QUERY = "INSERT INTO exams (name, mark)" +
            "VALUES (?, ?)";
    private static final String DELETE_EXAM_QUERY = "DELETE FROM exams WHERE id = ?";
    private static final String UPDATE_EXAM_QUERY = "UPDATE exams SET name = ?, mark = ?" +
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

    @Override
    public List<Exam> findAll() throws DaoException {
        List<Exam> exams = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        logger.debug("Finding all exams ...");
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

    public List<Exam> findAllByCandidatesId(int id) throws DaoException {
        logger.debug("Start searching candidates exams");
        Connection conn = null;
        PreparedStatement prStatement = null;
        ResultSet resultSet = null;
        List<Exam> exams = null;
        EntityMapper mapper = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(FIND_EXAM_BY_CANDIDATE_ID_QUERY);
            prStatement.setInt(1, id);
            resultSet = prStatement.executeQuery();

            exams = new ArrayList<>();
            mapper = new ExamMapper();
            while (resultSet.next()) {
                exams.add((Exam) mapper.mapEntity(resultSet));
            }
        } catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find candidates exams by id ==> " + id, e);
            throw new DaoException("Cannot find candidates exams by id ==> " + id, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, resultSet);
        }
        return exams;
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
            prStatement.setInt(2, exam.getMark());
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
            prStatement.setInt(2, exam.getMark());
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
            exam.setMark(rs.getInt(ColumnLabel.EXAM_MARK.getName()));
        } catch (SQLException e) {
            logger.error("Cannot extract exam from ResultSet", e);
        }
        logger.debug("Extracted exam from ResultSet ==> " + exam);
        return exam;
    }
}
}
