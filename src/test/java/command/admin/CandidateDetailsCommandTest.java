package command.admin;

import org.junit.jupiter.api.Test;
import services.interfaces.ApplicationService;
import services.interfaces.CandidateService;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;

import static org.mockito.Mockito.*;

class CandidateDetailsCommandTest {

    @Test
    void execute() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        CandidateService candidateService = mock(CandidateService.class);
        ApplicationService applicationService = mock(ApplicationService.class);
        CandidateDetailsCommand command = new CandidateDetailsCommand(candidateService, applicationService);

        when(req.getParameter("selectedCandidateId")).thenReturn("5");
        when(req.getLocale()).thenReturn(Locale.ENGLISH);

        command.execute(req);

        verify(req, times(0)).getSession();
        verify(req, times(1)).getParameter("selectedCandidateId");
        verify(req, times(2)).setAttribute(anyString(), any());
        verify(candidateService, times(1)).getById(5);
        verify(applicationService, times(1)).getCandidatesAppls(5, "en");
    }
}