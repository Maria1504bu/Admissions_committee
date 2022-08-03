package command.out_of_control;

import command.ActionCommand;
import dao.DaoException;
import dao.UserDaoImpl;
import logic.LoginLogic;
import managers.ConfigurationManager;
import managers.MessageManager;
import models.Role;
import models.User;
import org.apache.log4j.Logger;
import services.ExamService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ActionCommand {
   private static final String PARAM_NAME_LOGIN = "login";
   private  static final String PARAM_NAME_PASSWORD = "password";

   private static final Logger logger = Logger.getLogger(ActionCommand.class);

    private UserService userService;
    public LoginCommand(UserService userService) {
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest req) {
        logger.debug("LoginCommand starts");
        HttpSession session = req.getSession();
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
            session.setAttribute("userRole", role);
            logger.trace("Set the session attribute: userRole --> " + role);
            if (role == Role.CANDIDATE) {
                page = ConfigurationManager.getProperty("path.candidate.candidateProfile");
            } else if (role == Role.ADMIN) {
                page = ConfigurationManager.getProperty("path.admin.adminProfile");
            }
        } else {
            req.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginError"));
            page = ConfigurationManager.getProperty("path.common.login");
        }
        logger.debug("LoginCommand starts");
        logger.debug("Go to ==> " + page);
        return page;
    }
}
