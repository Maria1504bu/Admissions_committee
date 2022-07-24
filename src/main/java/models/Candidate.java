package models;

public class Candidate extends User{
    public Candidate(int id, String email, String password, Role role) {
        super(id, email, password, role);
    }
}
