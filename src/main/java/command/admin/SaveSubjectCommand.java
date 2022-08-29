package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Subject;
import org.apache.log4j.Logger;
import services.interfaces.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class SaveSubjectCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(SaveSubjectCommand.class);
    private SubjectService subjectService;
    public SaveSubjectCommand(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        LOG.debug("SaveSubjectCommand starts");
        String page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("path.command.subjects");

        // obtain data from the request
        String englishName = request.getParameter("englishName");
        LOG.debug("Request parameter: englishName --> " + englishName);
        String ukrainianName = request.getParameter("ukrainianName");
        LOG.trace("Request parameter: ukrainianName --> " + ukrainianName);

        String courseDuration = request.getParameter("courseDuration");
        LOG.trace("Request parameter: courseDuration --> " + courseDuration);
        int duration = Integer.parseInt(courseDuration);

        if (englishName == null || ukrainianName == null || courseDuration == null ||
                englishName.isEmpty() || ukrainianName.isEmpty() || courseDuration.isEmpty()) {
        }

        Subject subject = new Subject(0, Arrays.asList(englishName, ukrainianName), duration);

        subjectService.save(subject);
        LOG.debug("SaveSubjectCommand save subject ==> " + subject);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
