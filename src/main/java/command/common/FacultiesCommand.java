package command.common;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Faculty;
import models.Role;
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
        String page = null;
        String lang = req.getLocale().getLanguage();
        LOG.debug("Language from locale ==> " + lang);

        Role role = (Role) req.getSession().getAttribute("role");
        LOG.debug("Attribute role ==> " + role);
        if(role == null || role == Role.CANDIDATE){
            page = ConfigurationManager.getProperty("common.index");
        } else if (role == Role.ADMIN){
            page = ConfigurationManager.getProperty("admin.faculties");
        }

        String facultySort = req.getParameter("facultySort");
        LOG.debug("facultySort value ==> " + facultySort);

        String order = req.getParameter("order");
        LOG.debug("order value ==> " + order);

        String orderBy = req.getParameter("by");
        LOG.debug("orderBy value ==> " + orderBy);

        List<Faculty> faculties = facultyService.getSortedList(lang, orderBy, order);

        req.setAttribute("faculties", faculties);
        LOG.debug("Set session attribute faculties => " + faculties + " sorted by ==> " + facultySort);

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
