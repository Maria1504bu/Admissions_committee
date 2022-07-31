package command;

import dao.DaoException;
import dao.ExamDao;
import dao.UserDaoImpl;
import managers.ConfigurationManager;
import models.Exam;
import models.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        List<String> allExamsNames = null;
        try {
            allExamsNames = new ExamDao().findAll().stream()
                    .map(exam -> exam.getName()).sorted().collect(Collectors.toList());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        req.getSession().setAttribute("allExamsNames", allExamsNames);
        String page = ConfigurationManager.getProperty("path.page.candidateProfile");
        return page;
    }
}
