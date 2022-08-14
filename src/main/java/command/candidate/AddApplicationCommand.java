package command.candidate;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;

import javax.servlet.http.HttpServletRequest;

public class AddApplicationCommand implements ActionCommand {
    private static final Logger logger = Logger.getLogger(AddApplicationCommand.class);
    private  static final String PARAM_NAME_EXAM_ID = "examId";
    private  static final String PARAM_NAME_MARK = "mark";
    private ApplicationService applicationService;
    public AddApplicationCommand(ApplicationService applicationService){

        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        logger.debug("AddSubjectCommand starts");
        String page = ConfigurationManager.getProperty("path.command.candidateProfile");
        return page;
    }
}
