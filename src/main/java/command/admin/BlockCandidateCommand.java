package command.admin;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;

public class BlockCandidateCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(BlockCandidateCommand.class);
    private CandidateService candidateService;

    public BlockCandidateCommand(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        LOG.debug("BlockCandidateCommand starts");
        String page = ConfigurationManager.getProperty("redirect") +
                ConfigurationManager.getProperty("admin.candidates");
        String candidateId = req.getParameter("candidateToBlockId");
        LOG.debug("Candidate id to (un)block ==> " + candidateId);

        candidateService.blockCandidate(Integer.parseInt(candidateId));

        LOG.debug("Go to ==> " + page);
        return page;
    }
}
