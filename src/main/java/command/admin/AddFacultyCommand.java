package command.admin;

import command.ActionCommand;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;

public class AddFacultyCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(AddFacultyCommand.class);
    private FacultyService facultyService;
    public AddFacultyCommand(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @Override
    public String execute(HttpServletRequest req) {
        return null;
    }
}
