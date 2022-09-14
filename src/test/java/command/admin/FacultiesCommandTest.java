//package command.admin;
//
//import command.common.FacultiesCommand;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import services.interfaces.FacultyService;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Locale;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//class FacultiesCommandTest {
//    @Mock
//    FacultyService facultyService;
//
//    @Mock
//    HttpServletRequest req;
//
//    @Test
//    void execute() {
//        MockitoAnnotations.initMocks(this);
//        FacultiesCommand command = new FacultiesCommand(facultyService);
//        when(req.getLocale()).thenReturn(Locale.FRENCH);
//        Assertions.assertEquals("/jsp/admin/faculties.jsp", command.execute(req));
//        verify(req, times(3)).getParameter(anyString());
//        verify(facultyService).getSortedList("fr", null, null);
//        verify(req, times(1)).setAttribute(eq("faculties"), any());
//    }
//}