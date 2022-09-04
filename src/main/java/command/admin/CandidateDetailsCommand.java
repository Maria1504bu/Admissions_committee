package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Application;
import models.Candidate;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;
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
    public String execute(HttpServletRequest request) {
        LOG.debug("CandidateDetailsCommand starts");
        String page = ConfigurationManager.getProperty("admin.candidateDetails");
        String candidateId = request.getParameter("selectedCandidateId");
        LOG.debug("CandidateId value ==> " + candidateId);
        String language = request.getLocale().getLanguage();
        int id = Integer.parseInt(candidateId);
        Candidate candidate = candidateService.getById(id);
        List<Application> applications = applicationService.getCandidatesAppls(id, language);

        request.setAttribute("candidate", candidate);
        request.setAttribute("applications", applications);

        LOG.debug("Go to ==> " + page);
        return page;

    }
}
