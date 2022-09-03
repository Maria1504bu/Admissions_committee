package models;

import java.io.Serializable;
import java.util.*;

public class Faculty implements Serializable {
    private static final long serialVersionUID = 3523165227703971485L;
    private int id;
    private List<String> namesList;
    private int budgetPlaces;
    private int totalPlaces;
    private List<Subject> subjectList;


    public Faculty(){
        subjectList = new ArrayList<>();
        namesList = new ArrayList<>();
    }
    public Faculty(int id){
        this.id = id;
    }
    public Faculty(int id, List<String> namesList, int budgetPlaces, int totalPlaces){
        this.id = id;
        this.namesList = namesList;
        this.budgetPlaces = budgetPlaces;
        this.totalPlaces = totalPlaces;
        this.subjectList = new ArrayList<>();
    }
    public Faculty(List<String> namesList, int budgetPlaces, int totalPlaces){
        this.namesList = namesList;
        this.budgetPlaces = budgetPlaces;
        this.totalPlaces = totalPlaces;
        this.subjectList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getNamesList() {
        return namesList;
    }

    public int getBudgetPlaces() {
        return budgetPlaces;
    }

    public void setBudgetPlaces(int budgetPlaces) {
        this.budgetPlaces = budgetPlaces;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id &&
                budgetPlaces == faculty.budgetPlaces &&
                totalPlaces == faculty.totalPlaces &&
                Objects.equals(namesList, faculty.namesList) &&
                Objects.equals(subjectList, faculty.subjectList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namesList, budgetPlaces, totalPlaces, subjectList);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", namesList=" + namesList +
                ", budgetPlaces=" + budgetPlaces +
                ", totalPlaces=" + totalPlaces +
                ", subjectList=" + subjectList +
                '}';
    }
}