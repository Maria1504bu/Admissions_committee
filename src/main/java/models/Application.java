package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Application implements Serializable {
    private static final long serialVersionUID = -6038504292558516492L;
    private int id;
    private Candidate candidate;
    private Faculty faculty;
    private List<Grade> gradesList;
    private int priority;
    private ApplicationStatus applicationStatus;

    public Application(){
        this.gradesList = new ArrayList<>();
    }
    public Application(int id, Candidate candidate, Faculty faculty, List<Grade> grList, int priority, ApplicationStatus applicationStatus){
        this.id = id;
        this.candidate = candidate;
        this.faculty = faculty;
        this.priority = priority;
        this.applicationStatus = applicationStatus;
        this.gradesList = new ArrayList<>();
        listInitialize(grList);
    }

    public Application(Candidate candidate, Faculty faculty, List<Grade> grList, int priority, ApplicationStatus applicationStatus){
        this.candidate = candidate;
        this.faculty = faculty;
        this.priority = priority;
        this.applicationStatus = applicationStatus;
        this.gradesList = new ArrayList<>();
        listInitialize(grList);
    }
    private void listInitialize(List<Grade> grList) {
        gradesList.addAll(grList);
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public List<Grade> getGradesList() {
        return gradesList;
    }

    public static Comparator<Application> COMPARE_BY_AVERAGE_GRADE =
            (appl1, appl2) -> {
                int appl1AverGrade = 0;
                int appl2AverGrade = 0;
                for(Grade g : appl1.getGradesList()){
                    appl1AverGrade += g.getGrade();
                }
                if(appl1.getGradesList().size() > 0)
                    appl1AverGrade = appl1AverGrade / appl1.getGradesList().size();

                for(Grade g : appl2.getGradesList()){
                    appl2AverGrade += g.getGrade();
                }
                if(appl2.getGradesList().size() > 0)
                    appl2AverGrade = appl2AverGrade / appl2.getGradesList().size();
                return appl1AverGrade - appl2AverGrade;
            };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id &&
                priority == that.priority &&
                Objects.equals(candidate, that.candidate) &&
                Objects.equals(faculty, that.faculty) &&
                Objects.equals(gradesList, that.gradesList) &&
                applicationStatus == that.applicationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, candidate, faculty, gradesList, priority, applicationStatus);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", candidate=" + candidate +
                ", faculty=" + faculty +
                ", gradesList=" + gradesList +
                ", priority=" + priority +
                ", applicationStatus=" + applicationStatus +
                '}';
    }
}
