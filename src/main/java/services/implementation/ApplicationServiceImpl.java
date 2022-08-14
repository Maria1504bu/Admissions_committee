package services.implementation;

import dao.interfaces.ApplicationDao;
import services.interfaces.ApplicationService;

public class ApplicationServiceImpl implements ApplicationService {
    private ApplicationDao applicationDao;
    public ApplicationServiceImpl(ApplicationDao applicationDao){
        this.applicationDao = applicationDao;
    }

}
