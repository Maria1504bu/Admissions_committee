package command;

import dao.DaoException;
import dao.ExamDao;
import managers.ConfigurationManager;
import models.Exam;
import models.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class CandidateProfileCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        int candidatesId = user.getId();

        Map<String, Integer> candidatesExams = null;
        try {
            candidatesExams = new ExamDao().findAllByCandidatesId(candidatesId);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        req.getSession().setAttribute("candidatesExams", candidatesExams);

        List<Exam> notPassedExams = null;
        try {
            notPassedExams = new ExamDao().findAll();
            for(String examName : candidatesExams.keySet()) {
                notPassedExams.removeIf(exam -> exam.getName().equals(examName));
            }} catch (DaoException e) {
            throw new RuntimeException(e);
        }
        req.getSession().setAttribute("notPassedExams", notPassedExams);
        String page = ConfigurationManager.getProperty("path.page.candidateProfile");
        return page;
    }
}
