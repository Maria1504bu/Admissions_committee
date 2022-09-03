package command.admin;

import org.junit.jupiter.api.Test;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class BlockCandidateCommandTest {

    @Test
    void execute() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        CandidateService candidateService = mock(CandidateService.class);
        BlockCandidateCommand command = new BlockCandidateCommand(candidateService);

        when(req.getParameter("candidateToBlockId")).thenReturn("1");

        command.execute(req);

        verify(req, times(0)).getSession();
        verify(req, times(1)).getParameter("candidateToBlockId");
        verify(candidateService, times(1)).blockCandidate(1);
    }
}