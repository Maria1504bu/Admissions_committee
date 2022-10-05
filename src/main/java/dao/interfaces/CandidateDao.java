package dao.interfaces;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import models.Candidate;

import java.util.List;

public interface CandidateDao {
     Candidate getById(int id) throws DaoException;

     //TODO: Pagination
     List<Candidate> findAll() throws DaoException;

     void saveLogin(String email, String password) throws WrongExecutedQueryException, AlreadyExistException, DaoException;

     void update(Candidate candidate) throws WrongExecutedQueryException, AlreadyExistException, DaoException;

     void blockCandidate(int id) throws WrongExecutedQueryException;

     void delete(int id) throws WrongExecutedQueryException, DaoException;

     int getCandidateListSize(int facultyId);
     List<Candidate> getCandidatesForFaculty(int facultyId, int limit, int offset);

     Candidate getByLogin(String login) throws DaoException;

     void save(Candidate candidate) throws WrongExecutedQueryException, AlreadyExistException, DaoException;

     void saveCertificate(int id, String certificateName) throws WrongExecutedQueryException;
}
