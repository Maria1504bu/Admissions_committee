package services;

import java.util.List;

public interface ExamService {
    void addExam(String examName);

    List findAll();
}
