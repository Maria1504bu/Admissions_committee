//package command.admin;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import services.interfaces.SubjectService;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class DeleteSubjectCommandTest {
//
//    @Mock
//    SubjectService subjectService;
//
//    @Mock
//    HttpServletRequest req;
//
//    @Test
//    void execute() {
//        MockitoAnnotations.initMocks(this);
//        DeleteSubjectCommand command = new DeleteSubjectCommand(subjectService);
//        when(req.getParameter("deleteSubjectId")).thenReturn("7");
//
//        assertEquals("redirect:/controller?command=subjects", command.execute(req));
//        verify(subjectService).delete(7);
//    }
//}