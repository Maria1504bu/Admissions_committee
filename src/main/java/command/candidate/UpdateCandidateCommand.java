package command.candidate;

import command.ActionCommand;
import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import managers.ConfigurationManager;
import models.Candidate;
import models.City;
import org.apache.log4j.Logger;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Get change parameters of candidate from request and update the candidate in the db
 */
public class UpdateCandidateCommand implements ActionCommand {
    private final CandidateService candidateService;

    public UpdateCandidateCommand(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    private static final Logger LOG = Logger.getLogger(UpdateCandidateCommand.class);

    private final static String PARAM_NAME_EMAIL = "email";
    private final static String PARAM_NAME_PASS = "password";
    private final static String PARAM_NAME_FIRSTNAME = "firstName";
    private final static String PARAM_NAME_FATHER_NAME = "fatherName";
    private final static String PARAM_NAME_SECOND_NAME = "secondName";
    private final static String PARAM_NAME_CITY = "city";
    private final static String PARAM_NAME_SCHOOL_NAME = "schoolName";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("UpdateCandidateCommand starts");
        String page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("candidate.candidateProfile");

        Candidate candidate = (Candidate) request.getSession().getAttribute("user");

        LOG.trace("Candidate, which need to update ==> " + candidate);

        String email = request.getParameter(PARAM_NAME_EMAIL);
        String password = request.getParameter(PARAM_NAME_PASS);
        LOG.trace("Get parameters: email == " + email + " and password");

        String fatherName = request.getParameter(PARAM_NAME_FATHER_NAME);
        LOG.debug("Request parameter: fatherName --> " + fatherName);

        String firstName = request.getParameter(PARAM_NAME_FIRSTNAME);
        LOG.debug("Request parameter: firstName --> " + firstName);

        String secondName = request.getParameter(PARAM_NAME_SECOND_NAME);
        LOG.debug("Request parameter: secondName --> " + secondName);

        String city = request.getParameter(PARAM_NAME_CITY);
        LOG.debug("Request parameter: city --> " + city);

        String schoolName = request.getParameter(PARAM_NAME_SCHOOL_NAME);
        LOG.debug("Request parameter: schoolName --> " + schoolName);

        Candidate candidateToUpdate = Candidate.builder().id(candidate.getId()).email(email).fatherName(fatherName).firstName(firstName)
                .secondName(secondName).city(Enum.valueOf(City.class, city)).schoolName(schoolName).build();

        LOG.debug("Candidate with new data, which need to update at db ==> " + candidateToUpdate);
        try {
            candidateService.update(candidateToUpdate);
        } catch (AlreadyExistException | WrongExecutedQueryException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
