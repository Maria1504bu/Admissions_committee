package services.interfaces;

import models.Candidate;
import models.City;
import services.EmptyFieldsException;

public interface CandidateService {
    public Candidate authenticate(String email, String password) throws EmptyFieldsException;
    public Candidate signInit(String email, String password) throws EmptyFieldsException;
    public Candidate signFinal(Candidate candidate, String firstName, String fatherName, String secondName, City city, String schoolName) throws EmptyFieldsException;

}
