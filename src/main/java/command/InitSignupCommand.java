package command;

import jakarta.servlet.http.HttpServletRequest;
import managers.ConfigurationManager;

public class InitSignupCommand implements ActionCommand{
    @Override
    public String execute(HttpServletRequest req) {
        return ConfigurationManager.getProperty("path.page.signup");
    }
}
