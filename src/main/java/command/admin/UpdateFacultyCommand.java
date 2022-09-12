package command.admin;

import com.google.protobuf.ServiceException;
import command.ActionCommand;
import managers.ConfigurationManager;
import models.Faculty;
import models.Language;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Get change parameters of faculty from request and update the faculty in the db
 */
public class UpdateFacultyCommand implements ActionCommand {
    private final FacultyService facultyService;

    public UpdateFacultyCommand(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    private static final Logger LOG = Logger.getLogger(UpdateFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOG.debug("UpdateFacultyCommand starts");
        String page;

        HttpSession session = request.getSession();
        String facultyIdToUpdate = request.getParameter("updateFacultyId");

        LOG.debug("UpdateFacultyCommand facultyIdToUpdate value ==> " + facultyIdToUpdate);
        if (facultyIdToUpdate != null) {
            Faculty facultyToDisplay = null;
            try {
                facultyToDisplay = facultyService.getById(Integer.parseInt(facultyIdToUpdate));
                LOG.trace("Faculty, which need to update ==> " + facultyToDisplay);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
            session.setAttribute("facultyToDisplay", facultyToDisplay);
            LOG.debug("Set attribute facultyToDisplay ==> " + facultyToDisplay);
            page = ConfigurationManager.getProperty("admin.updateFaculty");
            LOG.trace("Go to ==> " + page);
            return page;
        } else {
            String facultyId = request.getParameter("facultyId");
            String budgetPlaces = request.getParameter("budgetQty");
            String totalPlaces = request.getParameter("totalQty");

            String facultyEngName = request.getParameter("englishName");
            String facultyUkrName = request.getParameter("ukrainianName");

            Faculty facultyToUpdate = new Faculty();

            facultyToUpdate.setId(Integer.parseInt(facultyId));
            facultyToUpdate.setBudgetPlaces(Integer.parseInt(budgetPlaces));
            facultyToUpdate.setTotalPlaces(Integer.parseInt(totalPlaces));
            facultyToUpdate.getNames().put(Language.EN, facultyEngName);
            facultyToUpdate.getNames().put(Language.UK, facultyUkrName);


            LOG.debug("Faculty with new data, which need to update at db ==> " + facultyToUpdate);
            facultyService.update(facultyToUpdate);

            page = ConfigurationManager.getProperty("redirect") +
                    ConfigurationManager.getProperty("path.command.faculties");
        }
        LOG.debug("Go to ==> " + page);
        return page;
    }
}
