package business;

import java.util.Date;

public abstract class Student {
    // fields protected như UML
    protected String id;
    protected String name;
    protected Date birthDate;
    protected String major;

    // constructor protected
    protected Student(String id, String name, Date birthDate, String major) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.major = major;
    }

    // phương thức trừu tượng để con override
    public abstract double calculateGPA();

    // classifyAcademic dựa trên kết quả calculateGPA
    public String classifyAcademic() {
        double gpa = calculateGPA();
        if (gpa >= 8.0) {
            return "Giỏi";
        } else if (gpa >= 6.5) {
            return "Khá";
        } else if (gpa >= 5.0) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }

    // các getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getMajor() {
        return major;
    }
}