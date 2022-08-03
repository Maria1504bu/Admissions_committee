package command.candidate;

import command.ActionCommand;
import dao.DaoException;
import dao.ExamDaoImpl;
import managers.ConfigurationManager;
import models.Exam;
import models.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class CandidateProfileCommand implements ActionCommand {
    private static final Logger logger = Logger.getLogger(CandidateProfileCommand.class);
    @Override
    public String execute(HttpServletRequest req) {
        logger.debug("Start candidateProfileCommand");
        User user = (User) req.getSession().getAttribute("user");
        int candidatesId = user.getId();

        Map<String, Integer> candidatesExams = null;
        try {
            candidatesExams = new ExamDaoImpl().findAllByCandidatesId(candidatesId);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        req.getSession().setAttribute("candidatesExams", candidatesExams);
        logger.debug("set exams passed by candidate to session attribute candidatesExams => " + candidatesExams);

        List<Exam> notPassedExams = null;
        try {
            notPassedExams = new ExamDaoImpl().findAll();
            for(String examName : candidatesExams.keySet()) {
                notPassedExams.removeIf(exam -> exam.getName().equals(examName));
            }} catch (DaoException e) {
            throw new RuntimeException(e);
        }
        req.getSession().setAttribute("notPassedExams", notPassedExams);
        logger.debug("set not passed exams by this candidate for select it to add to session attribute notPassedExams => " + notPassedExams);

        String page = ConfigurationManager.getProperty("path.candidate.candidateProfile");
        logger.debug("Go to candidateProfile.jsp");
        return page;
    }
}
