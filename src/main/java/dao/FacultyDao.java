package dao;

import java.util.List;
import models.Exam;
import models.Faculty;

public interface FacultyDao extends CrudDao<Faculty> {
    List<Exam> findSubjects(int id);
}
