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
    public Faculty(int id, String[] names, int budgetPlacesQty, int totalPlacesQty){
        this.id = id;
        this.namesList = new ArrayList<>();
        this.budgetPlaces = budgetPlacesQty;
        this.totalPlaces = totalPlacesQty;
        this.subjectList = new ArrayList<>();
        listInitialize(names);
    }
    public Faculty(String[] names, int budgetPlacesQty, int totalPlacesQty){
        this.namesList = new ArrayList<>();
        this.budgetPlaces = budgetPlacesQty;
        this.totalPlaces = totalPlacesQty;
        this.subjectList = new ArrayList<>();
        listInitialize(names);
    }
    private void listInitialize(String[] localesNames) {
        namesList.addAll(Arrays.asList(localesNames));
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

    public static Comparator<Faculty> COMPARE_BY_NAME = (one, other) -> one.getNamesList().get(0).compareTo(other.getNamesList().get(0));

    public static Comparator<Faculty> COMPARE_BY_BUDGET_PLACES = (one, other) -> one.getBudgetPlaces() - other.getBudgetPlaces();

    public static Comparator<Faculty> COMPARE_BY_TOTAL_PLACES = (one, other) -> one.getTotalPlaces() - other.getTotalPlaces();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id &&
                budgetPlaces == faculty.budgetPlaces &&
                totalPlaces == faculty.totalPlaces &&
                namesList.equals(faculty.namesList) &&
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
                ", budgetPlacesQty=" + budgetPlaces +
                ", totalPlacesQty=" + totalPlaces +
                ", subjectList=" + subjectList +
                '}';
    }
}