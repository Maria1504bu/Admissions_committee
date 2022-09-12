package services.implementation;

import dao.interfaces.*;
import models.Application;
import models.Candidate;
import models.Faculty;
import models.Grade;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;

import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationDao applicationDao;
    private final CandidateDao candidateDao;
    private final FacultyDao facultyDao;
    private final GradeDao gradeDao;
    private final SubjectDao subjectDao;
    private static final Logger LOG = Logger.getLogger(ApplicationServiceImpl.class);
    public ApplicationServiceImpl(ApplicationDao applicationDao, CandidateDao candidateDao, FacultyDao facultyDao, GradeDao gradeDao, SubjectDao subjectDao){
        this.applicationDao = applicationDao;
        this.candidateDao = candidateDao;
        this.facultyDao = facultyDao;
        this.gradeDao = gradeDao;
        this.subjectDao = subjectDao;
    }

    @Override
    public List<Application> getCandidatesAppls(int candidateId, String language) {
        LOG.trace("Start get candidates applications by candidateId ==> " + candidateId);
        List<Application> applications = applicationDao.getCandidateAppls(candidateId);
        LOG.trace("Applications from db ==> " + applications);
        // from applicationDao we get only id faculty
        for(Application application : applications){
            Faculty faculty = facultyDao.getById(application.getFaculty().getId());
            application.setFaculty(faculty);
            LOG.trace("Set faculty "+ faculty + " for application" + application);

            List<Grade> grades = gradeDao.getApplGrades(application.getId());
            for(Grade grade : grades){
                grade.setSubject(subjectDao.getById(grade.getSubject().getId()));
            }
            application.setGradesList(grades);
            LOG.trace("Set grades "+ grades + " for application" + application);
        }
        return applications;
    }

    @Override
    public List<Application> getFacultyAppls(int facultyId) {
        LOG.trace("Start get faculty`s applications by candidateId ==> " + facultyId);
        List<Application> applications = applicationDao.getFacultyAppls(facultyId);
        LOG.trace("Applications from db ==> " + applications);
        // from applicationDao we get only id faculty
        for(Application application : applications){
            Candidate candidate = candidateDao.getById(application.getCandidate().getId());
            application.setCandidate(candidate);
            LOG.trace("Set candidate "+ candidate + " to application" + application);

            Faculty faculty = facultyDao.getById(application.getFaculty().getId());
            application.setFaculty(faculty);
            LOG.trace("Set faculty "+ faculty + " for application" + application);

            List<Grade> grades = gradeDao.getApplGrades(application.getId());
            for(Grade grade : grades){
                grade.setSubject(subjectDao.getById(grade.getSubject().getId()));
            }
            application.setGradesList(grades);
            LOG.trace("Set grades "+ grades + " for application" + application);
        }
        return applications;
    }
}
