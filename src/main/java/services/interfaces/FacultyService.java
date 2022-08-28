package services.interfaces;

import com.google.protobuf.ServiceException;
import models.Faculty;
import services.EmptyFieldsException;

import java.util.List;

public interface FacultyService {
    Faculty getById (int id) throws ServiceException;
    void update (Faculty faculty);
    void delete (int id);
    List<Faculty> getSortedList(String lang, String orderBy, String order);

    void save(String englishName, String ukrainianName, String budgetQty, String totalQty, String[] subjectsIds) throws EmptyFieldsException;
}
