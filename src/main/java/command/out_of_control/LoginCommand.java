package command.out_of_control;

import command.ActionCommand;
import managers.ConfigurationManager;
import managers.MessageManager;
import models.Candidate;
import models.Role;
import org.apache.log4j.Logger;
import services.EmptyFieldsException;
import services.ServiceException;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    private static final Logger logger = Logger.getLogger(ActionCommand.class);

    private CandidateService candidateService;

    public LoginCommand(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        logger.debug("LoginCommand starts");
        HttpSession session = req.getSession();
        String page = null;
        Candidate candidate = null;
        String login = req.getParameter(PARAM_NAME_LOGIN);
        String password = req.getParameter(PARAM_NAME_PASSWORD);
        try {
            candidate = candidateService.authenticate(login, password);
        } catch (EmptyFieldsException |
                 ServiceException e) {
            req.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginError"));
            page = ConfigurationManager.getProperty("path.common.login");
            return page;
        }

        req.getSession().setAttribute("user", candidate);
        logger.trace("Set the session attribute: user --> " + candidate);

        Role role = candidate.getRole();
        session.setAttribute("role", role);
        logger.trace("Set the session attribute: role --> " + role);

        if (role == Role.CANDIDATE) {
            page = ConfigurationManager.getProperty("path.candidate.candidateProfile");
        } else if (role == Role.ADMIN) {
            page = ConfigurationManager.getProperty("path.admin.adminProfile");
        }
        logger.debug("Go to ==> " + page);
        return page;
    }
}
