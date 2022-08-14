package services.interfaces;

import java.util.List;

public interface SubjectService {
    void addSubject(String examName);

    List findAll();
}
