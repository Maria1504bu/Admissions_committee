package dao;

import models.Role;
import models.User;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);
    private final static String FIND_USER_BY_LOGIN_QUERY = "SELECT u.id, u.email, u.password, r.name " +
            "FROM users u, roles r " +
            "WHERE u.email = ? AND u.role_id = r.id";
    private static final String FIND_ALL_USERS_QUERY = "SELECT u.id, u.email, u.password, r.name " +
            "FROM users u, roles r " +
            "WHERE u.role_id = r.id";

    private static final String FIND_USER_BY_ID_QUERY = "SELECT u.id, u.email, u.password, r.name " +
            "FROM users u, roles r " +
            "WHERE u.id = ? AND u.role_id = r.id";

    private final static String INSERT_USER_QUERY = "INSERT INTO users (email, password, role_id)" +
            "VALUES (?, ?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, password = ? WHERE id = ?";

    private final DataSource dataSource;

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getByLogin(String login) throws DaoException {
        LOG.debug("Start searching user ==>" + login + " in db");
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, login);

            try (ResultSet resultSet = prStatement.executeQuery()) {
                UserMapper mapper = new UserMapper();
                while (resultSet.next()) {
                    user = mapper.mapEntity(resultSet);
                }
            }

            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find user with login ==>" + login, e);
        }
        LOG.debug("Searched user ==>" + user);
        return user;
    }

    @Override
    public User getById(int id) throws DaoException {
        LOG.debug("Start getting user by id ==> " + id);
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(FIND_USER_BY_ID_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);

            try (ResultSet resultSet = prStatement.executeQuery()) {
                UserMapper mapper = new UserMapper();
                while (resultSet.next()) {
                    user = mapper.mapEntity(resultSet);
                }
            }

            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find user with id ==>" + id, e);
        }
        LOG.debug("Searched user ==>" + user);
        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        LOG.debug("Finding all users ...");
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS_QUERY)) {
            LOG.trace("Resources are created");

            UserMapper mapper = new UserMapper();
            while (resultSet.next()) {
                users.add(mapper.mapEntity(resultSet));
            }

            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find all user", e);
        }
        LOG.debug("Found user ==> " + users);
        return users;
    }

    @Override
    public void save(User user) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving user ==> " + user);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_USER_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, user.getEmail());
            prStatement.setString(2, user.getPassword());
            prStatement.setInt(3, 2);

            boolean saved = prStatement.executeUpdate() == 1;
            LOG.debug("User " + user + " is saved ? ==>" + saved);
            if (saved) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of user " + user);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("User is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save user ==> " + user, e);
        }
    }

    @Override
    public void update(User user) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating user");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(UPDATE_USER_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, user.getEmail());
            prStatement.setString(2, user.getPassword());
            prStatement.setInt(3, user.getId());

            boolean updated = prStatement.executeUpdate() == 1;
            LOG.debug("User " + user + " is updated ? ==>" + updated);
            if (updated) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of user " + user);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Similar user already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot update user ==> " + user, e);
        }
    }

    @Override
    public void delete(User user) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start deleting user");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(DELETE_USER_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, user.getId());

            boolean deleted = prStatement.executeUpdate() == 1;
            LOG.debug("User " + user + " is deleted ? ==>" + deleted);
            if (deleted) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of user " + user);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot delete user ==> " + user, e);
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
                user.setRole(Enum.valueOf(Role.class, rs.getString(ColumnLabel.ROLE_NAME
                        .getName()).toUpperCase()));
            } catch (SQLException e) {
                LOG.error("Cannot extract user from ResultSet", e);
            }
            LOG.debug("Extracted user from ResultSet ==> " + user);
            return user;
        }
    }
}
