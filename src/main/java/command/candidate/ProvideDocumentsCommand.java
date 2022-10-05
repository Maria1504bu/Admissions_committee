package command.candidate;

import command.ActionCommand;
import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import managers.ConfigurationManager;
import models.Application;
import models.ApplicationStatus;
import models.Candidate;
import org.apache.log4j.Logger;
import services.interfaces.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProvideDocumentsCommand implements ActionCommand {

    private final ApplicationService applicationService;

    private static final Logger LOG = Logger.getLogger(ProvideDocumentsCommand.class);

    public ProvideDocumentsCommand(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("ProvideDocumentsCommand starts");
        String page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("common.applications");
        Candidate candidate = (Candidate) req.getSession().getAttribute("user");
        int applId = Integer.parseInt(req.getParameter("application"));
        Application application = new Application(applId, candidate, null, null, 0, ApplicationStatus.DOCUMENTS_PROVIDED);
        try {
            applicationService.provideDocuments(application);
        } catch (AlreadyExistException | WrongExecutedQueryException e) {
            req.setAttribute("errorMessage", e);
        }
        LOG.debug("Go to ==> " + page);
        return page;
    }
}
