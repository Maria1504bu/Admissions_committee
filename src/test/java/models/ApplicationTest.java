//package models;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//
//class ApplicationTest {
//
//    Application application;
//
//    @BeforeEach
//    void setUp() {
//        Candidate candidate = Candidate.builder().id(25).firstName("Alex").build();
//        Faculty faculty = new Faculty(3);
//        List grades = List.of(new Grade(new Subject(50, "Math", "М"), 123));
//        application = new Application(5, candidate, faculty, grades, 1, ApplicationStatus.BUDGET_APPROVED);
//    }
//
//    @Test
//    void getId() {
//        Assertions.assertEquals(5, application.getId());
//    }
//
//    @Test
//    void setId() {
//        application.setId(17);
//        Assertions.assertEquals(17, application.getId());
//    }
//
//    @Test
//    void getFaculty() {
//        Assertions.assertEquals(new Faculty(3), application.getFaculty());
//    }
//
//    @Test
//    void setFaculty() {
//        Faculty faculty = new Faculty("Engineering", "Інженерія", 12, 20);
//        application.setFaculty(faculty);
//        Assertions.assertEquals(new Faculty("Engineering", "Інженерія", 12, 20), application.getFaculty());
//    }
//
//    @Test
//    void getCandidate() {
//        Assertions.assertEquals(Candidate.builder().id(25).firstName("Alex").build(), application.getCandidate());
//    }
//
//    @Test
//    void setCandidate() {
//        Candidate candidateToEquals = Candidate.builder().fatherName("Smith").schoolName("school 23").build();
//        application.setCandidate(candidateToEquals);
//        Assertions.assertSame(candidateToEquals, application.getCandidate());
//    }
//
//    @Test
//    void getPriority() {
//        Assertions.assertEquals(1, application.getPriority());
//    }
//
//    @Test
//    void setPriority() {
//        application.setPriority(3);
//        Assertions.assertEquals(3, application.getPriority());
//    }
//
//    @Test
//    void getApplicationStatus() {
//        Assertions.assertEquals(ApplicationStatus.BUDGET_APPROVED, application.getApplicationStatus());
//    }
//
//    @Test
//    void setApplicationStatus() {
//        application.setApplicationStatus(ApplicationStatus.NOT_APPROVED);
//        Assertions.assertEquals(ApplicationStatus.NOT_APPROVED, application.getApplicationStatus());
//    }
//
//    @Test
//    void getGradesList() {
//        Assertions.assertEquals(List.of(new Grade(new Subject(50, "Math", "Математика"), 123)), application.getGradesList());
//    }
//
//    @Test
//    void setGradesList() {
//        List newGrades = List.of(new Grade(new Subject(28, "English", "Англійська", 170), 117));
//        application.setGradesList(newGrades);
//        Assertions.assertEquals(newGrades, application.getGradesList());
//    }
//
//    @Test
//    void testEqualsTrue() {
//        Candidate candidate = Candidate.builder().id(25).firstName("Alex").build();
//        Faculty faculty = new Faculty(3);
//        List grades = List.of(new Grade(new Subject(50, "Math", "Математика"), 123));
//        Application equalApplication = new Application(5, candidate, faculty, grades, 1, ApplicationStatus.BUDGET_APPROVED);
//        Assertions.assertTrue(application.equals(equalApplication));
//    }
//
//    @Test
//    void testEqualsFalse() {
//        Candidate candidate = Candidate.builder().id(25).firstName("Dmytro").build();
//        Faculty faculty = new Faculty(3);
//        List grades = List.of(new Grade(new Subject(50,"Math", "Математика"), 123));
//        Application notEqualApplication = new Application(5, candidate, faculty, grades, 1, ApplicationStatus.BUDGET_APPROVED);
//        Assertions.assertFalse(application.equals(notEqualApplication));
//    }
//
//    @Test
//    void testHashCode() {
//        int applHash = application.hashCode();
//        Candidate candidate = Candidate.builder().id(25).firstName("Alex").build();
//        Faculty faculty = new Faculty(3);
//        List grades = List.of(new Grade(new Subject(50, "Math", "Математика"), 123));
//        Application equalApplication = new Application(5, candidate, faculty, grades, 1, ApplicationStatus.BUDGET_APPROVED);
//        int equalApplHash = equalApplication.hashCode();
//        Assertions.assertEquals(applHash, equalApplHash);
//
//    }
//
//    @Test
//    void testToString() {
//        Assertions.assertEquals("Application{id=5, candidate=Candidate{id=25, email='null', password='null'," +
//                " role=null, firstName='Alex', fatherName='null', secondName='null', certificate'null', city='null', " +
//                "schoolName='null', isBlocked=false, applicationsList=null, applicationDate=null}, faculty=Faculty{id=3," +
//                " namesList=null, budgetPlaces=0, totalPlaces=0, subjectList=null}, gradesList=[Grade{id=0, " +
//                "subject=Subject{id=0, nameList=[Math], maxGrade=50}, grade=123}], " +
//                "applicationStatus=BUDGET_APPROVED}", application.toString());
//    }
//}