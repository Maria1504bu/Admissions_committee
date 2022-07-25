package dao;

import models.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import util.EntityMapper;

public class SubjectDao implements CrudDao<Subject> {
    private static final Logger logger = Logger.getLogger(SubjectDao.class);

    private static final String FIND_SUBJECT_BY_ID_QUERY = "SELECT name, mark " +
            "FROM subjects " +
            "WHERE id = ?";

    private static final String FIND_ALL_SUBJECTS_QUERY = "SELECT name, mark FROM subjects";
    private final static String INSERT_SUBJECT_QUERY = "INSERT INTO subjects (name, mark)" +
            "VALUES (?, ?)";
    private static final String DELETE_SUBJECT_QUERY = "DELETE FROM subjects WHERE id = ?";
    private static final String UPDATE_SUBJECT_QUERY = "UPDATE subjects SET name = ?, mark = ?" +
            " WHERE id = ?";

    @Override
    public Subject getById(int id) throws DaoException{
        logger.debug("Start getting subject by id ==> " + id);
        Subject subject = null;
        Connection conn = null;
        PreparedStatement prStatement = null;
        ResultSet rs = null;
        try{
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(FIND_SUBJECT_BY_ID_QUERY);
            prStatement.setInt(1, id);
            rs = prStatement.executeQuery();

            SubjectMapper mapper = new SubjectMapper();
            while(rs.next()) {
                subject = mapper.mapEntity(rs);
            }
        } catch(SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find subject by id ==> " + id, e);
            throw new DaoException("Cannot find subject with id ==>" + id, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, rs);
        }
        logger.debug("Searched Subject ==>" +  subject);
        return subject;
    }

    @Override
    public List<Subject> findAll() throws DaoException{
        List<Subject> subjects = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        logger.debug("Finding all subjects ...");
        try {
            conn = DBManager.getInstance().getConnection();
            statement = conn.createStatement();
            rs = statement.executeQuery(FIND_ALL_SUBJECTS_QUERY);

            subjects = new ArrayList<>();
            SubjectMapper mapper = new SubjectMapper();
            while(rs.next()){
                subjects.add(mapper.mapEntity(rs));
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find all subjects", e);
            throw new DaoException("Cannot find all subjects", e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, statement, rs);
        }
        return subjects;
    }

    @Override
    public void save(Subject subject) throws DaoException {
        logger.debug("Start saving subject ==> " + subject);
        Connection conn = null;
        PreparedStatement prStatement = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(INSERT_SUBJECT_QUERY);
            prStatement.setString(1, subject.getName());
            prStatement.setInt(2, subject.getMark());
            int saved = prStatement.executeUpdate();
            logger.debug("Subject " + subject + " is saved ? ==>" + (saved == 1));
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot save subject ==> " + subject, e);
            throw new DaoException("Cannot save subject ==> " + subject, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
        }
    }

    @Override
    public void update(Subject subject) throws DaoException{
        logger.debug("Start updating Subject");
        Connection conn = null;
        PreparedStatement prStatement = null;
        try{
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(UPDATE_SUBJECT_QUERY);
            prStatement.setString(1, subject.getName());
            prStatement.setInt(2, subject.getMark());
            int updatedFaculties = prStatement.executeUpdate();
            logger.debug(updatedFaculties == 1 ? "Subject is updated": "Something went wrong");
        } catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot update subject ==> " + subject, e);
            throw new DaoException("Cannot update Subject ==> " + subject, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
        }
    }

    @Override
    public void delete(int id) throws DaoException{
        Connection conn = null;
        PreparedStatement prStatement = null;
        try{
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(DELETE_SUBJECT_QUERY);
            prStatement.setInt(1, id);
            int deleted = prStatement.executeUpdate();
            logger.debug("Subject with id " + id + " removed ? => " + (deleted == 1));
            prStatement.close();
        } catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot delete subject with id ==> " + id , e);
            throw new DaoException("Cannot delete subject with id ==> " + id , e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
        }
    }

    /**
     * Extract Subject from ResultSet
     */
    private static class SubjectMapper implements EntityMapper<Subject> {
        @Override
        public Subject mapEntity(ResultSet rs) {
            Subject subject = new Subject();
            try {
                subject.setId(rs.getInt(ColumnLabel.SUBJECT_ID.getName()));
                subject.setName(rs.getString(ColumnLabel.SUBJECT_NAME.getName()));
                subject.setMark(rs.getInt(ColumnLabel.SUBJECT_MARK.getName()));
            } catch (SQLException e) {
                logger.error("Cannot extract subject from ResultSet", e);
            }
            logger.debug("Extracted subject from ResultSet ==> " + subject);
            return subject;
        }
    }
}
