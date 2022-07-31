package command;

import dao.DaoException;
import dao.UserDaoImpl;
import logic.LoginLogic;
import managers.ConfigurationManager;
import managers.MessageManager;
import models.Role;
import models.User;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand{
   private static final String PARAM_NAME_LOGIN = "login";
   private  static final String PARAM_NAME_PASSWORD = "password";
    @Override
    public String execute(HttpServletRequest req) {
        String page = null;
        User user = null;
        String login = req.getParameter(PARAM_NAME_LOGIN);
        String password = req.getParameter(PARAM_NAME_PASSWORD);
        if(LoginLogic.checkLogin(login, password)){
            try {
                user = new UserDaoImpl().getByLogin(login);
                req.getSession().setAttribute("user", user);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            Role role = user.getRole();
            if (role == Role.CANDIDATE) {
                page = ConfigurationManager.getProperty("path.command.candidateProfile");
            } else if (role == Role.ADMIN) {
                page = ConfigurationManager.getProperty("path.page.adminProfile");
            }
        } else {
            req.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginError"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
