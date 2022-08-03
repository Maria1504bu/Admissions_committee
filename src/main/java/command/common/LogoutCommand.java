package command.common;

import command.ActionCommand;
import managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        String page = ConfigurationManager.getProperty("path.common.index");
        req.getSession().invalidate();
        return page;
    }
}
