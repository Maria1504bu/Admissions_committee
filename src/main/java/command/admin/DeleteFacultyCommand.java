package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;

public class DeleteFacultyCommand implements ActionCommand {

    private final FacultyService facultyService;
    public DeleteFacultyCommand(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    private static final Logger LOG = Logger.getLogger(DeleteFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOG.debug("DeleteFacultyCommand starts");
        String page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("path.command.faculties");

        // obtain id to delete from the request
        String facultyId = request.getParameter("deleteFacultyId");
        LOG.debug("Request parameter: deleteFacultyId ==> " + facultyId);

        int idToDelete = 0;
        try {
            idToDelete = Integer.parseInt(facultyId);
        } catch (NumberFormatException ex) {
            LOG.debug("Could not get id to delete from url " + ex);
            return page;
        }

        facultyService.delete(idToDelete);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
