package services.interfaces;

import models.Subject;

import java.util.List;

public interface SubjectService {
    Subject getById(int id);
    void save(Subject subject);
    void update(Subject subject);
    void delete(int id);
    List findAll();

    List<Subject> findAllByFaculty(int facultyId);
}
