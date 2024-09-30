

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class User extends Frame implements ActionListener {
    Label lblTitle, lblID, lblName, lblAge, lblCity, lblOutput;
    TextField txtID, txtName, txtAge, txtCity;
    Button btnSave, btnClear, btnDelete, btnFetch;

    Connection con = null;
    String query = "";
    PreparedStatement pst = null;
    Statement stmt;
    ResultSet rs;

    public void connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/crud";
            String userName = "root";
            String pas = "";
            con = DriverManager.getConnection(url, userName, pas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void clear() {
        txtID.setText("");
        txtName.setText("");
        txtCity.setText("");
        txtAge.setText("");
        txtName.requestFocus();
    }

    public User() {
        connect();

        setTitle("User Management System");
        setLayout(null);
        setSize(900, 600);
        setVisible(true);
        Color back = new Color(53, 59, 72);
        setBackground(back);

        Font titleFont = new Font("arial", Font.BOLD, 26);
        Font labelFont = new Font("arial", Font.PLAIN, 18);

        lblTitle = new Label("User Management System");
        lblTitle.setBounds(200, 100, 400, 30);
        lblTitle.setForeground(Color.YELLOW);
        lblTitle.setFont(titleFont);
        add(lblTitle);

        lblID = new Label("ID");
        lblID.setForeground(Color.WHITE);
        lblID.setFont(labelFont);
        lblID.setBounds(200, 150, 50, 20);
        add(lblID);

        txtID = new TextField();
        txtID.setBounds(350, 150, 350, 20);
        add(txtID);

        lblName = new Label("Name");
        lblName.setForeground(Color.WHITE);
        lblName.setFont(labelFont);
        lblName.setBounds(200, 200, 50, 20);
        add(lblName);

        txtName = new TextField();
        txtName.setBounds(350, 200, 350, 20);
        add(txtName);

        lblAge = new Label("Age");
        lblAge.setForeground(Color.WHITE);
        lblAge.setFont(labelFont);
        lblAge.setBounds(200, 250, 50, 20);
        add(lblAge);

        txtAge = new TextField();
        txtAge.setBounds(350, 250, 350, 20);
        add(txtAge);

        lblCity = new Label("City");
        lblCity.setForeground(Color.WHITE);
        lblCity.setFont(labelFont);
        lblCity.setBounds(200, 300, 50, 20);
        add(lblCity);

        txtCity = new TextField();
        txtCity.setBounds(350, 300, 350, 20);
        add(txtCity);

        btnSave = new Button("Save");
        btnSave.setBounds(350, 350, 100, 20);
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(Color.BLUE);
        btnSave.addActionListener(this);
        add(btnSave);

        btnClear = new Button("Clear");
        btnClear.setBounds(470, 350, 100, 20);
        btnClear.setForeground(Color.WHITE);
        Color yel = new Color(0xDACD0F);
        btnClear.setBackground(yel);
        btnClear.addActionListener(this);
        add(btnClear);

        btnDelete = new Button("Delete");
        btnDelete.setBounds(590, 350, 100, 20);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setBackground(Color.RED);
        btnDelete.addActionListener(this);
        add(btnDelete);


        btnFetch = new Button("Fetch");
        btnFetch.setBounds(350, 380, 100, 20);
        btnFetch.setForeground(Color.WHITE);
        btnFetch.setBackground(Color.GREEN);
        btnFetch.addActionListener(this);
        add(btnFetch);

        lblOutput = new Label("");
        lblOutput.setFont(labelFont);
        lblOutput.setForeground(Color.WHITE);
        lblOutput.setBounds(350, 410, 250, 20);
        add(lblOutput);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String id = txtID.getText();
            String name = txtName.getText();
            String age = txtAge.getText();
            String city = txtCity.getText();

            if (e.getSource().equals(btnFetch)) {
                // Get user by ID when "Fetch" is clicked
                query = "select ID, Name, Age, City from User where ID = ?";
                pst = con.prepareStatement(query);
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    txtID.setText(rs.getString("ID"));
                    txtName.setText(rs.getString("Name"));
                    txtAge.setText(rs.getString("Age"));
                    txtCity.setText(rs.getString("City"));
                    lblOutput.setText("User Found !  !");
                } else {
                    clear();
                    lblOutput.setText("Invalid ID");
                }
            }

            if (e.getSource().equals(btnClear)) {
                clear();
            }
            else if (e.getSource().equals(btnSave)) {
                if (id.isEmpty()) {
                    query = "insert into User (Name, Age, City) values(?,?,?)";
                    pst = con.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setString(2, age);
                    pst.setString(3, city);
                    pst.executeUpdate();
                    clear();
                    lblOutput.setText("Data Insert Success");
                } else {
                    query = "update User set Name=?, Age=?, City=? where ID=?";
                    pst = con.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setString(2, age);
                    pst.setString(3, city);
                    pst.setString(4, id);
                    pst.executeUpdate();
                    clear();
                    lblOutput.setText("Data Update Success");
                }
            } else if (e.getSource().equals(btnDelete)) {
                query = "delete from User where ID=?";
                pst = con.prepareStatement(query);
                pst.setString(1, id);
                pst.executeUpdate();
                clear();
                lblOutput.setText("Details Deleted Successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new User();
    }
}
