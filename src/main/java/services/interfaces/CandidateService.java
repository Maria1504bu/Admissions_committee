package services.interfaces;

import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import models.Candidate;
import models.City;
import services.EmptyFieldsException;

import java.util.List;

public interface CandidateService {
    Candidate authenticate(String email, String password) throws EmptyFieldsException;
    Candidate signInit(String email, String password) throws EmptyFieldsException;
    Candidate signFinal(Candidate candidate, String firstName, String fatherName, String secondName, City city, String schoolName) throws EmptyFieldsException;

    Candidate saveCertificate(Candidate candidate, String certificateName);

    void blockCandidate(int candidateId);

    int getCandidatesListSize(String facultyId);

    List<Candidate> getAll(String selectedFaculty, String limitItemsQty, String offSetValueCandidates);
    Candidate getById(int id);
    void update(Candidate candidate) throws AlreadyExistException, WrongExecutedQueryException;
}
