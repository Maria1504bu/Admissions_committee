package models;

public class Exam {
    private int id;
    private String name;
    private int mark;

    public Exam(){
    }

    public Exam(int id, String name, int mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }


    @Override
    public String toString() {
        return "Exam [ id = " + id +
                " name = " + name + " ]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
