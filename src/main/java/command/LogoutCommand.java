package command;

import javax.servlet.http.HttpServletRequest;
import managers.ConfigurationManager;

public class LogoutCommand implements ActionCommand{
    @Override
    public String execute(HttpServletRequest req) {
        String page = ConfigurationManager.getProperty("path.page.index");
        req.getSession().invalidate();
        return page;
    }
}
