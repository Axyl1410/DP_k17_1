package business;

import java.sql.SQLException;

import persistence.AddStudentDAO;

public class AddStudentUseCase {
    private final AddStudentDAO addStudentDAO;

    public AddStudentUseCase(AddStudentDAO addStudentDAO) {
        super();
        this.addStudentDAO = addStudentDAO;
    }

    public void execute(Student student) throws SQLException {
        addStudentDAO.addStudent(student);
    }
}
