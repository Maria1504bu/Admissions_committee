package command.out_of_control;

import com.google.protobuf.ServiceException;
import command.ActionCommand;
import managers.ConfigurationManager;
import models.Application;
import models.Faculty;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ApplicationsToFacCommand implements ActionCommand {
    private final FacultyService facultyService;
    private final ApplicationService applicationService;
    private static final Logger LOG = Logger.getLogger(ApplicationsToFacCommand.class);

    public ApplicationsToFacCommand(FacultyService facultyService, ApplicationService applicationService) {
        this.facultyService = facultyService;
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) {
        LOG.trace("ApplicationToFacultyCommand starts");
        String page = ConfigurationManager.getProperty("common.applications");
        String facultyId = req.getParameter("applsToFacId");
        LOG.debug("Faculty id to display applications ==> " + facultyId);
        Faculty faculty = null;
        List<Application> applications = null;
        if (facultyId != null) {
            int facId = Integer.parseInt(facultyId);
            try {
                faculty = facultyService.getById(facId);
                LOG.trace("Searched faculty ==> " + faculty);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
            applications = applicationService.getFacultyAppls(facId);
            LOG.trace("Applications to faculty ==> " + applications);
        }
        req.getSession().setAttribute("faculty", faculty);
        LOG.debug("Set session attribute faculty ==> " + faculty);
        req.setAttribute("appls", applications);
        LOG.debug("Set appls attribute ==> " + applications);
        LOG.debug("Go to ==> " + page);
        return page;
    }
}
