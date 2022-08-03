package command.out_of_control;

import command.ActionCommand;
import dao.DaoException;
import dao.UserDao;
import dao.UserDaoImpl;
import managers.ConfigurationManager;
import models.Role;
import models.User;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignupCommand implements ActionCommand {
    private final static String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_PASS = "password";

    private UserService userService;
    public SignupCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String login = req.getParameter(PARAM_NAME_LOGIN);
        String password = req.getParameter(PARAM_NAME_PASS);
        UserDao userDao = new UserDaoImpl();
        // TODO: change id and role, use service
        User user = new User(0, login, password, Role.CANDIDATE);
        try {
            userDao.save(user);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        session.setAttribute("userRole", Role.CANDIDATE);
        String page = ConfigurationManager.getProperty("path.candidate.candidateProfile");
        req.getSession().setAttribute("user", user);
        return page;
    }
}
