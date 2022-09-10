package services.implementation;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.SubjectDao;
import models.Subject;
import org.apache.log4j.Logger;
import services.ServiceException;
import services.interfaces.SubjectService;
import util.NotValidException;
import util.Validator;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {

    private SubjectDao subjectDao;

    public SubjectServiceImpl(SubjectDao subjectDao){
        this.subjectDao = subjectDao;
    }
    private static final Logger LOG = Logger.getLogger(SubjectServiceImpl.class);
    @Override
    public Subject getById(int id) {
        Subject subject;
        try {
            Validator.validateId(id);
            subject = subjectDao.getById(id);
        } catch (NotValidException | DaoException e) {
            throw new ServiceException("Can`t get subject by id", e);
        }
        return subject;
    }
    @Override
    public void save (Subject subject) {
        try {
            subjectDao.save(subject);
        } catch (AlreadyExistException | WrongExecutedQueryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Subject subject) {
        try {
            subjectDao.update(subject);
        } catch (AlreadyExistException | WrongExecutedQueryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        LOG.debug("SubjectService finish update subject ==> " + subject);
    }

    @Override
    public void delete(int id) {
        try {
            Validator.validateId(id);
            subjectDao.delete(id);
        } catch (WrongExecutedQueryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List findAll() {
        return subjectDao.findAll();
    }

    @Override
    public List<Subject> findAllByFaculty (int facultyId){
        Validator.validateId(facultyId);
        return subjectDao.findAllByFacultyId(facultyId);
    }
}
