package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Application;
import models.Candidate;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CandidateDetailsCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(CandidateDetailsCommand.class);

    private final CandidateService candidateService;
    private final ApplicationService applicationService;

    public CandidateDetailsCommand(CandidateService candidateService, ApplicationService applicationService) {
        this.candidateService = candidateService;
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse resp) {
        LOG.debug("CandidateDetailsCommand starts");
        String page = ConfigurationManager.getProperty("admin.candidateDetails");
        String id = request.getParameter("selectedCandidateId");
        LOG.debug("Candidate Id value ==> " + id);
        Candidate candidate = candidateService.getById(id);
        List<Application> applications = applicationService.getCandidatesAppls(id);

        request.setAttribute("candidate", candidate);
        request.setAttribute("applications", applications);

        LOG.debug("Go to ==> " + page);
        return page;

    }
}
