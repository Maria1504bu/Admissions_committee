package dao;

import models.Role;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.apache.log4j.Logger;
import util.EntityMapper;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private final static String FIND_USER_BY_LOGIN_QUERY = "SELECT u.id, u.email, u.password " +
            "FROM users u " +
            "WHERE u.email = ?";

    //            "SELECT u.id, u.email, u.password, r.name " +
//            "FROM users u AND roles r " +
//            "WHERE u.email = ? AND u.roles_id=r.id";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT u.id, u.email, u.password " +
            "FROM users u " +
            "WHERE u.id = ?";;

    private final static String INSERT_USER_QUERY = "INSERT INTO users (email, password, role_id)" +
            "VALUES (?, ?, ?)";

    @Override
    public User getByLogin(String login) {
        User user = null;
        Connection conn = null;
        PreparedStatement prSt = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prSt = conn.prepareStatement(FIND_USER_BY_LOGIN_QUERY);
            prSt.setString(1, login);
            rs = prSt.executeQuery();

            UserMapper mapper = new UserMapper();
            while (rs.next()) {
                user = mapper.mapEntity(rs);
            }
            prSt.close();
            rs.close();
        } catch (SQLException e) {
            logger.error("Cannot find user with login " + login, e);
            DBManager.getInstance().rollbackAndClose(conn);
        } finally {
            DBManager.getInstance().commitAndClose(conn);
        }
        return user;
    }

    @Override
    public User getById(int id) {
        User user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(FIND_USER_BY_ID_QUERY);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            UserMapper mapper = new UserMapper();
            while(rs.next()) {
                user = mapper.mapEntity(rs);
            }
            ps.close();
            rs.close();
        } catch(SQLException e){
            logger.error("Cannot find user by id " + id, e);
            DBManager.getInstance().rollbackAndClose(conn);
        } finally {
            DBManager.getInstance().commitAndClose(conn);
        }
        return user;
    }

    @Override
    public void save(User user) {
        logger.debug("Start saving user: " + user);
        Connection conn = null;
        PreparedStatement prSt = null;
        try {
            conn = DBManager.getInstance().getConnection();
            prSt = conn.prepareStatement(INSERT_USER_QUERY);
            prSt.setString(1, user.getEmail());
            prSt.setString(2, user.getPassword());
            //TODO: change role
            prSt.setObject(3, 1);
            boolean saved = prSt.execute();
        } catch (SQLException e) {
            logger.error("Cannot save user  " + user, e);
            DBManager.getInstance().rollbackAndClose(conn);
        } finally {
            DBManager.getInstance().commitAndClose(conn);
        }
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<User> findAll() {
        return null;
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
                //TODO: change get role
                //   user.setRole((Role) rs.getObject(ColumnLabel.ROLE_NAME.name()));
            } catch (SQLException e) {
                logger.error("Cannot extract user from ResultSet", e);
            }
            return user;
        }
    }
}
