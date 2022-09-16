package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFacultyCommand implements ActionCommand {

    private final FacultyService facultyService;
    public DeleteFacultyCommand(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    private static final Logger LOG = Logger.getLogger(DeleteFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("DeleteFacultyCommand starts");
        String page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("path.command.faculties");

        // obtain id to delete from the request
        String facultyId = request.getParameter("deleteFacultyId");
        LOG.debug("Request parameter: deleteFacultyId ==> " + facultyId);

        facultyService.delete(facultyId);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
