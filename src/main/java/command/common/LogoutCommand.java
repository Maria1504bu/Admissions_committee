package command.common;

import command.ActionCommand;
import managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("common.index");
        req.getSession().invalidate();
        return page;
    }
}
