package dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;


// singleton
public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class);

    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    private DBManager() {
    }

    /**
     * Get connection from ConnectionPool
     *
     * @return DB connection
     */
    public Connection getConnection() {
        Connection connection = null;
        logger.debug("Start getting connection");
        try {
            Context envCtx = (Context) new InitialContext().lookup("java:comp/env");
            DataSource dataSource = (DataSource) envCtx.lookup("jdbc/admissions_committee");
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            logger.error("Cannot get connection", e);
        }
        logger.debug("Getting connection is finished");
        return connection;
    }

    /**
     * Rollbacks and close connection
     *
     * @param connection to be rollbacked and closed
     */
    public void rollbackAndClose(Connection connection) {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            logger.error("Cannot rollback and close connection", e);
        }
    }

    /**
     * Commits and close connection
     *
     * @param connection to be committed and closed
     */
    public void commitAndClose(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            connection.commit();
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Cannot commit and close connection", e);
        }
    }


}
