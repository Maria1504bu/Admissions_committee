package dao;

public enum ColumnLabel {
    USER_ID("id"),
    USER_EMAIL("email"),
    USER_PASSWORD("password"),
    USER_ROLE_ID("role_id"),

    CANDIDATE_LOGIN_ID("login_id"),
    CANDIDATE_FIRSTNAME("first_name"),
    CANDIDATE_FATHER_NAME("father_name"),
    CANDIDATE_SECOND_NAME("second_name"),
    CANDIDATE_CERTIFICATE("certificate"),
    CANDIDATE_CITY_ID("city_id"),
    CANDIDATE_SCHOOL("school_name"),
    CANDIDATE_IS_BLOCKED("is_blocked"),
    CANDIDATE_APPL_DATE("appl_date"),

    CITY_ID("id"),
    CITY_NAME("name"),
    ROLE_ID("id"),
    ROLE_NAME("name"),
    LANG_CODE("lang_code"),

    FACULTY_ID("id"),
    FACULTY_NAME("name"),
    FACULTY_BUDGET_PLACES("budget_places"),
    FACULTY_TOTAL_PLACES("total_places"),

    SUBJECT_ID("id"),
    SUBJECT_MAX_GRADE("maxGrade"),
    SUBJECT_NAME("name"),
    SUBJECT_COEFFICIENT("subject_coefficient"),
    APPL_ID("id"),
    APPL_LOGIN_ID("login_id"),
    APPL_FACULTY_ID("faculty_id"),
    APPL_RATING_SCORE("rating_score"),
    APPL_STATUS("status"),

    GRADE_CANDIDATE_ID("candidate_id"),
    GRADE_SUBJ_ID("subject_id"),
    GRADE_VALUE("grade");
    private final String name;
    public final String getName(){
        return name;
    }

    private ColumnLabel(final String name){
        this.name = name;
    }
}
