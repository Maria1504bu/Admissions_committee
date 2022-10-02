package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application implements Serializable {
    private int id;
    private Candidate candidate;
    private Faculty faculty;
    private List<Grade> gradesList;
    private int ratingScore;
    private ApplicationStatus applicationStatus;

    public Application() {
        this.gradesList = new ArrayList<>();
    }

    public Application(int id, Candidate candidate, Faculty faculty, List<Grade> gradeList, int ratingScore, ApplicationStatus applicationStatus) {
        this.id = id;
        this.candidate = candidate;
        this.faculty = faculty;
        this.applicationStatus = applicationStatus;
        this.ratingScore = ratingScore;
        this.gradesList = gradeList;
    }

    public Application(Candidate candidate, Faculty faculty, List<Grade> gradeList, int ratingScore, ApplicationStatus applicationStatus) {
        this.candidate = candidate;
        this.faculty = faculty;
        this.gradesList = gradeList;
        this.ratingScore = ratingScore;
        this.applicationStatus = applicationStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public List<Grade> getGradesList() {
        return gradesList;
    }
    public void setGradesList(List<Grade> gradesList) {
        this.gradesList = gradesList;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id &&
                Objects.equals(candidate, that.candidate) &&
                Objects.equals(faculty, that.faculty) &&
                Objects.equals(gradesList, that.gradesList) &&
                Objects.equals(ratingScore, that.ratingScore) &&
                Objects.equals(applicationStatus, that.applicationStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, candidate, faculty, gradesList, ratingScore, applicationStatus);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", candidate=" + candidate +
                ", faculty=" + faculty +
                ", gradesList=" + gradesList +
                ", ratingScore=" + ratingScore +
                ", applicationStatus=" + applicationStatus +
                '}';
    }
}
