//package models;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SubjectTest {
//
//    Subject subject;
//    @BeforeEach
//    void setup() {
//        ArrayList nameList = new ArrayList<>();
//        subject = new Subject(1, "Math", "Математика", 50);
//    }
//
//    @Test
//    void getId() {
//        assertEquals(1, subject.getId());
//    }
//
//    @Test
//    void setId() {
//       subject.setId(3);
//        assertEquals(3, subject.getId());
//    }
//
//    @Test
//    void getNameEn() {
//        assertEquals("Math",  subject.getNameEn());
//    }
//
//    @Test
//    void getNameUk() {
//        assertEquals("Математика", subject.getNameUk());
//    }
//
//    @Test
//    void getCourseDuration() {
//        assertEquals(50, subject.getMaxGrade());
//    }
//
//    @Test
//    void setCourseDuration() {
//        subject.setMaxGrage(78);
//        assertEquals(78, subject.getMaxGrade());
//    }
//
//    @Test
//    void testEqualsTrue() {
//        Subject equalSubject = new Subject(1, "Math", "Математика", 50);
//        assertTrue(subject.equals(equalSubject));
//    }
//
//    @Test
//    void testEqualsFalse() {
//        Subject notEqualSubject = new Subject(1, "Mathes", "Математика", 50);
//        assertFalse(subject.equals(notEqualSubject));
//    }
//
//    @Test
//    void testHashCode() {
//        Subject subject = new Subject(1, null, null,50);
//        assertEquals(30802, subject.hashCode());
//    }
//
//    @Test
//    void testToString() {
//        assertEquals("Subject{id=1, nameEn=Math, nameUk=Математика, courseDuration=50}", subject.toString());
//    }
//}