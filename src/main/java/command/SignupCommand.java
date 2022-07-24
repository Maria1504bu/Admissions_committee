package command;

import dao.UserDao;
import dao.UserDaoImpl;
import jakarta.servlet.http.HttpServletRequest;
import managers.ConfigurationManager;
import models.Role;
import models.User;

public class SignupCommand implements ActionCommand{
    private final static String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_PASS = "password";


    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter(PARAM_NAME_LOGIN);
        String password = req.getParameter(PARAM_NAME_PASS);
        UserDao userDao = new UserDaoImpl();
        // TODO: change id and role
        User user = new User(0, login, password, Role.CANDIDATE);
        userDao.save(user);

        String page = ConfigurationManager.getProperty("path.page.main");
        req.setAttribute("user", login);
        return page;
    }
}
