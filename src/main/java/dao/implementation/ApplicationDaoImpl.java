package dao.implementation;

import dao.AlreadyExistException;
import dao.ColumnLabel;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.ApplicationDao;
import models.Application;
import models.ApplicationStatus;
import models.Candidate;
import models.Faculty;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fulfill crud operations for Application entity with the db
 */
public class ApplicationDaoImpl implements ApplicationDao {
    private static final Logger LOG = Logger.getLogger(ApplicationDaoImpl.class);

    private static final String INSERT_APPL_QUERY =
            "INSERT INTO applications(user_logins_id, faculty_id, priority, status)VALUES(?, ?, ?, ?)";
    private static final String GET_APPL_BY_ID_QUERY =
            "SELECT * FROM applications WHERE id = ?;";
    private static final String GET_ALL_APPLS_QUERY =
            "SELECT * FROM applications";

    private static final String UPDATE_APPL_QUERY =
            "UPDATE applications SET priority = ?, status = ? WHERE id = ?";
    private static final String DELETE_APPL_QUERY =
            "DELETE FROM applications WHERE id = ?";

    private final DataSource dataSource;

    public ApplicationDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Get application entity from db
     *
     * @param id of entity
     * @return entity by id
     */
    @Override
    public Application getById(int id) throws DaoException {
        LOG.debug("Start getting application by id ==> " + id);
        Application application = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(GET_APPL_BY_ID_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);
            LOG.trace("set parameter into prepareStatement" + id);
            try (ResultSet resultSet = prStatement.executeQuery()) {
                ApplicationMapper mapper = new ApplicationMapper();
                while (resultSet.next()) {
                    application = mapper.mapEntity(resultSet);
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find application with id ==>" + id, e);
        }
        LOG.debug("Searched application ==>" + application);
        return application;
    }

    /**
     * Get list of applications placed by candidate OR faculty
     *
     * @return list of applications entities
     */
    @Override
    public List<Application> findAll() throws DaoException {
        List<Application> applications = new ArrayList<>();
        LOG.debug("Start searching all applications");
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_APPLS_QUERY)) {
            LOG.trace("Resources are created");

            ApplicationDaoImpl.ApplicationMapper mapper = new ApplicationDaoImpl.ApplicationMapper();
            while (resultSet.next()) {
                applications.add(mapper.mapEntity(resultSet));
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find all applications", e);
        }
        LOG.debug("Searched applications ==>" + applications);
        return applications;
    }


    /**
     * Insert application entity into db
     *
     * @param application entity to save
     */
    @Override
    public void save(Application application) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving application ==> " + application);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_APPL_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, application.getCandidate().getId());
            prStatement.setInt(2, application.getFaculty().getId());
            prStatement.setInt(3, application.getPriority());
            prStatement.setString(3, application.getApplicationStatus());

            boolean saved = prStatement.executeUpdate() == 1;
            LOG.debug("application " + application + " is saved ? ==>" + saved);
            if (saved) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of application " + application);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Application is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save application ==> " + application, e);
        }
    }

    /**
     * Update application in the db
     *
     * @param application entity
     */
    @Override
    public void update(Application application) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating application");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(UPDATE_APPL_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, application.getPriority());
            prStatement.setString(2, application.getApplicationStatus());
            prStatement.setInt(3, application.getId());

            boolean updated = prStatement.executeUpdate() == 1;
            LOG.debug("Application " + application + " is updated ? ==>" + updated);

            if (updated) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of application " + application);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Similar application already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot update application ==> " + application, e);
        }
    }

    /**
     * Delete application entity from the db
     *
     * @param id application
     */
    @Override
    public void delete(int id) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start deleting application");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(DELETE_APPL_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);

            boolean deleted = prStatement.executeUpdate() == 1;
            LOG.debug("Application with id " + id + " removed ? => " + deleted);
            if (deleted) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of application id" + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot delete application with id ==> " + id, e);
        }
    }

    /**
     * Extracts an application entity from the result set row.
     */
    //TODO: in service find suitable candidate and faculty
    private static class ApplicationMapper implements EntityMapper<Application> {
        @Override
        public Application mapEntity(ResultSet rs) {
            Application application = new Application();
            Candidate candidate = new Candidate();
            application.setCandidate(candidate);
            Faculty faculty = new Faculty();
            application.setFaculty(faculty);
            try {
                application.setId(rs.getInt(ColumnLabel.APPL_ID.getName()));
                application.getFaculty().setId(rs.getInt(ColumnLabel.FACULTY_ID.getName()));
                application.getFaculty().getNamesList().add(rs.getString(ColumnLabel.FACULTY_NAME.getName()));
                application.setPriority(rs.getInt(ColumnLabel.APPL_PRIORITY.getName()));
                application.setApplicationStatus(ApplicationStatus.values()[rs.getInt(ColumnLabel.APPL_STATUS.getName())]);
                return application;
            } catch (SQLException e) {
                LOG.error("Cannot extract application from ResultSet", e);
            }
            LOG.debug("Extracted application from ResultSet ==> " + application);
            return application;
        }
    }
}
