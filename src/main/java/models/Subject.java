package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Subject implements Serializable {
    private static final long serialVersionUID = 6088279359888273831L;
    private int id;
    private List<String> nameList;
    private int courseDuration;

    public Subject() {
        nameList = new ArrayList<>();
    }

    public Subject(int id) {
        this.id = id;
        nameList = new ArrayList<>();
    }

    public Subject(int courseDuration, String[] localesNames) {
        this.courseDuration = courseDuration;
        nameList = new ArrayList<>();
        listInitialize(localesNames);
    }

    public Subject(int id, String[] localesNames, int courseDuration) {
        this.id = id;
        this.courseDuration = courseDuration;
        nameList = new ArrayList<>();
        listInitialize(localesNames);
    }

    private void listInitialize(String[] localesNames) {
        nameList.addAll(Arrays.asList(localesNames));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id &&
                courseDuration == subject.courseDuration &&
                nameList.equals(subject.nameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameList, courseDuration);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", nameList=" + nameList +
                ", courseDuration=" + courseDuration +
                '}';
    }
}
