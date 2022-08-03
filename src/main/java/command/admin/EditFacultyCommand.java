package command.admin;

import command.ActionCommand;
import org.apache.log4j.Logger;
import services.FacultyService;

import javax.servlet.http.HttpServletRequest;

public class EditFacultyCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(EditFacultyCommand.class);
    private FacultyService facultyService;
    public EditFacultyCommand(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        return null;
    }
}
