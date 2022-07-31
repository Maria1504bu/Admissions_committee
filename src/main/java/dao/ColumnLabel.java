package dao;

public enum ColumnLabel {
    USER_ID("id"),
    USER_EMAIL("email"),
    USER_PASSWORD("password"),
    USER_ROLE_ID("role_id"),

    ROLE_ID("id"),
    ROLE_NAME("name"),

    FACULTY_ID("id"),
    FACULTY_NAME("name"),
    FACULTY_BUDGET_PLACES("budget_places"),
    FACULTY_ALL_PLACES("all_places"),

    EXAM_ID("id"),
    EXAM_NAME("name"),

    CANDIDATES_EXAM_MARK("mark");
    private final String name;
    public final String getName(){
        return name;
    }

    private ColumnLabel(final String name){
        this.name = name;
    }
}
