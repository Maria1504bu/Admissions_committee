package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;
import services.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class FacultiesCommand implements ActionCommand {
    private static final Logger logger = Logger.getLogger(FacultiesCommand.class);

    private FacultyService facultyService;

    public FacultiesCommand(FacultyService facultyService){
        this.facultyService = facultyService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        logger.debug("Start facultiesCommand");
        String page = null;
        List faculties = null;
        try{
            faculties = facultyService.findAll();
        } catch (ServiceException e){
            req.setAttribute("errorMessage", e.getMessage());
        }
        req.setAttribute("faculties", faculties);
        logger.debug("Set attribute exams with all created faculties => " + faculties);

        page = ConfigurationManager.getProperty("path.admin.faculties");
        logger.debug("Go to faculties.jsp page");
        return page;
    }
}
