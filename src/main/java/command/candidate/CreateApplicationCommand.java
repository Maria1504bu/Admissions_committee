package command.candidate;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Candidate;
import models.Faculty;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CreateApplicationCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(CreateApplicationCommand.class);
    private final FacultyService facultyService;
    public CreateApplicationCommand(FacultyService facultyService){

        this.facultyService = facultyService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        LOG.debug("CreateApplicationCommand starts");
        HttpSession session = req.getSession();
        Candidate candidate = (Candidate) session.getAttribute("user");
        LOG.trace("CreateApplicationCommand get user from session --> " + candidate);
        String language = req.getLocale().getLanguage();
        LOG.trace("CreateApplicationCommand get language --> " + language);


        List<Faculty> facultiesList = facultyService.findAll();
        LOG.debug("CreateApplicationCommand to board facultiesList size --> " + facultiesList.size());
    //    LOG.debug("CreateApplicationCommand getSubjectList().get(0) --> " + facultiesList.get(0).getSubjectList().get(0));
        session.setAttribute("facultiesList", facultiesList);
        LOG.trace("Set session attribute facultiesList ==> " + facultiesList);

        String selectFacultyPriority1 = req.getParameter("selectedFaculty1");
        String selectFacultyPriority2 = req.getParameter("selectedFaculty2");
        String selectFacultyPriority3 = req.getParameter("selectedFaculty3");

        LOG.debug("selectFacultyPriority1 --> " + selectFacultyPriority1);
        LOG.debug("selectFacultyPriority2 --> " + selectFacultyPriority2);
        LOG.debug("selectFacultyPriority3 --> " + selectFacultyPriority3);

        String page = ConfigurationManager.getProperty("path.candidate.createApplication");

        if (selectFacultyPriority1 == null && selectFacultyPriority2 == null &&
                selectFacultyPriority3 == null) {
            session.setAttribute("priority1Subjects", 1);
            session.setAttribute("priority2Subjects", 2);
            session.setAttribute("priority3Subjects", 3);
            return page;
        }
        if (selectFacultyPriority1 != null && selectFacultyPriority2 != null &&
                selectFacultyPriority3 != null) {
            session.setAttribute("priority1Subjects", selectFacultyPriority1);
            session.setAttribute("priority2Subjects", selectFacultyPriority2);
            session.setAttribute("priority3Subjects", selectFacultyPriority3);
            return page;
        }


        LOG.debug("CreateApplication command finished with ==> " + page);
        return page;
    }
}
