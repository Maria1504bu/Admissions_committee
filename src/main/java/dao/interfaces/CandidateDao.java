package dao.interfaces;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import models.Candidate;

import java.util.List;

public interface CandidateDao extends CrudDao<Candidate> {
     void saveLogin(String email, String password) throws WrongExecutedQueryException, AlreadyExistException, DaoException;
     void blockCandidate(int id) throws WrongExecutedQueryException;
     int getCandidateListSize(int facultyId);
     List<Candidate> getCandidatesForFaculty(int facultyId, int limit, int offset);

     Candidate getByLogin(String login) throws DaoException;

    void saveCertificate(int id, String certificateName) throws WrongExecutedQueryException;
}
