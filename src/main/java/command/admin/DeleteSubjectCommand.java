package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.interfaces.SubjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteSubjectCommand implements ActionCommand {
    private final SubjectService subjectService;

    public DeleteSubjectCommand(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    private static final Logger LOG = Logger.getLogger(DeleteSubjectCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("DeleteSubjectCommand starts");
        String page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("path.command.subjects");

        // obtain id to delete from the request
        String subjectId = request.getParameter("deleteSubjectId");
        LOG.debug("Request parameter: deleteSubjectId ==> " + subjectId);

        subjectService.delete(subjectId);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
