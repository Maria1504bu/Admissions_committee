package services.implementation;


import dao.DaoException;
import dao.interfaces.FacultyDao;
import models.Faculty;
import org.apache.log4j.Logger;
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
    public void save(Faculty faculty) {

    }

    @Override
    public void update(Faculty faculty) {

    }

    @Override
    public void delete(int id) {

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
