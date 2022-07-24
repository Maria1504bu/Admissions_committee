package command;

import jakarta.servlet.http.HttpServletRequest;
import logic.LoginLogic;
import managers.ConfigurationManager;
import managers.MessageManager;

import javax.security.auth.login.Configuration;

public class LoginCommand implements ActionCommand{
   private static final String PARAM_NAME_LOGIN = "login";
   private  static final String PARAM_NAME_PASSWORD = "password";
    @Override
    public String execute(HttpServletRequest req) {
        String page = null;
        String login = req.getParameter(PARAM_NAME_LOGIN);
        String password = req.getParameter(PARAM_NAME_PASSWORD);
        if(LoginLogic.checkLogin(login, password)){
            req.setAttribute("user", login);
            page = ConfigurationManager.getProperty("path.page.main");
        } else {
            req.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginError"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
