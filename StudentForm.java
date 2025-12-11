import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentForm extends JFrame {
    JTextField nameField, rollField, sectionField, branchField, yearField;
    JButton addButton;

    public StudentForm() {
        setTitle("Add Student");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Roll No:"));
        rollField = new JTextField();
        add(rollField);

        add(new JLabel("Section:"));
        sectionField = new JTextField();
        add(sectionField);

        add(new JLabel("Branch:"));
        branchField = new JTextField();
        add(branchField);

        add(new JLabel("Year:"));
        yearField = new JTextField();
        add(yearField);

        addButton = new JButton("Add Student");
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudentToDB();
            }
        });

        setVisible(true);
    }

    private void addStudentToDB() {
        try {
           
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/exam_seating", "root", "root");
            String query = "INSERT INTO students(name, roll_no, section, branch, year) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nameField.getText());
            ps.setString(2, rollField.getText());
            ps.setString(3, sectionField.getText());
            ps.setString(4, branchField.getText());
            ps.setInt(5, Integer.parseInt(yearField.getText()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Student added successfully!");
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, " Error adding student.");
        }
    }

    public static void main(String[] args) {
        new StudentForm();
    }
}
