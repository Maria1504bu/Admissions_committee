package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Candidate implements Serializable {
    private int id;
    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String fatherName;
    private String secondName;
    private String certificate_url;
    private City city;
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

    public String getCertificate_url() {
        return certificate_url;
    }

    public City getCity() {
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

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", certificate_url'" + certificate_url + '\'' +
                ", city='" + city + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", schoolCertificate=" + Arrays.toString(schoolCertificate) +
                ", isBlocked=" + isBlocked +
                ", applicationsList=" + applicationsList +
                ", applicationDate=" + applicationDate +
                '}';
    }

    public static CandidateBuilder builder(){
        return new CandidateBuilder();
    }

    public static CandidateBuilder modify(Candidate candidate){
        return new CandidateBuilder(candidate);
    }

    /**
     * Builder
     */
    public static  class CandidateBuilder{

        private Candidate candidate;

        private CandidateBuilder(){
            this.candidate = new Candidate();
        }

        private CandidateBuilder(Candidate candidate){
            this.candidate = candidate;
        }

        public CandidateBuilder id(int id){
            candidate.id = id;
            return this;
        }

        public CandidateBuilder email(String email){
            candidate.email = email;
            return this;
        }

        public CandidateBuilder password(String password){
            candidate.password = password;
            return this;
        }

        public CandidateBuilder role(Role role){
            candidate.role = role;
            return this;
        }

        public CandidateBuilder firstName(String firstName){
            candidate.firstName = firstName;
            return this;
        }

        public CandidateBuilder fatherName(String fatherName){
            candidate.fatherName = fatherName;
            return this;
        }

        public CandidateBuilder secondName(String secondName){
            candidate.secondName = secondName;
            return this;
        }

        public CandidateBuilder certificate_url(String certificate_url){
            candidate.certificate_url = certificate_url;
            return this;
        }

        public CandidateBuilder city(City city){
            candidate.city = city;
            return this;
        }

        public CandidateBuilder schoolName(String schoolName){
            candidate.schoolName = schoolName;
            return this;
        }
        public CandidateBuilder schoolCertificate(byte[] schoolCertificate){
            candidate.schoolCertificate = schoolCertificate;
            return this;
        }
        public CandidateBuilder isBlocked(boolean isBlocked){
            candidate.isBlocked = isBlocked;
            return this;
        }
        public CandidateBuilder applicationsList(List<Application> applicationsList){
            candidate.applicationsList = applicationsList;
            return this;
        }
        public CandidateBuilder applicationDate(LocalDate applicationDate){
            candidate.applicationDate = applicationDate;
            return this;
        }
        public Candidate build(){
            return this.candidate;
        }
    }
}
