package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Language;
import models.Subject;
import org.apache.log4j.Logger;
import services.interfaces.SubjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateSubjectCommand implements ActionCommand {
    private final SubjectService subjectService;

    public UpdateSubjectCommand(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    private static final Logger LOG = Logger.getLogger(UpdateSubjectCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) {
        LOG.debug("UpdateSubjectCommand starts");
        HttpSession session = request.getSession();
        String page;

        String subjIdToUpdate = request.getParameter("updateSubjectId");

        if(subjIdToUpdate != null)
        {
            Subject subToDisplay = subjectService.getById(subjIdToUpdate);
            session.setAttribute("subToDisplay", subToDisplay);
            page = ConfigurationManager.getProperty("admin.updateSubject");
            return page;
        }

        Subject subjectToUpdate = new Subject();
        String subjId = request.getParameter("subjId");
        String maxGrade = request.getParameter("maxGrade");

        String subjEngName = request.getParameter("subjEngName");
        String subjUkrName = request.getParameter("subjUkrName");

        subjectToUpdate.setId(Integer.parseInt(subjId));
        subjectToUpdate.setMaxGrade(Integer.parseInt(maxGrade));
        subjectToUpdate.getNames().put(Language.EN, subjEngName);
        subjectToUpdate.getNames().put(Language.UK, subjUkrName);

        LOG.debug("subjUkrName to write to db --> " + subjUkrName);
        subjectService.update(subjectToUpdate);

        page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("path.command.subjects");
        LOG.debug("Go to ==> " + page);
        return page;
    }
}
