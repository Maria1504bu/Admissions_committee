package command;

import dao.DaoException;
import dao.UserDao;
import dao.UserDaoImpl;
import javax.servlet.http.HttpServletRequest;
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
        try {
            userDao.save(user);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        String page = ConfigurationManager.getProperty("path.page.main");
        req.setAttribute("user", login);
        return page;
    }
}
