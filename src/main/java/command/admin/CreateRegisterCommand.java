package command.admin;


import command.ActionCommand;
import managers.ConfigurationManager;
import models.Faculty;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateRegisterCommand implements ActionCommand {
    private final ApplicationService applicationService;
    private final FacultyService facultyService;

    public CreateRegisterCommand(ApplicationService applicationService, FacultyService facultyService){
        this.applicationService = applicationService;
        this.facultyService = facultyService;
    }
    private static final Logger LOG = Logger.getLogger(CreateRegisterCommand.class);
    /**
     * Can`t set 'phase' attribute explicitly at js function because of peculiarity their performance
     * @param req
     * @param resp
     * @return
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("CreateRegisterCommand starts");
        String page = ConfigurationManager.getProperty("path.command.faculties");
        String phase = req.getParameter("phase");
        LOG.trace("Parameter phase from request ==> " + phase);
        req.getServletContext().setAttribute("phase", phase);
        LOG.trace("Set application attribute phase ==> " + phase);
        if(phase.equals("completedRegister")){
            LOG.debug("Create registers");
            List<Faculty> faculties = facultyService.getSortedList("UK", "name", "ASC");
            for(Faculty faculty : faculties){
                applicationService.createRegister(faculty);
                LOG.debug("Register for faculty ==> " + faculty + " created");
            }
        }

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
