package command.admin;

import command.ActionCommand;
import dao.DaoException;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.ExamService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ExamsCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(ExamsCommand.class);
    private ExamService examService;
    public ExamsCommand(ExamService examService) {
        this.examService = examService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        LOG.debug("Start examsCommand");
        String page = null;
        List exams = null;
        //TODO:
        try {
            exams = examService.findAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("exams", exams);
        LOG.debug("Set attribute exams with all created exams => " + exams);

        page = ConfigurationManager.getProperty("path.admin.exams");
        LOG.debug("Go to exams.jsp page");
        return page;
    }
}
