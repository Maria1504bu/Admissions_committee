package dao.interfaces;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import models.Candidate;

import java.util.List;

public interface CandidateDao extends CrudDao<Candidate> {
    public void saveLogin(String email, String password) throws WrongExecutedQueryException, AlreadyExistException, DaoException;
    public void blockCandidate(int id, boolean toBlock) throws WrongExecutedQueryException;
    public int getCandidateListSize(int facultyId);
    public List<Candidate> getCandidatesForFaculty(int facultyId, int limit, int offset);

    public Candidate getByLogin(String login) throws DaoException;
}
