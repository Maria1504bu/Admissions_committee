package command.admin;


import com.google.protobuf.ServiceException;
import command.ActionCommand;
import managers.ConfigurationManager;
import models.*;
import org.apache.log4j.Logger;
import services.MailSender;
import services.interfaces.ApplicationService;
import services.interfaces.CandidateService;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateRegisterCommand implements ActionCommand {
    private final ApplicationService applicationService;
    private final FacultyService facultyService;
    private final CandidateService candidateService;

    public CreateRegisterCommand(ApplicationService applicationService, FacultyService facultyService, CandidateService candidateService){
        this.applicationService = applicationService;
        this.facultyService = facultyService;
        this.candidateService = candidateService;
    }
    private static final Logger LOG = Logger.getLogger(CreateRegisterCommand.class);
    /**
     * Can`t set 'phase' attribute explicitly at js function because of peculiarity their performance
     * @param req
     * @param resp
     * @return
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("CreateRegisterCommand starts");
        String page = ConfigurationManager.getProperty("path.command.faculties");
        String phase = req.getParameter("phase");
        LOG.trace("Parameter phase from request ==> " + phase);
        req.getServletContext().setAttribute("phase", phase);
        LOG.trace("Set application attribute phase ==> " + phase);
        if(phase.equals("completedRegister")){
            LOG.debug("Create registers");
            List<Faculty> faculties = facultyService.getSortedList("UK", "name", "ASC");
            for(Faculty faculty : faculties){
                applicationService.createRegister(faculty);
                LOG.debug("Register for faculty ==> " + faculty + " created");
            }
        }

        //todo: remake
        MailSender sender = new MailSender();
        List<Candidate> candidates = candidateService.getAll();
        for(Candidate candidate : candidates){
List<Application> candAppls = applicationService.getCandidatesAppls(String.valueOf(candidate.getId()));
           String email = candidate.getEmail();
           String facultyName = null;
           //get(0) because if 1 appl by candidate was provided another is deleted by my logic
        if(candAppls.get(0).getApplicationStatus().equals(ApplicationStatus.DOCUMENTS_PROVIDED)){
                try {
                    facultyName = facultyService.getById(String.valueOf(candAppls.get(0).getFaculty().getId())).getNames().get(Language.EN);
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
            }
            sender.sendGmail(email, facultyName);
        }

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
