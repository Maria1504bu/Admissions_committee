package dao;

import models.Faculty;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDaoImpl implements FacultyDao {
    private static final Logger LOG = Logger.getLogger(FacultyDaoImpl.class);

    private static final String FIND_FACULTY_BY_ID_QUERY = "SELECT name, budget_places, all_places " +
            "FROM faculties " +
            "WHERE id = ?";

    private static final String FIND_ALL_FACULTIES_QUERY = "SELECT name, budget_places, all_places FROM faculties";
    private final static String INSERT_FACULTY_QUERY = "INSERT INTO faculties (name, budget_places, all_places)" +
            "VALUES (?, ?, ?)";
    private static final String DELETE_FACULTY_QUERY = "DELETE FROM faculties WHERE id = ?";
    private static final String UPDATE_FACULTY_QUERY = "UPDATE faculties SET name = ?, budget_places = ?, all_places = ?" +
            " WHERE id = ?";
    private final DataSource dataSource;

    public FacultyDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Faculty getById(int id) throws DaoException {
        LOG.debug("Start getting faculty by id ==> " + id);
        Faculty faculty = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(FIND_FACULTY_BY_ID_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);

            try (ResultSet resultSet = prStatement.executeQuery()) {
                FacultyMapper mapper = new FacultyMapper();
                while (resultSet.next()) {
                    faculty = mapper.mapEntity(resultSet);
                }
            }
            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find faculty with id ==>" + id, e);
        }
        LOG.debug("Searched faculty ==>" + faculty);
        return faculty;
    }

    @Override
    public List<Faculty> findAll() throws DaoException {
        List<Faculty> faculties = new ArrayList<>();
        LOG.debug("Finding all faculties ...");
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_FACULTIES_QUERY)) {
            LOG.trace("Resources are created");

            FacultyMapper mapper = new FacultyMapper();
            while (resultSet.next()) {
                faculties.add(mapper.mapEntity(resultSet));
            }

            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find all faculties", e);
        }
        LOG.debug("Found faculties ==>" + faculties);
        return faculties;
    }

    // TODO: boolean
    @Override
    public void save(Faculty faculty) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving faculty ==> " + faculty);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_FACULTY_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, faculty.getName());
            prStatement.setInt(2, faculty.getBudget_places());
            prStatement.setInt(3, faculty.getAll_places());

            boolean saved = prStatement.executeUpdate() == 1;
            LOG.debug("Faculty " + faculty + " is saved ? ==>" + saved);
            if (saved) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of faculty " + faculty);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Faculty is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save faculty ==> " + faculty, e);
        }
    }

    @Override
    public void update(Faculty faculty) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating faculty");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(UPDATE_FACULTY_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, faculty.getName());
            prStatement.setInt(2, faculty.getBudget_places());
            prStatement.setInt(3, faculty.getAll_places());

            boolean updated = prStatement.executeUpdate() == 1;
            LOG.debug("Faculty " + faculty + " is updated ? ==>" + updated);
            if (updated) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of faculty " + faculty);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Similar faculty already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot update faculty ==> " + faculty, e);
        }
    }

    @Override
    public void delete(Faculty faculty) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start deleting faculty");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(DELETE_FACULTY_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, faculty.getId());

            boolean deleted = prStatement.executeUpdate() == 1;
            LOG.debug("Faculty " + faculty + " is deleted ? ==>" + deleted);
            if (deleted) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of faculty " + faculty);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot delete faculty ==> " + faculty, e);
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
                LOG.error("Cannot extract Faculty from ResultSet", e);
            }
            LOG.debug("Extracted Faculty from ResultSet ==> " + faculty);
            return faculty;
        }
    }
}
