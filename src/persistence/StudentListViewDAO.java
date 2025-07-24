package persistence;

import business.EconomicsStudent;
import business.SoftwareStudent;
import business.Student;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentListViewDAO {
    private final Connection conn;

    public StudentListViewDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/student?useSSL=false&serverTimezone=UTC";
        conn = DriverManager.getConnection(url, username, password);
        System.out.println("Connected!!!");
    }

    public List<Student> getAllStudents() throws SQLException, ParseException {
        List<Student> studentList = new ArrayList<Student>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT id, name, birthday, major, javaScore," +
                "htmlScore, cssScore, marketingScore, salesScore " +
                "FROM student";
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            Date birthDate = dateFormat.parse(resultSet.getString("birthday"));
            String major = resultSet.getString("major");
            Student student = null;
            if ("Software".equalsIgnoreCase(major)) {
                double javaScore = resultSet.getDouble("javaScore");
                double htmlScore = resultSet.getDouble("htmlScore");
                double cssScore = resultSet.getDouble("cssScore");
                student = new SoftwareStudent(id, name, birthDate, javaScore, htmlScore, cssScore);
            } else if ("Economics".equalsIgnoreCase(major)) {
                double marketingScore = resultSet.getDouble("marketingScore");
                double salesScore = resultSet.getDouble("salesScore");
                student = new EconomicsStudent(id, name, birthDate, marketingScore, salesScore);
            }
            if (student != null) {
                studentList.add(student);
            }
        }
        conn.close();
        statement.close();
        resultSet.close();
        return studentList;
    }

}
