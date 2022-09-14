package command.out_of_control;

import command.ActionCommand;
import managers.ConfigurationManager;
import managers.MessageManager;
import models.Candidate;
import models.City;
import models.Role;
import org.apache.log4j.Logger;
import services.EmptyFieldsException;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

public class SignupStartCommand implements ActionCommand {
    private final static String PARAM_NAME_EMAIL = "email";
    private final static String PARAM_NAME_PASS = "password";

    private static final Logger LOG = Logger.getLogger(SignupStartCommand.class);

    private CandidateService candidateService;
    public SignupStartCommand(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) {
        LOG.debug("SignupStartCommand starts");
        HttpSession session = req.getSession();
        String email = req.getParameter(PARAM_NAME_EMAIL);
        String password = req.getParameter(PARAM_NAME_PASS);
        LOG.trace("Get parameters: email == " + email + " and password");
        Candidate candidate = null;
        String page = null;
        try {
            candidate = candidateService.signInit(email, password);
        } catch (EmptyFieldsException e) {
            req.setAttribute("errorEmailAlreadyExist",
                    MessageManager.getProperty("message.alreadyExist"));
            page = ConfigurationManager.getProperty("common.signupStart");
            return page;
        }
        List cities = Arrays.asList(City.values());
        req.getServletContext().setAttribute("cities", cities);
        LOG.trace("Set attribute cities for select tag and other tables on application scope");
        req.getSession().setAttribute("user", candidate);
        LOG.trace("Set the session attribute: user --> " + candidate);
        session.setAttribute("role", Role.CANDIDATE);
        LOG.trace("Set the session attribute: role --> " + Role.CANDIDATE);

        page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("candidate.signupFinal");
        LOG.debug("Go to ==> " + page);
        return page;
    }
}
