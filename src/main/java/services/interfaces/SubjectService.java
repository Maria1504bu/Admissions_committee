package services.interfaces;

import models.Subject;

import java.util.List;

public interface SubjectService {
    Subject getById(String id);
    void save(Subject subject);
    void update(Subject subject);
    void delete(String id);
    List findAll();

    List<Subject> findAllByFaculty(String facultyId);
}
