package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Candidate implements Serializable {
    private int id;
    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String fatherName;
    private String secondName;
    private String certificate;
    private City city;
    private String schoolName;
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

    public String getCertificate() {
        return certificate;
    }

    public City getCity() {
        return city;
    }


    public String getSchoolName() {
        return schoolName;
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
                ", certificate'" + certificate + '\'' +
                ", city='" + city + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", isBlocked=" + isBlocked +
                ", applicationsList=" + applicationsList +
                ", applicationDate=" + applicationDate +
                '}';
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role, fatherName, firstName, secondName, certificate,
        city, schoolName, isBlocked, applicationsList, applicationDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != Candidate.class) return false;
        Candidate candidate = (Candidate) obj;
        return Objects.equals(id, candidate.id) &&
                Objects.equals(email, candidate.email) &&
                Objects.equals(password, candidate.password) &&
                Objects.equals(role, candidate.role) &&
                Objects.equals(fatherName, candidate.fatherName) &&
                Objects.equals(firstName, candidate.firstName) &&
                Objects.equals(secondName, candidate.secondName) &&
                Objects.equals(certificate, candidate.certificate) &&
                Objects.equals(city, candidate.city) &&
                Objects.equals(schoolName, candidate.schoolName) &&
                Objects.equals(isBlocked, candidate.isBlocked) &&
                Objects.equals(applicationsList, candidate.applicationsList) &&
                Objects.equals(applicationDate, candidate.applicationDate);
    }

    public static CandidateBuilder builder() {
        return new CandidateBuilder();
    }

    public static CandidateBuilder modify(Candidate candidate) {
        return new CandidateBuilder(candidate);
    }

    /**
     * Builder
     */
    public static class CandidateBuilder {

        private final Candidate candidate;

        private CandidateBuilder() {
            this.candidate = new Candidate();
        }

        private CandidateBuilder(Candidate candidate) {
            this.candidate = candidate;
        }

        public CandidateBuilder id(int id) {
            candidate.id = id;
            return this;
        }

        public CandidateBuilder email(String email) {
            candidate.email = email;
            return this;
        }

        public CandidateBuilder password(String password) {
            candidate.password = password;
            return this;
        }

        public CandidateBuilder role(Role role) {
            candidate.role = role;
            return this;
        }

        public CandidateBuilder firstName(String firstName) {
            candidate.firstName = firstName;
            return this;
        }

        public CandidateBuilder fatherName(String fatherName) {
            candidate.fatherName = fatherName;
            return this;
        }

        public CandidateBuilder secondName(String secondName) {
            candidate.secondName = secondName;
            return this;
        }

        public CandidateBuilder certificate(String certificate) {
            candidate.certificate = certificate;
            return this;
        }

        public CandidateBuilder city(City city) {
            candidate.city = city;
            return this;
        }

        public CandidateBuilder schoolName(String schoolName) {
            candidate.schoolName = schoolName;
            return this;
        }

        public CandidateBuilder isBlocked(boolean isBlocked) {
            candidate.isBlocked = isBlocked;
            return this;
        }

        public CandidateBuilder applicationsList(List<Application> applicationsList) {
            candidate.applicationsList = applicationsList;
            return this;
        }

        public CandidateBuilder applicationDate(LocalDate applicationDate) {
            candidate.applicationDate = applicationDate;
            return this;
        }

        public Candidate build() {
            return this.candidate;
        }
    }

}
