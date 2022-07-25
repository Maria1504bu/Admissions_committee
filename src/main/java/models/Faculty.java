package models;

public class Faculty {
    private int id;
    private String name;
    private int budget_places;
    private int all_places;

    public Faculty() {
    }

    public Faculty(int id, String name, int budget_places, int all_places) {
        this.id = id;
        this.name = name;
        this.budget_places = budget_places;
        this.all_places = all_places;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBudget_places() {
        return budget_places;
    }

    public void setBudget_places(int budget_places) {
        this.budget_places = budget_places;
    }

    public int getAll_places() {
        return all_places;
    }

    public void setAll_places(int all_places) {
        this.all_places = all_places;
    }
}
