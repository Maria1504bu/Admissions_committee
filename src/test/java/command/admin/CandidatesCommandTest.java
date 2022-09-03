package command.admin;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.interfaces.CandidateService;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CandidatesCommandTest {

    @Mock
    CandidateService candidateService;

    @Mock
    FacultyService facultyService;

    @Mock
    HttpServletRequest req;

    @Test
    void execute() {
        MockitoAnnotations.initMocks(this);
        CandidatesCommand command = new CandidatesCommand(candidateService, facultyService);
        when(req.getLocale()).thenReturn(Locale.ENGLISH);
        when(req.getParameter("selectedFacultyId")).thenReturn("3");

        assertEquals("/jsp/admin/candidates.jsp", command.execute(req));

        verify(req, times(3)).setAttribute(anyString(), any());
        verify(facultyService, times(1)).getSortedList("en", null, null);
        verify(candidateService, times(1)).getCandidatesListSize("3");

    }
}