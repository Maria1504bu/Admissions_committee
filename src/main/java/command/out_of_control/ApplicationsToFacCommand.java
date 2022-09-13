package command.out_of_control;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Application;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ApplicationsToFacCommand implements ActionCommand {
    private final ApplicationService applicationService;
    private static final Logger LOG = Logger.getLogger(ApplicationsToFacCommand.class);
    public ApplicationsToFacCommand(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        LOG.trace("ApplicationToFacultyCommand starts");
        String page = ConfigurationManager.getProperty("admin.applications");
        String facultyId = req.getParameter("applsToFacId");
        List <Application> applications = null;
        if(facultyId != null) {
            applications = applicationService.getFacultyAppls(Integer.parseInt(facultyId));
        }
        req.setAttribute("appls", applications);
        LOG.trace("Set appls attribute ==> " + applications);
        return page;
    }
}
