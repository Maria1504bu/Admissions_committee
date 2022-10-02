package command.candidate;

import command.ActionCommand;
import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import managers.ConfigurationManager;
import models.*;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateApplicationCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(CreateApplicationCommand.class);
    private final ApplicationService applicationService;
    public CreateApplicationCommand(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) {
        LOG.debug("CreateApplicationCommand starts");
        String page =  ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("common.applications");
        HttpSession session = req.getSession();
        Candidate candidate = (Candidate) session.getAttribute("user");
        LOG.trace("User from session ==> " + candidate);
        Faculty faculty = (Faculty) req.getSession().getAttribute("faculty");
        LOG.trace("faculty to application ==> " + faculty);
        List<Grade> grades = new ArrayList<>();
        if(faculty != null){
        for(Map.Entry<Subject, Integer> entry : faculty.getSubjectsWithCoefs().entrySet()){
            Subject subject = entry.getKey();
            int mark = Integer.parseInt(req.getParameter(String.valueOf(subject.getId())));
            LOG.debug("Grade ==> " + mark + " for subject with id ==> " + subject.getId());
            Grade grade = new Grade(subject, mark);
            LOG.debug("Add grade ==> " + grade + " to grades");
            grades.add(grade);
        }}
        Application application = new Application(candidate, faculty, grades, 0, ApplicationStatus.NOT_APPROVED);
        LOG.trace("Create application to save at db");
        try {
            applicationService.saveWithGrades(application);
        } catch (AlreadyExistException | WrongExecutedQueryException e) {
            req.setAttribute("errorMessage", e.getMessage());
        }
        List<Application> applications = applicationService.getFacultyAppls(String.valueOf(faculty.getId()));
        LOG.trace("Applications to faculty ==> " + applications);
        req.setAttribute("appls", applications);
        LOG.debug("Set appls attribute ==> " + applications);
        LOG.debug("Go to ==> " + page);
        return page;
    }
}
