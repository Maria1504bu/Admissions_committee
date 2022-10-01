package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.EmptyFieldsException;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class SaveFacultyCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(SaveFacultyCommand.class);
    private final FacultyService facultyService;

    public SaveFacultyCommand(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
            LOG.debug("CreateFacultyCommand starts -->" + request);
            String page = ConfigurationManager.getProperty("redirect") +
                    ConfigurationManager.getProperty("path.command.faculties");


            // obtain data from the request
            String englishName = request.getParameter("englishName");
            LOG.debug("Request parameter: englishName --> " + englishName);
            String ukrainianName = request.getParameter("ukrainianName");
            LOG.trace("Request parameter: ukrainianName --> " + ukrainianName);
            String budgetQty = request.getParameter("budgetQty");
            LOG.trace("Request parameter: budgetQty --> " + budgetQty);
            String totalQty = request.getParameter("totalQty");
            LOG.trace("Request parameter: totalQty --> " + totalQty);
            String[] subjectsIds = request.getParameterValues("subject");
            LOG.debug("Request parameter: subject --> " + Arrays.toString(subjectsIds));
        String[] subjectsCoefs = request.getParameterValues("coefficient");
        LOG.debug("Request parameter: coefficient --> " + Arrays.toString(subjectsCoefs));


        try {
            facultyService.save(englishName, ukrainianName, budgetQty, totalQty, subjectsIds, subjectsCoefs);
        } catch (EmptyFieldsException e) {
            throw new RuntimeException(e);
        }

        LOG.debug("Go to ==> " + page);
            return page;
    }
}
