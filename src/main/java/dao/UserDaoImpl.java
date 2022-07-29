package dao;

import models.Role;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import util.EntityMapper;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private final static String FIND_USER_BY_LOGIN_QUERY = "SELECT u.id, u.email, u.password " +
            "FROM users u " +
            "WHERE u.email = ?";
    private static final String FIND_ALL_USERS_QUERY = "SELECT u.id, u.email, u.password FROM users";

    private static final String FIND_USER_BY_ID_QUERY = "SELECT u.id, u.email, u.password " +
            "FROM users u " +
            "WHERE u.id = ?";

    private final static String INSERT_USER_QUERY = "INSERT INTO users (email, password, role_id)" +
            "VALUES (?, ?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, password = ? WHERE id = ?";

    @Override
    public User getByLogin(String login) throws DaoException{
        logger.debug("Start searching user ==>" +  login + " in db");
        User user = null;
        Connection conn = null;
        PreparedStatement prStatement = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(FIND_USER_BY_LOGIN_QUERY);
            prStatement.setString(1, login);
            rs = prStatement.executeQuery();

            UserMapper mapper = new UserMapper();
            while (rs.next()) {
                user = mapper.mapEntity(rs);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find user with login ==>" + login, e);
            throw new DaoException("Cannot find user with login ==>" + login, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, rs);
        }
        logger.debug("Searched user ==>" +  user);
        return user;
    }

    @Override
    public User getById(int id) throws DaoException{
        logger.debug("Start getting user by id ==> " + id);
        User user = null;
        Connection conn = null;
        PreparedStatement prStatement = null;
        ResultSet rs = null;
        try{
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(FIND_USER_BY_ID_QUERY);
            prStatement.setInt(1, id);
            rs = prStatement.executeQuery();

            UserMapper mapper = new UserMapper();
            while(rs.next()) {
                user = mapper.mapEntity(rs);
            }
        } catch(SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find user by id ==> " + id, e);
            throw new DaoException("Cannot find user with id ==>" + id, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, rs);
        }
        logger.debug("Searched user ==>" +  user);
        return user;
    }

    @Override
    public List<User> findAll() throws DaoException{
        List<User> users = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        logger.debug("Finding all users ...");
        try {
            conn = DBManager.getInstance().getConnection();
            statement = conn.createStatement();
            rs = statement.executeQuery(FIND_ALL_USERS_QUERY);

            users = new ArrayList<>();
            UserMapper mapper = new UserMapper();
            while(rs.next()){
                users.add(mapper.mapEntity(rs));
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot find all user", e);
            throw new DaoException("Cannot find all user", e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, statement, rs);
        }
        return users;
    }

    @Override
    public void save(User user) throws DaoException {
        logger.debug("Start saving user ==> " + user);
        Connection conn = null;
        PreparedStatement prStatement = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(INSERT_USER_QUERY);
            prStatement.setString(1, user.getEmail());
            prStatement.setString(2, user.getPassword());
            prStatement.setObject(3, 1);
            int saved = prStatement.executeUpdate();
            logger.debug("User " + user + " is saved ? ==>" + (saved == 1));
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot save user ==> " + user, e);
            throw new DaoException("Cannot save user ==> " + user, e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);

        }
    }

    @Override
    public void update(User user) throws DaoException{
        logger.debug("Start updating user");
        Connection conn = null;
        PreparedStatement prStatement = null;
        try{
            conn = DBManager.getInstance().getConnection();
            prStatement = conn.prepareStatement(UPDATE_USER_QUERY);
            prStatement.setString(1, user.getEmail());
            prStatement.setString(2, user.getPassword());
            prStatement.setInt(3, user.getId());
            int updatedUsers = prStatement.executeUpdate();
            logger.debug(updatedUsers == 1 ? "User is updated": "Something went wrong");
        } catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot update user ==> " + user, e);
            throw new DaoException("Cannot update user ==> " + user, e);
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
            prStatement = conn.prepareStatement(DELETE_USER_QUERY);
            prStatement.setInt(1, id);
            int deleted = prStatement.executeUpdate();
            logger.debug("User with id " + id + " removed ? => " + (deleted == 1));
            prStatement.close();
        } catch (SQLException e){
            DBManager.getInstance().rollbackAndClose(conn);
            logger.error("Cannot delete user with id ==> " + id , e);
            throw new DaoException("Cannot delete user with id ==> " + id , e);
        } finally {
            DBManager.getInstance().commitAndClose(conn, prStatement, null);
        }
    }

    /**
     * Extract user from ResultSet
     */
    private static class UserMapper implements EntityMapper<User> {
        @Override
        public User mapEntity(ResultSet rs) {
            User user = new User();
            try {
                user.setId(rs.getInt(ColumnLabel.USER_ID.getName()));
                user.setEmail(rs.getString(ColumnLabel.USER_EMAIL.getName()));
                user.setPassword(rs.getString(ColumnLabel.USER_PASSWORD.getName()));
                user.setRole(Role.CANDIDATE);
            } catch (SQLException e) {
                logger.error("Cannot extract user from ResultSet", e);
            }
            logger.debug("Extracted user from ResultSet ==> " + user);
            return user;
        }
    }
}
