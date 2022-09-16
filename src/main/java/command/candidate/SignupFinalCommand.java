package command.candidate;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Candidate;
import models.City;
import models.Role;
import org.apache.log4j.Logger;
import services.EmptyFieldsException;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignupFinalCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(SignupFinalCommand.class);
    private final static String PARAM_NAME_FIRSTNAME = "firstName";
    private final static String PARAM_NAME_FATHER_NAME = "fatherName";
    private final static String PARAM_NAME_SECOND_NAME = "secondName";
    private final static String PARAM_NAME_CITY = "city";
    private final static String PARAM_NAME_SCHOOL_NAME = "schoolName";

    private CandidateService candidateService;
    public SignupFinalCommand(CandidateService candidateService){
        this.candidateService = candidateService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("SignUpFinalCommand starts -->" + request);
        HttpSession session = request.getSession();
        Candidate candidate = (Candidate) session.getAttribute("user");

        // obtain data from the request
        String firstName = request.getParameter(PARAM_NAME_FIRSTNAME);
        LOG.debug("Request parameter: firstName --> " + firstName);

        String fatherName = request.getParameter(PARAM_NAME_FATHER_NAME);
        LOG.debug("Request parameter: fatherName --> " + fatherName);

        String secondName = request.getParameter(PARAM_NAME_SECOND_NAME);
        LOG.debug("Request parameter: secondName --> " + secondName);

        String city = request.getParameter(PARAM_NAME_CITY);
        LOG.debug("Request parameter: city --> " + city);

        String schoolName = request.getParameter(PARAM_NAME_SCHOOL_NAME);
        LOG.debug("Request parameter: schoolName --> " + schoolName);

        try {
            candidate = candidateService.signFinal(candidate, firstName, fatherName, schoolName, Enum.valueOf(City.class, city), schoolName);
        } catch (EmptyFieldsException e) {
            throw new RuntimeException(e);
        }
        session.setAttribute("user", candidate);
        LOG.trace("Reset user attribute ==> " + candidate);
        session.setAttribute("role", Role.CANDIDATE);
        LOG.trace("Set the session attribute: role --> " + Role.CANDIDATE);
        String page = ConfigurationManager.getProperty("redirect") + ConfigurationManager.getProperty("path.command.candidateProfile");
        LOG.debug("Go to ==> " + page);
        return page;
    }
}
