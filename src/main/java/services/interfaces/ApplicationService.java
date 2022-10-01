package services.interfaces;

import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import models.Application;
import models.Faculty;

import java.util.List;

public interface ApplicationService {
    void saveWithGrades(Application application) throws AlreadyExistException, WrongExecutedQueryException;

    public List<Application> getCandidatesAppls(String candidateId);

    List<Application> getFacultyAppls(String facultyId);

    void provideDocuments(Application application) throws AlreadyExistException, WrongExecutedQueryException;
    void createRegister(Faculty faculty);
}
