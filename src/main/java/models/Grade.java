package models;

import java.io.Serializable;
import java.util.Objects;


public class Grade implements Serializable {
    private int id;
    private Candidate candidate;
    private Subject subject;
    private int grade;

    public Grade() {
    }

    public Grade(int id, Subject subject, int grade) {
        this.id = id;
        this.subject = subject;
        this.grade = grade;

    }

    public Grade(Subject subject, int grade) {
        this.subject = subject;
        this.grade = grade;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return id == grade1.id &&
                grade == grade1.grade &&
                Objects.equals(candidate, grade1.candidate) &&
                Objects.equals(subject, grade1.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, candidate, subject, grade);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                "candidate=" + candidate +
                ", subject=" + subject +
                ", grade=" + grade +
                '}';
    }
}
