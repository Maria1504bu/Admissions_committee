package services.interfaces;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import models.Application;
import models.Faculty;
import services.ServiceException;

import java.util.List;

public interface ApplicationService {
    void saveWithGrades(Application application) throws AlreadyExistException, WrongExecutedQueryException, DaoException;

    public List<Application> getCandidatesAppls(String candidateId) throws DaoException;

    List<Application> getFacultyAppls(String facultyId) throws DaoException;

    void provideDocuments(Application application) throws AlreadyExistException, WrongExecutedQueryException;
    void createRegister(Faculty faculty) throws ServiceException, DaoException;
}
