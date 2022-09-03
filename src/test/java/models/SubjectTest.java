package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    Subject subject;
    @BeforeEach
    void setup() {
        ArrayList nameList = new ArrayList<>();
        subject = new Subject(1, List.of("Math", "Математика"), 50);
    }

    @Test
    void getId() {
        assertEquals(1, subject.getId());
    }

    @Test
    void setId() {
       subject.setId(3);
        assertEquals(3, subject.getId());
    }

    @Test
    void getNameList() {
        assertEquals(Arrays.asList("Math", "Математика"), subject.getNameList());
    }

    @Test
    void getCourseDuration() {
        assertEquals(50, subject.getCourseDuration());
    }

    @Test
    void setCourseDuration() {
        subject.setCourseDuration(78);
        assertEquals(78, subject.getCourseDuration());
    }

    @Test
    void testEqualsTrue() {
        Subject equalSubject = new Subject(1, List.of("Math", "Математика"), 50);
        assertTrue(subject.equals(equalSubject));
    }

    @Test
    void testEqualsFalse() {
        Subject notEqualSubject = new Subject(1, Arrays.asList("Mathes", "Математика"), 50);
        assertFalse(subject.equals(notEqualSubject));
    }

    @Test
    void testHashCode() {
        Subject subject = new Subject(1, null, 50);
        assertEquals(30802, subject.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Subject{id=1, nameList=[Math, Математика], courseDuration=50}", subject.toString());
    }
}