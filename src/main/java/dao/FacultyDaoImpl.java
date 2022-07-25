package dao;

import models.Faculty;
import models.Role;
import models.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import models.Subject;
import org.apache.log4j.Logger;
import util.EntityMapper;

public class FacultyDaoImpl implements CrudDao<Faculty> {
    private static final Logger logger = Logger.getLogger(FacultyDaoImpl.class);

    private static final String FIND_FACULTY_BY_ID_QUERY = "SELECT name, budget_places, all_places " +
            "FROM faculties " +
            "WHERE id = ?";

    private static final String FIND_ALL_FACULTIES_QUERY = "SELECT name, budget_places, all_places FROM faculties";
    private final static String INSERT_FACULTY_QUERY = "INSERT INTO faculties (name, budget_places, all_places)" +
            "VALUES (?, ?, ?)";
    private static final String DELETE_FACULTY_QUERY = "DELETE FROM faculties WHERE id = ?";
    private static final String UPDATE_FACULTY_QUERY = "UPDATE faculties SET name = ?, budget_places = ?, all_places = ?" +
            " WHERE id = ?";

    @Override
    public Faculty getById(int id) throws DaoException{
        logger.debug("Start getting faculty by id ==> " + id);
        Faculty faculty = null;
        Connection conn = null;
        PreparedStatement prStatement = null;
        ResultSet rs = null;
        try{
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(FIND_FACULTY_BY_ID_QUERY);
            prStatement.setInt(1, id);
            rs = prStatement.executeQuery();

            FacultyMapper mapper = new FacultyMapper();
            while(rs.next()) {
                faculty = mapper.mapEntity(rs);
            }
        } catch(SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find faculty by id ==> " + id, e);
            throw new DaoException("Cannot find faculty with id ==>" + id, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, rs);
        }
        logger.debug("Searched faculty ==>" +  faculty);
        return faculty;
    }

    @Override
    public List<Faculty> findAll() throws DaoException{
        List<Faculty> faculties = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        logger.debug("Finding all faculties ...");
        try {
            conn = DBManager.getInstance().getConnection();
            statement = conn.createStatement();
            rs = statement.executeQuery(FIND_ALL_FACULTIES_QUERY);

            faculties = new ArrayList<>();
            FacultyMapper mapper = new FacultyMapper();
            while(rs.next()){
                faculties.add(mapper.mapEntity(rs));
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find all faculties", e);
            throw new DaoException("Cannot find all faculties", e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, statement, rs);
        }
        return faculties;
    }

    @Override
    public void save(Faculty faculty) throws DaoException {
        logger.debug("Start saving faculty ==> " + faculty);
        Connection conn = null;
        PreparedStatement prStatement = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(INSERT_FACULTY_QUERY);
            prStatement.setString(1, faculty.getName());
            prStatement.setInt(2, faculty.getBudget_places());
            prStatement.setInt(3, faculty.getAll_places());
            int saved = prStatement.executeUpdate();
            logger.debug("Faculty " + faculty + " is saved ? ==>" + (saved == 1));
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot save faculty ==> " + faculty, e);
            throw new DaoException("Cannot save faculty ==> " + faculty, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
        }
    }

    @Override
    public void update(Faculty faculty) throws DaoException{
        logger.debug("Start updating faculty");
        Connection conn = null;
        PreparedStatement prStatement = null;
        try{
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(UPDATE_FACULTY_QUERY);
            prStatement.setString(1, faculty.getName());
            prStatement.setInt(2, faculty.getBudget_places());
            prStatement.setInt(3, faculty.getAll_places());
            int updatedFaculties = prStatement.executeUpdate();
            logger.debug(updatedFaculties == 1 ? "Faculty is updated": "Something went wrong");
        } catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot update faculty ==> " + faculty, e);
            throw new DaoException("Cannot update faculty ==> " + faculty, e);
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
            prStatement = conn.prepareStatement(DELETE_FACULTY_QUERY);
            prStatement.setInt(1, id);
            int deleted = prStatement.executeUpdate();
            logger.debug("Faculty with id " + id + " removed ? => " + (deleted == 1));
            prStatement.close();
        } catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot delete faculty with id ==> " + id , e);
            throw new DaoException("Cannot delete faculty with id ==> " + id , e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
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
                faculty.setName(rs.getString(ColumnLabel.FACULTY_NAME.getName()));
                faculty.setBudget_places(rs.getInt(ColumnLabel.FACULTY_BUDGET_PLACES.getName()));
                faculty.setAll_places(rs.getInt(ColumnLabel.FACULTY_ALL_PLACES.getName()));
            } catch (SQLException e) {
                logger.error("Cannot extract Faculty from ResultSet", e);
            }
            logger.debug("Extracted Faculty from ResultSet ==> " + faculty);
            return faculty;
        }
    }
}
