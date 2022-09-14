package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Candidate;
import models.Faculty;
import org.apache.log4j.Logger;
import services.interfaces.CandidateService;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Retrieve candidate list from db according to provided parameters
 * (could be received for certain faculty and using pagination)
 */
public class CandidatesCommand implements ActionCommand {
    private final CandidateService candidateService;
private final FacultyService facultyService;
    public CandidatesCommand(CandidateService candidateService, FacultyService facultyService) {
        this.candidateService = candidateService;
        this.facultyService = facultyService;
    }

    private static final Logger LOG = Logger.getLogger(CandidatesCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("CandidatesCommand starts");
        String page = ConfigurationManager.getProperty("admin.candidates");
        String language = request.getLocale().getLanguage();
        LOG.trace("Language from Locale ==> " + language);

        String selectedFacultyId = request.getParameter("selectedFacultyId");
        String limitItems = request.getParameter("limitItems");
        String offset = request.getParameter("offset");

        LOG.debug("selectedFacultyId ==> " + selectedFacultyId);
        LOG.debug("limitItems ==> " + limitItems);
        LOG.debug("offset ==> " + offset);

        List<Candidate> candidates = candidateService.getAll(selectedFacultyId, limitItems, offset);
        request.setAttribute("candidates", candidates);
        LOG.trace("Set the request attribute: candidates ==> " + candidates);

        List<Faculty> faculties = facultyService.getSortedList(language, null, null);
        request.setAttribute("faculties", faculties);
        LOG.trace("Set the request attribute: faculties ==> " + faculties);

        int totalCandidates = candidateService.getCandidatesListSize(selectedFacultyId);
        request.setAttribute("totalCandidates", totalCandidates);
        LOG.trace("Set the request attribute: totalCandidates ==> " + totalCandidates);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
