package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Faculty;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class FacultiesCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(FacultiesCommand.class);

    private FacultyService facultyService;

    public FacultiesCommand(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        LOG.debug("Start facultiesCommand");
        String page = ConfigurationManager.getProperty("admin.faculties");
        String lang = req.getLocale().getLanguage();
        LOG.debug("Language from locale ==> " + lang);

        String facultySort = req.getParameter("facultySort");
        LOG.debug("facultySort value ==> " + facultySort);

        String order = req.getParameter("order");
        LOG.debug("order value ==> " + order);

        String orderBy = req.getParameter("by");
        LOG.debug("orderBy value ==> " + orderBy);

        List<Faculty> faculties = facultyService.getSortedList(lang, orderBy, order);

        req.getSession().setAttribute("faculties", faculties);
        LOG.debug("Set session attribute faculties => " + faculties + " sorted by ==> " + facultySort);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
