import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class SeatAllocation extends JFrame {
    private JButton[][] seats;
    private JPanel seatPanel;
    private JButton allocateButton;
    private ArrayList<String> students;

    private int rows = 5;   // rows
    private int cols = 5;   // cols

    public SeatAllocation() {
        setTitle("Seat Allocation");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        students = fetchStudentsFromDB();

        seatPanel = new JPanel(new GridLayout(rows, cols, 10, 10));
        seats = new JButton[rows][cols];

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                seats[i][j] = new JButton("Empty");
                seats[i][j].setBackground(Color.LIGHT_GRAY);
                seatPanel.add(seats[i][j]);
            }
        }

        add(seatPanel, BorderLayout.CENTER);

        allocateButton = new JButton("Allocate Seats");
        add(allocateButton, BorderLayout.SOUTH);

        allocateButton.addActionListener(e -> allocateSeats());
    }

    private ArrayList<String> fetchStudentsFromDB() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/exam_seating", "root", "root");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM students");

            while(rs.next()) {
                list.add(rs.getString("name"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        return list;
    }

    private void allocateSeats() {
        int index = 0;
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                if(index < students.size()){
                    seats[i][j].setText(students.get(index));
                    seats[i][j].setBackground(Color.CYAN);
                    index++;
                } else {
                    seats[i][j].setText("Empty");
                    seats[i][j].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeatAllocation().setVisible(true));
    }
}
