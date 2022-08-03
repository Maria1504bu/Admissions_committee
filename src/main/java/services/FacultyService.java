package services;

import com.google.protobuf.ServiceException;
import models.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty getById (int id) throws ServiceException;
    void save(Faculty faculty);
    void update (Faculty faculty);
    void delete (int id);
    List<Faculty> findAll();
}
