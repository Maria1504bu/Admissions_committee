package models;

public class Faculty {
    private int id;
    private String name;
    private int budget_places;
    private int all_places;

    public Faculty(int id, String name, int budget_places, int all_places) {
        this.id = id;
        this.name = name;
        this.budget_places = budget_places;
        this.all_places = all_places;
    }
}
