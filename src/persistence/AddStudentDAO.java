package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import business.Student;

public class AddStudentDAO {
    private final Connection conn;

    public AddStudentDAO() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/student?useSSL=false&serverTimezone=UTC";
        conn = DriverManager.getConnection(url, username, password);
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (id, name, birthday, major, javaScore, htmlScore, cssScore, marketingScore, salesScore) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setDate(3, new java.sql.Date(student.getBirthDate().getTime()));
            ps.setString(4, student.getMajor());

            if (student.getMajor().equalsIgnoreCase("Software")) {
                ps.setDouble(5, ((business.SoftwareStudent) student).getJavaScore());
                ps.setDouble(6, ((business.SoftwareStudent) student).getHtmlScore());
                ps.setDouble(7, ((business.SoftwareStudent) student).getCssScore());
                ps.setNull(8, java.sql.Types.DOUBLE);
                ps.setNull(9, java.sql.Types.DOUBLE);
            } else if (student.getMajor().equalsIgnoreCase("Economics")) {
                ps.setNull(5, java.sql.Types.DOUBLE);
                ps.setNull(6, java.sql.Types.DOUBLE);
                ps.setNull(7, java.sql.Types.DOUBLE);
                ps.setDouble(8, ((business.EconomicsStudent) student).getMarketingScore());
                ps.setDouble(9, ((business.EconomicsStudent) student).getSalesScore());
            } else {
                ps.setNull(5, java.sql.Types.DOUBLE);
                ps.setNull(6, java.sql.Types.DOUBLE);
                ps.setNull(7, java.sql.Types.DOUBLE);
                ps.setNull(8, java.sql.Types.DOUBLE);
                ps.setNull(9, java.sql.Types.DOUBLE);
            }
            ps.executeUpdate();
        }
    }
}
