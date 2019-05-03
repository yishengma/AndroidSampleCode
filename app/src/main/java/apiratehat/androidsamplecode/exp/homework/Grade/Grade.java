package apiratehat.androidsamplecode.exp.homework.Grade;

public class Grade {
    private int id;
    private String classes;
    private String name;
    private String grade;

    public Grade(String classes, String name, String grade) {
        this.classes = classes;
        this.name = name;
        this.grade = grade;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
