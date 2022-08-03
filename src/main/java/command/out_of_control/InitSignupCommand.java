package command.out_of_control;

import command.ActionCommand;
import managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class InitSignupCommand implements ActionCommand {


    @Override
    public String execute(HttpServletRequest req) {

        return ConfigurationManager.getProperty("path.common.signup");
    }
}
