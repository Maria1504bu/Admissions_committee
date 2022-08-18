package services.implementation;

import dao.interfaces.ApplicationDao;
import dao.interfaces.FacultyDao;
import models.Application;
import models.Faculty;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;

import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {
    private ApplicationDao applicationDao;
    private FacultyDao facultyDao;
    private static final Logger LOG = Logger.getLogger(ApplicationServiceImpl.class);
    public ApplicationServiceImpl(ApplicationDao applicationDao, FacultyDao facultyDao){
        this.applicationDao = applicationDao;
        this.facultyDao = facultyDao;
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
        }
        return applications;
    }
}
