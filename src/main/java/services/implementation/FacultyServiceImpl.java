package services.implementation;


import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.FacultyDao;
import models.Faculty;
import models.Subject;
import org.apache.log4j.Logger;
import services.EmptyFieldsException;
import services.ServiceException;
import services.interfaces.FacultyService;
import util.NotValidException;
import util.Validator;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {

    private static final Logger LOG = Logger.getLogger(FacultyServiceImpl.class);
    private FacultyDao facultyDao;

    public FacultyServiceImpl(FacultyDao facultyDao){
        this.facultyDao = facultyDao;
    }

    @Override
    public Faculty getById(int id) throws ServiceException {
        Faculty faculty;
        try {
            Validator.validateId(id);
            faculty = facultyDao.getById(id);
        } catch (NotValidException | DaoException e) {
            throw new ServiceException("Can`t get faculty by id", e);
        }
        return faculty;
    }

    @Override
    public void save(String englishName, String ukrainianName, String budgetQty, String totalQty, String[] subjectsIds) throws EmptyFieldsException {
        Faculty faculty = new Faculty();
        if (englishName == null || ukrainianName == null || budgetQty == null || totalQty == null ||
                subjectsIds.length == 0 || englishName.isEmpty() || ukrainianName.isEmpty() || budgetQty.isEmpty() || totalQty.isEmpty()) {
            throw new EmptyFieldsException();
        }

        faculty.getNamesList().add(englishName);
        faculty.getNamesList().add(ukrainianName);
        faculty.setBudgetPlaces(Integer.parseInt(budgetQty));
        faculty.setTotalPlaces(Integer.parseInt(totalQty));
        for (String id : subjectsIds) {
            Subject s = new Subject();
            s.setId(Integer.parseInt(id));
            faculty.getSubjectList().add(s);
        }

        try {
            facultyDao.save(faculty);
        } catch (AlreadyExistException | WrongExecutedQueryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        LOG.debug("FacultyService finish saving faculty with englishName " + englishName);
    }

    @Override
    public void update(Faculty faculty) {
        try {
            facultyDao.update(faculty);
        } catch (AlreadyExistException | WrongExecutedQueryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        LOG.debug("FacultyService finish update faculty ==> " + faculty);
    }

    @Override
    public void delete(int id) {
        try {
            Validator.validateId(id);
            facultyDao.delete(id);
        } catch (NotValidException| WrongExecutedQueryException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }


    @Override
    public List<Faculty> getSortedList(String lang, String orderBy, String order) {
        if(orderBy == null || order.isEmpty()) orderBy = "name";
        if(order == null || order.isEmpty()) order = "ASC";
        orderBy += " " + order;
        List<Faculty> sortedList = facultyDao.getAllOrderBy(lang, orderBy);
        return sortedList;
    }

}
