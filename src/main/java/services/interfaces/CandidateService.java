package services.interfaces;

import models.Candidate;
import models.City;
import services.EmptyFieldsException;

public interface CandidateService {
    Candidate authenticate(String email, String password) throws EmptyFieldsException;
    Candidate signInit(String email, String password) throws EmptyFieldsException;
    Candidate signFinal(Candidate candidate, String firstName, String fatherName, String secondName, City city, String schoolName) throws EmptyFieldsException;

    Candidate saveCertificate(Candidate candidate, String certificateName);
}
