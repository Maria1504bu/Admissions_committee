package command.admin;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteFacultyCommandTest {
    @Mock
    FacultyService facultyService;

    @Mock
    HttpServletRequest req;

    @Test
    void execute() {
        MockitoAnnotations.initMocks(this);
        DeleteFacultyCommand command = new DeleteFacultyCommand(facultyService);
        when(req.getParameter("deleteFacultyId")).thenReturn("5");

        assertEquals("redirect:/controller?command=faculties", command.execute(req));
        verify(facultyService).delete(5);
    }
}