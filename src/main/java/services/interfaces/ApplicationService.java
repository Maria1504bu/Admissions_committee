package services.interfaces;

import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import models.Application;

import java.util.List;

public interface ApplicationService {
    void saveWithGrades(Application application) throws AlreadyExistException, WrongExecutedQueryException;

    public List<Application> getCandidatesAppls(int candidateId, String language);

    List<Application> getFacultyAppls(int facultyId);
}
