package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import managers.MessageManager;
import org.apache.log4j.Logger;
import services.ExamService;
import services.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class AddExamCommand implements ActionCommand {
    private static final String PARAM_NAME_NEW_EXAM = "newExam";
    private static final Logger LOG = Logger.getLogger(AddExamCommand.class);
    private ExamService examService;
    public AddExamCommand(ExamService examService) {
        this.examService = examService;
    }
    @Override
    public String execute(HttpServletRequest req) {
        String page = null;
        String examName = null;
        LOG.debug("Start addExamCommand");
        examName = req.getParameter(PARAM_NAME_NEW_EXAM);

        try {
            examService.addExam(examName);
            //TODO: change catch e
        } catch (ServiceException e) {
            req.setAttribute("alreadyExist",
                    MessageManager.getProperty("message.alreadyExist"));
            throw new RuntimeException(e);
        }
        page = ConfigurationManager.getProperty("path.command.exams");
        LOG.debug("Go to ExamsCommand");
        return page;
    }
}
