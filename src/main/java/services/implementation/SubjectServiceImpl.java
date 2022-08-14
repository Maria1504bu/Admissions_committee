package services.implementation;

import dao.interfaces.SubjectDao;
import services.interfaces.SubjectService;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {

    private SubjectDao subjectDao;

    public SubjectServiceImpl(SubjectDao subjectDao){
        this.subjectDao = subjectDao;
    }
    @Override
    public void addSubject(String examName) {

    }

    @Override
    public List findAll() {
        return null;
    }
}
