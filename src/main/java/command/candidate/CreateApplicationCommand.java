package command.candidate;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Candidate;
import models.Faculty;
import models.Subject;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;
import services.interfaces.SubjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CreateApplicationCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(CreateApplicationCommand.class);
    private final FacultyService facultyService;
    private final SubjectService subjectService;
    public CreateApplicationCommand(FacultyService facultyService, SubjectService subjectService){
        this.facultyService = facultyService;
        this.subjectService = subjectService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        LOG.debug("CreateApplicationCommand starts");
        String page = ConfigurationManager.getProperty("candidate.createApplication");
        HttpSession session = req.getSession();
        Candidate candidate = (Candidate) session.getAttribute("user");
        LOG.trace("User from session ==> " + candidate);
        String language = req.getLocale().getLanguage();
        LOG.trace("Get language ==> " + language);
        String facultyId = req.getParameter("facultyId");
        LOG.trace("facultyId to application ==> " + facultyId);


        List<Faculty> faculties = facultyService.getSortedList(language, null, null);
        req.setAttribute("faculties", faculties);
        LOG.debug("Set request attribute facultiesList ==> " + faculties);
        List<Subject> subjects = subjectService.findAllByFaculty(Integer.parseInt(facultyId));
        req.setAttribute("subjects", subjects);
        LOG.debug("Set subjects to this faculty ==> " + subjects);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
