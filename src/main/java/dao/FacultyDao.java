package dao;

import java.util.List;
import models.Subject;
import models.Faculty;

public interface FacultyDao extends CrudDao<Faculty> {
    List<Subject> findSubjects(int id);
}
