//package models;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class GradeTest {
//    Grade grade;
//    @BeforeEach
//    void setup() {
//        Subject subject = new Subject(38, "Physic", "Фізика");
//        grade = new Grade(23,subject, 148);
//    }
//
//    @Test
//    void getId() {
//        assertEquals(23, grade.getId());
//    }
//
//    @Test
//    void setId() {
//        grade.setId(14);
//        assertEquals(14, grade.getId());
//    }
//
//    @Test
//    void getSubject() {
//        Subject subject = new Subject(38, "Physic", "Фізика");
//        assertEquals(subject, grade.getSubject());
//    }
//
//    @Test
//    void setSubject() {
//        Subject subject = new Subject(47, "Engineering", "Інженерія");
//        grade.setSubject(subject);
//        assertSame(subject, grade.getSubject());
//    }
//
//    @Test
//    void getGrade() {
//        assertEquals(148, grade.getGrade());
//    }
//
//    @Test
//    void setGrade() {
//        grade.setGrade(25);
//        assertEquals(25, grade.getGrade());
//    }
//
//    @Test
//    void testEqualsFalse() {
//        Subject subject = new Subject(32, "Physic", "Фізика");
//        Grade notEqualGrade = new Grade(23,subject, 148);
//        assertFalse(grade.equals(notEqualGrade));
//    }
//
//    @Test
//    void testHashCode() {
//        Subject subject = new Subject(38,"Physic", "Фізика");
//        Grade equalGrade = new Grade(23,subject, 148);
//        assertEquals(grade.hashCode(), equalGrade.hashCode());
//    }
//
//    @Test
//    void testToString() {
//        assertEquals("Grade{id=23, subject=Subject{id=0, nameList=[Physic, Фізика], maxGrade=38}," +
//                " grade=148}", grade.toString());
//    }
//}