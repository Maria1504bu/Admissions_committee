package dao;

import models.Candidate;
import models.Role;
import org.apache.log4j.Logger;
import util.EntityMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateDaoImpl implements CandidateDao {
    private static final Logger LOG = Logger.getLogger(CandidateDaoImpl.class);
    private final static String FIND_CANDIDATE_BY_LOGIN_QUERY = "SELECT l.id, l.email, l.password, r.name " +
            "FROM logins l, roles r " +
            "WHERE l.role_id = r.id AND l.email=?";

    private static final String FIND_CANDIDATE_BY_ID_QUERY = "SELECT l.id AS login_id, l.email, l.password, r.name, c.first_name, " +
            "c.father_name, c.second_name, c.certificate_url, cities.name " +
            "c.school_name, c.is_blocked, c.appl_date, " +
            "FROM logins l " +
            "INNER JOIN roles r ON l.role.id = r.id " +
            "INNER JOIN candidates c ON l.id = c.user_logins_id " +
            "INNER JOIN cities ON cities.id = c.city_id " +
            "WHERE l.id = ?;";

    private static final String FIND_ALL_CANDIDATES_QUERY = "SELECT c.login_id, l.email, l.password, r.name, " +
            "c.first_name, c.father_name, c.second_name, c.certificate_url, cities.name, " +
            "c.school_name, , c.is_blocked, c.appl_date " +
            "FROM candidates c, logins l, roles r, cities " +
            "WHERE c.login_id = l.id AND cities.id = с.city_id AND " +
            "r.name = `CANDIDATE` ";

    private static final String FIND_FACULTY_CANDIDATES_QUERY = "SELECT c.login_id, l.email, l.password, r.name, " +
            "c.first_name, c.father_name, c.second_name, c.certificate_url, cities.name, " +
            "c.school_name, , c.is_blocked, c.appl_date " +
            "FROM candidates c, logins l, roles r, cities, applications a " +
            "WHERE c.login_id = l.id AND cities.id = с.city_id AND " +
            "AND c.login_id = a.login_id AND r.name = `CANDIDATE` " +
            "AND a.faculties_id = ? LIMIT ? OFFSET ?";

    private static final String COUNT_CANDIDATES_BY_FACULTY_QUERY =
            "SELECT COUNT(c.login_id) " +
                    "FROM candidates c, logins l, applications a " +
                    "WHERE c.login_id = l.id AND l.id = a.login_id " +
                    "and a.faculties_id = ?";

    private final static String INSERT_CANDIDATE_INIT_QUERY =
            "INSERT INTO logins (`email`, `password`, `role_id`) " +
                    "VALUES (?, ?, id)" +
                    "FROM roles" +
                    "WHERE name = 'CANDIDATE'";

    private static final String INSERT_CANDIDATE_FINAL_QUERY =
            "INSERT INTO candidates(login_id, first_name, father_name, second_name, certificate_url, city_id, school_name, is_blocked, appl_date)" +
                    "VALUES (?, ?, ?, ?, ?, c.id, ?, ?, ?)" +
                    "FROM cities c " +
                    "WHERE name = ?";

    private static final String UPDATE_CANDIDATE_QUERY =
            "UPDATE candidates SET first_name = ?, father_name = ?, " +
                    "second_name = ?, certificate_url = ?, city_id = (SELECT id FROM cities WHERE name = ?), " +
                    "school_name = ?, appl_date = ?, is_blocked = ? " +
                    "WHERE user_logins_id = ?";

    private static final String BLOCK_CANDIDATE_BY_ADMIN_QUERY =
            "UPDATE CANDIDATE SET is_blocked = ?" +
                    "WHERE login_id = ?";
    private static final String DELETE_CANDIDATE_QUERY = "DELETE FROM user_logins WHERE id = ?";

    private final DataSource dataSource;

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public CandidateDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Candidate getByLogin(String login) throws DaoException {
        LOG.debug("Start searching candidate ==>" + login + " in db");
        Candidate candidate = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(FIND_CANDIDATE_BY_LOGIN_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, login);

            try (ResultSet resultSet = prStatement.executeQuery()) {
                while (resultSet.next()) {
                    candidate = Candidate.builder()
                            .id(resultSet.getInt(ColumnLabel.USER_ID.getName()))
                            .email(resultSet.getString(ColumnLabel.USER_EMAIL.getName()))
                            .password(resultSet.getString(ColumnLabel.USER_PASSWORD.getName()))
                            .role(Enum.valueOf(Role.class, resultSet.getString(ColumnLabel.ROLE_NAME.getName())))
                            .build();
                }
            }

            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find candidate with login ==>" + login, e);
        }
        LOG.debug("Searched candidate ==>" + candidate);
        return candidate;
    }

    @Override
    public Candidate getById(int id) throws DaoException {
        LOG.debug("Start getting candidate by id ==> " + id);
        Candidate candidate = null;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(FIND_CANDIDATE_BY_ID_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, id);

            try (ResultSet resultSet = prStatement.executeQuery()) {
                CandidateMapper mapper = new CandidateMapper();
                while (resultSet.next()) {
                    candidate = mapper.mapEntity(resultSet);
                }
            }

            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find candidate with id ==>" + id, e);
        }
        LOG.debug("Searched candidate ==>" + candidate);
        return candidate;
    }

    //TODO: Pagination
    @Override
    public List<Candidate> findAll() throws DaoException {
        List<Candidate> candidates = new ArrayList<>();
        LOG.debug("Finding all candidates ...");
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_CANDIDATES_QUERY)) {
            LOG.trace("Resources are created");

            CandidateMapper mapper = new CandidateMapper();
            while (resultSet.next()) {
                candidates.add(mapper.mapEntity(resultSet));
            }

            connection.commit();
            LOG.trace("Changes  at db was committed");
        } catch (SQLException e) {
            throw new DaoException("Cannot find all candidate", e);
        }
        LOG.debug("Found candidate ==> " + candidates);
        return candidates;
    }

    public void saveLogin(String email, String password) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving login ==> " + email);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_CANDIDATE_INIT_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, email);
            prStatement.setString(2, password);

            boolean saved = prStatement.executeUpdate() == 1;
            LOG.debug("Login with email ==>  " + email + " is saved ? ==>" + saved);
            if (saved) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of login ==>" + email);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Candidate is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save login ==> " + email, e);
        }
    }

    public void save(Candidate candidate) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start saving candidate ==> " + candidate);
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(INSERT_CANDIDATE_FINAL_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, candidate.getId());
            prStatement.setString(2, candidate.getFirstName());
            prStatement.setString(3, candidate.getFatherName());
            prStatement.setString(4, candidate.getSecondName());
            prStatement.setString(5, candidate.getCertificate_url());
            prStatement.setString(6, candidate.getCity());
            prStatement.setString(7, candidate.getSchoolName());
            prStatement.setObject(8, candidate.isBlocked());
            prStatement.setDate(9, candidate.getApplicationDate());

            boolean saved = prStatement.executeUpdate() == 1;
            LOG.debug("Candidate " + candidate + " is saved ? ==>" + saved);
            if (saved) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of candidate " + candidate);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Candidate is already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot save candidate ==> " + candidate, e);
        }
    }

    @Override
    public void update(Candidate candidate) throws WrongExecutedQueryException, AlreadyExistException, DaoException {
        LOG.debug("Start updating candidate");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(UPDATE_CANDIDATE_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setString(1, candidate.getEmail());
            prStatement.setString(2, candidate.getPassword());
            prStatement.setInt(3, candidate.getId());

            boolean updated = prStatement.executeUpdate() == 1;
            LOG.debug("Candidate " + candidate + " is updated ? ==>" + updated);
            if (updated) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of candidate " + candidate);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExistException("Similar candidate already exist", e);
        } catch (SQLException e) {
            throw new DaoException("Cannot update candidate ==> " + candidate, e);
        }
    }

    @Override
    public void blockCandidate(int id, boolean toBlock) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start to block candidate");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(BLOCK_CANDIDATE_BY_ADMIN_QUERY)) {
            LOG.debug("Resources are created");
            prStatement.setObject(1, toBlock);
            prStatement.setInt(2, id);
            boolean executed = prStatement.executeUpdate() == 1;
            LOG.debug("Candidate with id =" + id + " is blocked/unblocked ? ==>" + executed);
            if (executed) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Don`t (un)blocked candidate with id" + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot (un)block candidate", e);
        }
    }

    @Override
    public void delete(Candidate candidate) throws WrongExecutedQueryException, DaoException {
        LOG.debug("Start deleting candidate");
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(DELETE_CANDIDATE_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, candidate.getId());

            boolean deleted = prStatement.executeUpdate() == 1;
            LOG.debug("Candidate " + candidate + " is deleted ? ==>" + deleted);
            if (deleted) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
                throw new WrongExecutedQueryException("Operation is rollback! Wrong data of candidate " + candidate);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot delete candidate ==> " + candidate, e);
        }
    }

    @Override
    public int getCandidateListSize(int facultyId) {
        LOG.debug("Start getting candidateList size to faculty with id ==>" + facultyId);
        int candidateListSize = 0;
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(COUNT_CANDIDATES_BY_FACULTY_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, facultyId);

            ResultSet resultSet = prStatement.executeQuery();
            candidateListSize = resultSet.getInt(1);
            LOG.debug("CandidateList size = " + candidateListSize);

            if (candidateListSize != 0) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot get  candidateList size to faculty with id ==>" + facultyId, e);
        }
        return candidateListSize;
    }

    @Override
    public List<Candidate> getCandidatesForFaculty(int facultyId, int limit, int offset) {
        LOG.debug("Start getting candidateList to faculty with id ==>" + facultyId);
        List<Candidate> candidates = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement prStatement = connection.prepareStatement(FIND_FACULTY_CANDIDATES_QUERY)) {
            LOG.trace("Resources are created");
            prStatement.setInt(1, facultyId);
            prStatement.setInt(2, limit);
            prStatement.setInt(3, offset);

            try (ResultSet resultSet = prStatement.executeQuery()) {
                CandidateMapper mapper = new CandidateMapper();
                while (resultSet.next()) {
                    candidates.add(mapper.mapEntity(resultSet));
                }
            }
            LOG.debug("CandidateList size = " + candidates.size());

            if (candidates.size() != 0) {
                connection.commit();
                LOG.trace("Changes at db was committed");
            } else {
                connection.rollback();
                LOG.trace("Changes at db is rollback");
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot get  candidateList size to faculty with id ==>" + facultyId, e);
        }
        return candidates;
    }

    /**
     * Extract candidate from ResultSet
     */
    private static class CandidateMapper implements EntityMapper<Candidate> {
        @Override
        public Candidate mapEntity(ResultSet rs) {
            Candidate candidate = null;
            try {
                candidate = Candidate.builder()
                        .id(rs.getInt(ColumnLabel.USER_ID.getName()))
                        .email(rs.getString(ColumnLabel.USER_EMAIL.getName()))
                        .password(rs.getString(ColumnLabel.USER_PASSWORD.getName()))
                        .role(Enum.valueOf(Role.class, rs.getString(ColumnLabel.ROLE_NAME
                                .getName()).toUpperCase()))
                        .firstName(rs.getString(ColumnLabel.CANDIDATE_FIRSTNAME.getName()))
                        .fatherName(rs.getString(ColumnLabel.CANDIDATE_FATHER_NAME.getName()))
                        .secondName(rs.getString(ColumnLabel.CANDIDATE_SECOND_NAME.getName()))
                        .certificate_url(rs.getString(ColumnLabel.CANDIDATE_CERTIFICATE_URL.getName()))
                        .city(rs.getString(ColumnLabel.CITY_NAME.name()))
                        .schoolName(rs.getString(ColumnLabel.CANDIDATE_SCHOOL.getName()))
                        .isBlocked(rs.getObject(ColumnLabel.CANDIDATE_IS_BLOCKED.getName(), Boolean.class))
                        .applicationDate(rs.getDate(ColumnLabel.EXAM_ID.getName()))
                        .build();
            } catch (SQLException e) {
                LOG.error("Cannot extract candidate from ResultSet", e);
            }
            LOG.debug("Extracted candidate from ResultSet ==> " + candidate);
            return candidate;
        }
    }
}
