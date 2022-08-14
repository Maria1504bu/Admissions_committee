package services.implementation;

import dao.interfaces.GradeDao;
import services.interfaces.GradeService;

public class GradeServiceImpl implements GradeService {
    private GradeDao gradeDao;
    public GradeServiceImpl(GradeDao gradeDao){
        this.gradeDao = gradeDao;
    }

}
