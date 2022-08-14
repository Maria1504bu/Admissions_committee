package command.out_of_control;

import command.ActionCommand;
import managers.ConfigurationManager;
import managers.MessageManager;
import models.Candidate;
import models.Role;
import org.apache.log4j.Logger;
import services.EmptyFieldsException;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignupCommand implements ActionCommand {
    private final static String PARAM_NAME_LOGIN = "login";
    private final static String PARAM_NAME_PASS = "password";

    private static final Logger LOG = Logger.getLogger(SignupCommand.class);

    private CandidateService candidateService;
    public SignupCommand(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        LOG.debug("SignupCommand starts");
        HttpSession session = req.getSession();
        String login = req.getParameter(PARAM_NAME_LOGIN);
        String password = req.getParameter(PARAM_NAME_PASS);
        Candidate candidate = null;
        String page = null;
        try {
            candidate = candidateService.signInit(login, password);
        } catch (EmptyFieldsException e) {
            req.setAttribute("errorEmailAlreadyExist",
                    MessageManager.getProperty("message.alreadyExist"));
            page = ConfigurationManager.getProperty("path.common.signup");
            return page;
        }
        req.getSession().setAttribute("user", candidate);
        LOG.trace("Set the session attribute: user --> " + candidate);
        session.setAttribute("role", Role.CANDIDATE);
        LOG.trace("Set the session attribute: role --> " + Role.CANDIDATE);

        page = ConfigurationManager.getProperty("path.candidate.candidateProfile");
        LOG.debug("Go to ==> " + page);
        return page;
    }
}
