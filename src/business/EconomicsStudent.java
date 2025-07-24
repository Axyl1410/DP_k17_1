
package business;

import java.util.Date;

public class EconomicsStudent extends Student {
    private double marketingScore;
    private double salesScore;

    public EconomicsStudent(String id, String name, Date birthDate,
            double marketingScore, double salesScore) {
        super(id, name, birthDate, "Economics");
        this.marketingScore = marketingScore;
        this.salesScore = salesScore;
    }

    @Override
    public double calculateGPA() {
        // ví dụ tính trung bình 2 môn
        return (marketingScore * 2 + salesScore) / 3.0;
    }

    public double getMarketingScore() {
        return marketingScore;
    }

    public double getSalesScore() {
        return salesScore;
    }
}
