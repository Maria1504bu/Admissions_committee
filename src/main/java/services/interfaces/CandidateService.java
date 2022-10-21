package services.interfaces;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import models.Candidate;
import models.City;
import services.EmptyFieldsException;
import services.ServiceException;

import java.util.List;

public interface CandidateService {
    Candidate authenticate(String email, String password) throws EmptyFieldsException, ServiceException, DaoException;
    Candidate signInit(String email, String password) throws EmptyFieldsException, ServiceException, DaoException, AlreadyExistException, WrongExecutedQueryException;
    Candidate signFinal(Candidate candidate, String firstName, String fatherName, String secondName, City city, String schoolName) throws EmptyFieldsException, ServiceException, DaoException;

    Candidate saveCertificate(Candidate candidate, String certificateName) throws ServiceException;

    void blockCandidate(String candidateId) throws ServiceException;

    int getCandidatesListSize(String facultyId);

    List<Candidate> getAll() throws DaoException;
    List<Candidate> getByFaculty(String selectedFaculty, String limitItemsQty, String offSetValueCandidates);
    Candidate getById(String id) throws DaoException;

    void update(Candidate candidate) throws AlreadyExistException, WrongExecutedQueryException, DaoException;
}
