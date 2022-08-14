package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import managers.MessageManager;
import org.apache.log4j.Logger;
import services.interfaces.SubjectService;
import services.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class AddSubjectCommand implements ActionCommand {
    private static final String PARAM_NAME_NEW_EXAM = "newExam";
    private static final Logger LOG = Logger.getLogger(AddSubjectCommand.class);
    private SubjectService subjectService;
    public AddSubjectCommand(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @Override
    public String execute(HttpServletRequest req) {
        String page = null;
        String subjectName = null;
        LOG.debug("Start addExamCommand");
        subjectName = req.getParameter(PARAM_NAME_NEW_EXAM);

        try {
            subjectService.addSubject(subjectName);
            //TODO: change catch e
        } catch (ServiceException e) {
            req.setAttribute("alreadyExist",
                    MessageManager.getProperty("message.alreadyExist"));
            throw new RuntimeException(e);
        }
        page = ConfigurationManager.getProperty("path.command.subjects");
        LOG.debug("Go to ExamsCommand");
        return page;
    }
}
