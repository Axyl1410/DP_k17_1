package presentation;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.Student;

public class StudentListViewUI extends JFrame {
    private final JTextField searchTextField;
    private final JButton addButton;
    private final JButton reloadButton;
    private final JTable studentTable;
    private final DefaultTableModel tableModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public StudentListViewUI() {
        super("Danh sách sinh viên");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 400);
        setLocationRelativeTo(null);
        // Panel top
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        searchTextField = new JTextField();
        addButton = new JButton("Thêm");
        reloadButton = new JButton("Load lại");
        JPanel buttonPanel = new JPanel(new BorderLayout(5, 5));
        buttonPanel.add(addButton, BorderLayout.WEST);
        buttonPanel.add(reloadButton, BorderLayout.EAST);
        topPanel.add(searchTextField, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        addButton.addActionListener(e -> {
            StudentFormUI form = new StudentFormUI();
            form.setVisible(true);
        });
        // Table
        String[] columns = { "STT", "ID", "Họ & tên", "Ngày sinh", "Chuyên ngành", "Điểm TB", "Xếp loại" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);
    }

    public void showStudentList(List<Student> studentList) {
        tableModel.setRowCount(0);
        int orderNumber = 1;
        for (Student student : studentList) {
            Object[] row = {
                    orderNumber++,
                    student.getId(),
                    student.getName(),
                    dateFormat.format(student.getBirthDate()),
                    student.getMajor(),
                    String.format("%.2f", student.calculateGPA()),
                    student.classifyAcademic()
            };
            tableModel.addRow(row);
        }
    }

    public JButton getReloadButton() {
        return reloadButton;
    }
}