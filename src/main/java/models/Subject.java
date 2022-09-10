package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Subject implements Serializable {
    private int id;
    private Map<Language, String> names;
    private int maxGrade;

    public Subject() {
        this.names = new HashMap<>();
    }

    public Subject(int id) {
        this.id = id;
        this.names = new HashMap<>();
    }

    public Subject(int courseDuration, Map<Language, String> names) {
        this.maxGrade = courseDuration;
        this.names = names;
    }

    public Subject(int id, Map<Language, String> names, int maxGrade) {
        this.id = id;
        this.maxGrade = maxGrade;
        this.names = names;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Language, String> getNames() {
        return names;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrage(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id &&
                maxGrade == subject.maxGrade &&
                Objects.equals(names, subject.names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, names, maxGrade);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", names=" + names +
                ", maxGrade=" + maxGrade +
                '}';
    }
}
