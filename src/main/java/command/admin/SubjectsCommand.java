package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Subject;
import org.apache.log4j.Logger;
import services.interfaces.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SubjectsCommand implements ActionCommand {
    private final SubjectService subjectService;

    public SubjectsCommand(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    private static final Logger LOG = Logger.getLogger(SubjectsCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOG.debug("SubjectBoardCommand starts -->" + request);
        String page = ConfigurationManager.getProperty("admin.subjects");
        List<Subject> subjects = subjectService.findAll();
        LOG.debug("Set attribute list of subjects with size --> " + subjects.size());
        request.setAttribute("subjects", subjects);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
