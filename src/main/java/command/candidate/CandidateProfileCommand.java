package command.candidate;

import command.ActionCommand;
import managers.ConfigurationManager;
import models.Candidate;
import org.apache.log4j.Logger;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CandidateProfileCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(CandidateProfileCommand.class);
    private CandidateService candidateService;
    public CandidateProfileCommand(CandidateService candidateService){
        this.candidateService = candidateService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        LOG.debug("Start candidateProfileCommand");
        Candidate candidate = (Candidate) req.getSession().getAttribute("candidate");
        int candidatesId = candidate.getId();

        Map<String, Integer> candidatesSubjects = null;
//        try {
//            candidatesSubjects = new SubjectDaoImpl().findAllByCandidatesId(candidatesId);
//        } catch (DaoException e) {
//            throw new RuntimeException(e);
//        }
//        req.getSession().setAttribute("candidatesSubjects", candidatesSubjects);
//        LOG.debug("set subjects passed by candidate to session attribute candidatesSubjects => " + candidatesSubjects);
//
//        List<Subject> notPassedSubjects = null;
//        try {
//            notPassedSubjects = new SubjectDaoImpl().findAll();
//            for(String subjectName : candidatesSubjects.keySet()) {
//                notPassedSubjects.removeIf(subject -> subject.getName().equals(subjectName));
//            }} catch (DaoException e) {
//            throw new RuntimeException(e);
//        }
//        req.getSession().setAttribute("notPassedSubjects", notPassedSubjects);
//        LOG.debug("set not passed subjects by this candidate for select it to add to session attribute notPassedSubjects => " + notPassedSubjects);

        String page = ConfigurationManager.getProperty("path.candidate.candidateProfile");
        LOG.debug("Go to candidateProfile.jsp");
        return page;
    }
}
