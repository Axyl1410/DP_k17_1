package business;

import persistence.StudentListViewDAO;
import presentation.StudentListViewUI;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class StudentListViewUseCase {
    private final StudentListViewDAO listViewDAO;
    private final StudentListViewUI listViewUI;

    public StudentListViewUseCase(StudentListViewDAO listViewDAO,
                                  StudentListViewUI listViewUI) {
        super();
        this.listViewDAO = listViewDAO;
        this.listViewUI = listViewUI;
    }

    public void execute() throws SQLException, ParseException {
        List<Student> list = null;
        list = listViewDAO.getAllStudents();
        listViewUI.showStudentList(list);
    }

}
