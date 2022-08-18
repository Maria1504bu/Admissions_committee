package command.candidate;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Application;
import models.Candidate;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CandidateProfileCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(CandidateProfileCommand.class);
    private ApplicationService applicationService;

    public CandidateProfileCommand(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        LOG.debug("Start candidateProfileCommand");
        Candidate candidate = (Candidate) req.getSession().getAttribute("user");
        LOG.trace("User from session ==>" + candidate);
        String lang = req.getLocale().getLanguage();
        LOG.trace("Locale language ==> " + lang);

        String page = ConfigurationManager.getProperty("path.command.createApplication");

        List<Application> candidateAplls = applicationService.getCandidatesAppls(candidate.getId(), lang);
        if (candidateAplls != null && candidateAplls.size() > 0) {
            req.getSession().setAttribute("applicationsList", candidateAplls);
            //TODO: Get certificate
//            byte[] certificate = new CandidateDao().getCertificate(candidate.getId());
//            if (certificate.length > 0) {
//                String certImage = Base64.getEncoder().encodeToString(certificate);
//                session.setAttribute("certImage", certImage);
//            }
            page = ConfigurationManager.getProperty("path.candidate.candidateProfile");
        }
        LOG.debug("Go to ==> " + page);
        return page;
    }
}
