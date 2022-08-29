package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.interfaces.SubjectService;

import javax.servlet.http.HttpServletRequest;

public class DeleteSubjectCommand implements ActionCommand {
    private final SubjectService subjectService;

    public DeleteSubjectCommand(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    private static final Logger LOG = Logger.getLogger(DeleteSubjectCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOG.debug("DeleteSubjectCommand starts");
        String page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("path.command.subjects");

        // obtain id to delete from the request
        String subjectId = request.getParameter("deleteSubjectId");
        LOG.debug("Request parameter: deleteSubjectId ==> " + subjectId);

        int idToDelete = 0;
        try {
            idToDelete = Integer.parseInt(subjectId);
        } catch (NumberFormatException ex) {
            LOG.debug("Could not get id to delete from url " + ex);
            return page;
        }

        subjectService.delete(idToDelete);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
