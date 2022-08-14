package services.interfaces;

import models.Candidate;
import services.EmptyFieldsException;

public interface CandidateService {
    public Candidate authenticate(String email, String password) throws EmptyFieldsException;
    public Candidate signInit(String email, String password) throws EmptyFieldsException;

}
