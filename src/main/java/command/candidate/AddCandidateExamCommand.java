package command.candidate;

import command.ActionCommand;
import dao.DaoException;
import dao.ExamDaoImpl;
import managers.ConfigurationManager;
import managers.MessageManager;
import models.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddCandidateExamCommand implements ActionCommand {
    private static final Logger logger = Logger.getLogger(AddCandidateExamCommand.class);
    private  static final String PARAM_NAME_EXAM_ID = "examId";
    private  static final String PARAM_NAME_MARK = "mark";
    @Override
    public String execute(HttpServletRequest req) {
        logger.debug("AddExamCommand starts");
        User user = (User) req.getSession().getAttribute("user");
        int candidatesId = user.getId();
        int examId = Integer.parseInt(req.getParameter(PARAM_NAME_EXAM_ID));
        int mark = Integer.parseInt(req.getParameter(PARAM_NAME_MARK));
        boolean added = false;
        try {
            added = new ExamDaoImpl().addCandidatesExam(candidatesId, examId, mark);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        if (added){
            req.setAttribute("examIsAdded", MessageManager.getProperty("message.examIsAdded"));
        }
        String page = ConfigurationManager.getProperty("path.command.candidateProfile");
        return page;
    }
}
