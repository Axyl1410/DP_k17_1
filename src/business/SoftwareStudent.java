
package business;

import java.util.Date;

public class SoftwareStudent extends Student {
    private double javaScore;
    private double htmlScore;
    private double cssScore;

    public SoftwareStudent(String id, String name, Date birthDate,
            double javaScore, double htmlScore, double cssScore) {
        super(id, name, birthDate, "Software");
        this.javaScore = javaScore;
        this.htmlScore = htmlScore;
        this.cssScore = cssScore;
    }

    @Override
    public double calculateGPA() {
        return (javaScore * 2 + htmlScore + cssScore) / 4.0;
    }

    // getters nếu cần
    public double getJavaScore() {
        return javaScore;
    }

    public double getHtmlScore() {
        return htmlScore;
    }

    public double getCssScore() {
        return cssScore;
    }
}