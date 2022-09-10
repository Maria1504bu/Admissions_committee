package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Subject;
import org.apache.log4j.Logger;
import services.interfaces.SubjectService;

import javax.servlet.http.HttpServletRequest;

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

        String maxGrade = request.getParameter("maxGrade");
        LOG.trace("Request parameter: maxGrade --> " + maxGrade);
        int duration = Integer.parseInt(maxGrade);

        if (englishName == null || ukrainianName == null || maxGrade == null ||
                englishName.isEmpty() || ukrainianName.isEmpty() || maxGrade.isEmpty()) {
        }


        Subject subject = new Subject(duration);

        subjectService.save(subject);
        LOG.debug("SaveSubjectCommand save subject ==> " + subject);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
