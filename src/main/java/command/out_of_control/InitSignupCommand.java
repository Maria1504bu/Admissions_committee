package command.out_of_control;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class InitSignupCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(InitSignupCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        LOG.debug("InitSignupCommand redirect user to signupStart.jsp");
        return ConfigurationManager.getProperty("path.common.signupStart");
    }
}
