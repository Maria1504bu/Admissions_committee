package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Subject;
import org.apache.log4j.Logger;
import services.interfaces.SubjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PrepareFacultyFormCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(PrepareFacultyFormCommand.class);
    private SubjectService subjectService;
    public PrepareFacultyFormCommand(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) {
        LOG.debug("CreateFacultyCommand starts");
        String page = ConfigurationManager.getProperty("admin.createFaculty");

        List<Subject> subjects = subjectService.findAll();
        req.setAttribute("subjects", subjects);
        LOG.trace("Put to attributes list of subjects with size ==> " + subjects.size());

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
