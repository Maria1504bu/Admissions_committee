package command.admin;

import command.ActionCommand;
import dao.DaoException;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.interfaces.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class SubjectCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(SubjectCommand.class);
    private SubjectService examService;
    public SubjectCommand(SubjectService examService) {
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
