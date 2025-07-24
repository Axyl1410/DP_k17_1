package presentation;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.AddStudentUseCase;
import business.EconomicsStudent;
import business.SoftwareStudent;
import business.Student;
import persistence.AddStudentDAO;

public class StudentFormUI extends JFrame {
    private JTextField idField, nameField, birthDateField, javaField, htmlField, cssField, marketingField, salesField;
    private JComboBox<String> majorBox;

    public StudentFormUI() {
        super("Thêm sinh viên mới");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Họ & tên:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Ngày sinh (dd/MM/yyyy):"));
        birthDateField = new JTextField();
        panel.add(birthDateField);

        panel.add(new JLabel("Chuyên ngành:"));
        majorBox = new JComboBox<>(new String[] { "Software", "Economics" });
        panel.add(majorBox);

        panel.add(new JLabel("Java Score:"));
        javaField = new JTextField();
        panel.add(javaField);

        panel.add(new JLabel("HTML Score:"));
        htmlField = new JTextField();
        panel.add(htmlField);

        panel.add(new JLabel("CSS Score:"));
        cssField = new JTextField();
        panel.add(cssField);

        panel.add(new JLabel("Marketing Score:"));
        marketingField = new JTextField();
        panel.add(marketingField);

        panel.add(new JLabel("Sales Score:"));
        salesField = new JTextField();
        panel.add(salesField);

        JButton saveButton = new JButton("Lưu");
        panel.add(saveButton);

        JButton cancelButton = new JButton("Hủy");
        panel.add(cancelButton);
        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            try {
                String id = idField.getText();
                String name = nameField.getText();
                Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDateField.getText());
                String major = (String) majorBox.getSelectedItem();
                Student student;
                if ("Software".equalsIgnoreCase(major)) {
                    double javaScore = Double.parseDouble(javaField.getText());
                    double htmlScore = Double.parseDouble(htmlField.getText());
                    double cssScore = Double.parseDouble(cssField.getText());
                    student = new SoftwareStudent(id, name, birthDate, javaScore, htmlScore, cssScore);
                } else {
                    double marketingScore = Double.parseDouble(marketingField.getText());
                    double salesScore = Double.parseDouble(salesField.getText());
                    student = new EconomicsStudent(id, name, birthDate, marketingScore, salesScore);
                }
                AddStudentDAO dao = new AddStudentDAO();
                AddStudentUseCase useCase = new AddStudentUseCase(dao);
                useCase.execute(student);
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                // Reset all form fields
                idField.setText("");
                nameField.setText("");
                birthDateField.setText("");
                majorBox.setSelectedIndex(0);
                javaField.setText("");
                htmlField.setText("");
                cssField.setText("");
                marketingField.setText("");
                salesField.setText("");
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        add(panel);
    }
}