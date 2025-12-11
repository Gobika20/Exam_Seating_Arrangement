import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ViewStudents extends JFrame {
    JTable table;

    public ViewStudents() {
        setTitle("Student List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table = new JTable();
        add(new JScrollPane(table));

        loadStudents();
        setVisible(true);
    }

    private void loadStudents() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Roll No");
        model.addColumn("Section");
        model.addColumn("Branch");
        model.addColumn("Year");

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/exam_seating", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("roll_no"),
                    rs.getString("section"),
                    rs.getString("branch"),
                    rs.getInt("year")
                });
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setModel(model);
    }

    public static void main(String[] args) {
        new ViewStudents();
    }
}
