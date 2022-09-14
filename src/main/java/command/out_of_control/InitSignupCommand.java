package command.out_of_control;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitSignupCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(InitSignupCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) {
        LOG.debug("InitSignupCommand redirect user to signupStart.jsp");
        return ConfigurationManager.getProperty("common.signupStart");
    }
}
