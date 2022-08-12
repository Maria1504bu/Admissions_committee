package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Candidate implements Serializable {
    private int id;
    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String fatherName;
    private String secondName;
    private String city;
    private String schoolName;
    private byte[] schoolCertificate;
    private boolean isBlocked;
    private List<Application> applicationsList;
    private LocalDate applicationDate;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getCity() {
        return city;
    }


    public String getSchoolName() {
        return schoolName;
    }

    public byte[] getSchoolCertificate() {
        return schoolCertificate;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public List<Application> getApplicationsList() {
        return applicationsList;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    private Candidate(CandidateBuilder builder){
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.firstName = builder.firstName;
        this.fatherName = builder.fatherName;
        this.secondName = builder.secondName;
        this.city = builder.city;
        this.schoolName = builder.schoolName;
        this.schoolCertificate = builder.schoolCertificate;
        this.isBlocked = builder.isBlocked;
        this.applicationsList = builder.applicationsList;
        this.applicationDate = builder.applicationDate;
    }

    public static  class CandidateBuilder{
        private int id;
        private String email;
        private String password;
        private Role role;
        private String firstName;
        private String fatherName;
        private String secondName;
        private String city;
        private String schoolName;
        private byte[] schoolCertificate;
        private boolean isBlocked;
        private List<Application> applicationsList;
        private LocalDate applicationDate;

        public CandidateBuilder id(int id){
            this.id = id;
            return this;
        }

        public CandidateBuilder email(String email){
            this.email = email;
            return this;
        }

        public CandidateBuilder password(String password){
            this.password = password;
            return this;
        }

        public CandidateBuilder role(Role role){
            this.role = role;
            return this;
        }

        public CandidateBuilder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public CandidateBuilder fatherName(String fatherName){
            this.fatherName = fatherName;
            return this;
        }

        public CandidateBuilder secondName(String secondName){
            this.secondName = secondName;
            return this;
        }

        public CandidateBuilder city(String city){
            this.city = city;
            return this;
        }

        public CandidateBuilder schoolName(String schoolName){
            this.schoolName = schoolName;
            return this;
        }
        public CandidateBuilder schoolCertificate(byte[] schoolCertificate){
            this.schoolCertificate = schoolCertificate;
            return this;
        }
        public CandidateBuilder isBlocked(boolean isBlocked){
            this.isBlocked = isBlocked;
            return this;
        }
        public CandidateBuilder applicationsList(List<Application> applicationsList){
            this.applicationsList = applicationsList;
            return this;
        }
        public CandidateBuilder applicationDate(LocalDate applicationDate){
            this.applicationDate = applicationDate;
            return this;
        }
        public Candidate build(){
            return new Candidate(this);
        }
    }
}
