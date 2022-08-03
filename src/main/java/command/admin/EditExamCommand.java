package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.ExamService;

import javax.servlet.http.HttpServletRequest;

public class EditExamCommand implements ActionCommand {
    private static final Logger logger = Logger.getLogger(EditExamCommand.class);

    private ExamService examService;
    public EditExamCommand(ExamService examService) {
        this.examService = examService;
    }
    @Override
    public String execute(HttpServletRequest req) {
        logger.debug("Start EditExamCommand");
        String page = null;

//TODO: khg

        page = ConfigurationManager.getProperty("path.admin.exams");
        logger.debug("Go to exams.jsp");
        return page;
    }
}
