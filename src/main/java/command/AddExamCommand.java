package command;

import dao.DaoException;
import dao.ExamDao;
import managers.ConfigurationManager;
import managers.MessageManager;
import models.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddExamCommand implements ActionCommand{
    private static final Logger logger = Logger.getLogger(AddExamCommand.class);
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
            added = new ExamDao().addCandidatesExam(candidatesId, examId, mark);
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
