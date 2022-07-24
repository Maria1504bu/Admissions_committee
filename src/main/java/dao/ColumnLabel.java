package dao;

public enum ColumnLabel {
    USER_ID("id"),
    USER_EMAIL("email"),
    USER_PASSWORD("password"),
    USER_ROLE_ID("role_id"),

    ROLE_ID("id"),
    ROLE_NAME("name");
    private final String name;
    public final String getName(){
        return name;
    }

    private ColumnLabel(final String name){
        this.name = name;
    }
}
