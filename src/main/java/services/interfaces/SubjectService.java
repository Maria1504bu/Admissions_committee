package services.interfaces;

import models.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    Subject getById(String id);
    void save(Subject subject);
    void update(Subject subject);
    void delete(String id);
    List findAll();

    Map<Subject, Integer> findAllByFaculty(String facultyId);
}
