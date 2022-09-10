package models;

import java.io.Serializable;
import java.util.*;

public class Faculty implements Serializable {
    private int id;
    private Map<Language, String> names;
    private int budgetPlaces;
    private int totalPlaces;
    private List<Subject> subjectList;


    public Faculty() {
        this.names = new EnumMap<Language, String>(Language.class);
        this.subjectList = new ArrayList<>();
    }

    public Faculty(int id) {
        this.id = id;
        this.names = new EnumMap<Language, String>(Language.class);
        this.subjectList = new ArrayList<>();
    }

    public Faculty(int id, Map<Language, String> names, int budgetPlaces, int totalPlaces) {
        this.id = id;
        this.names = names;
        this.budgetPlaces = budgetPlaces;
        this.totalPlaces = totalPlaces;
        this.subjectList = new ArrayList<>();
    }

    public Faculty(Map<Language, String> names,int budgetPlaces, int totalPlaces) {
        this.names = names;
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

    public Map<Language, String> getNames() {return names;}

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

    public void setNames(Map<Language, String> names) {
        this.names = names;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
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
                Objects.equals(names, faculty.names) &&
                Objects.equals(subjectList, faculty.subjectList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, names, budgetPlaces, totalPlaces, subjectList);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", names=" + names +
                ", budgetPlaces=" + budgetPlaces +
                ", totalPlaces=" + totalPlaces +
                ", subjectList=" + subjectList +
                '}';
    }
}