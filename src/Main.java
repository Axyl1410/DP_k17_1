import java.sql.SQLException;
import java.text.ParseException;

import business.StudentListViewUseCase;
import persistence.StudentListViewDAO;
import presentation.StudentListViewUI;

public class Main {
    public static void main(String[] args) {
        StudentListViewUI listViewUI = new StudentListViewUI();
        try {
            StudentListViewDAO listViewDAO = new StudentListViewDAO();
            final StudentListViewUseCase listViewUseCase = new StudentListViewUseCase(listViewDAO, listViewUI);
            listViewUseCase.execute();
            listViewUI.getReloadButton().addActionListener(e -> {
                try {
                    listViewUseCase.execute();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            listViewUI.setVisible(true);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}