package logic;

import dao.UserDao;
import dao.UserDaoImpl;
import models.User;
import org.apache.log4j.Logger;


public class LoginLogic {
    private static final Logger logger = Logger.getLogger(LoginLogic.class);
    /**
     * Find user in db
     * @param login of user
     * @param password of user
     * @return boolean whether user exists
     */
    public static boolean checkLogin(String login, String password){
        UserDao userDao = new UserDaoImpl();
        User user = userDao.getByLogin(login);
        if(user == null || !user.getPassword().equals(password)){
            logger.error("Cannot find user with such login/password");
            return false;
        }
        return true;
    }
}
