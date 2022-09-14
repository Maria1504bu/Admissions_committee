package command.admin;

import command.ActionCommand;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditFacultyCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(EditFacultyCommand.class);
    private FacultyService facultyService;
    public EditFacultyCommand(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) {
        return null;
    }
}
