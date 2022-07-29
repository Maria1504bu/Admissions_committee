package command;

import dao.DaoException;
import dao.ExamDao;
import dao.UserDaoImpl;
import jakarta.servlet.http.HttpServletRequest;
import logic.LoginLogic;
import managers.ConfigurationManager;
import managers.MessageManager;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;

public class LoginCommand implements ActionCommand{
   private static final String PARAM_NAME_LOGIN = "login";
   private  static final String PARAM_NAME_PASSWORD = "password";
    @Override
    public String execute(HttpServletRequest req) {
        String page = null;
        String login = req.getParameter(PARAM_NAME_LOGIN);
        String password = req.getParameter(PARAM_NAME_PASSWORD);
        if(LoginLogic.checkLogin(login, password)){
            req.getSession().setAttribute("user", login);

            // TODO: sout exams this user
            List exams = null;
            try {
                exams = new ExamDao().findAllByCandidatesId(1);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            req.getSession().setAttribute("exams", exams);
            page = ConfigurationManager.getProperty("path.page.candidateProfile");
        } else {
            req.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginError"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
