/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegistrationForm extends JFrame {
    JTextField tfName, tfMobile, tfDOB;
    JRadioButton male, female;
    JTextArea taAddress;
    JCheckBox terms;
    JButton submit, reset;
    JTable table;
    DefaultTableModel model;

    Connection con;
    Statement stmt;
    ResultSet rs;

    public RegistrationForm() {
        setTitle("Registration Form");
        setLayout(null);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 30, 100, 30);
        add(lblName);

        tfName = new JTextField();
        tfName.setBounds(150, 30, 150, 30);
        add(tfName);

        JLabel lblMobile = new JLabel("Mobile:");
        lblMobile.setBounds(30, 80, 100, 30);
        add(lblMobile);

        tfMobile = new JTextField();
        tfMobile.setBounds(150, 80, 150, 30);
        add(tfMobile);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(30, 130, 100, 30);
        add(lblGender);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        male.setBounds(150, 130, 60, 30);
        female.setBounds(220, 130, 80, 30);
        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);
        add(male);
        add(female);

        JLabel lblDOB = new JLabel("DOB (dd/mm/yyyy):");
        lblDOB.setBounds(30, 180, 150, 30);
        add(lblDOB);

        tfDOB = new JTextField();
        tfDOB.setBounds(180, 180, 120, 30);
        add(tfDOB);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(30, 230, 100, 30);
        add(lblAddress);

        taAddress = new JTextArea();
        taAddress.setBounds(150, 230, 150, 60);
        add(taAddress);

        terms = new JCheckBox("Accept Terms And Conditions");
        terms.setBounds(30, 310, 250, 30);
        add(terms);

        submit = new JButton("Submit");
        submit.setBounds(30, 350, 100, 30);
        add(submit);

        reset = new JButton("Reset");
        reset.setBounds(150, 350, 100, 30);
        add(reset);

        // Table to display data
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Mobile");
        model.addColumn("Gender");
        model.addColumn("DOB");
        model.addColumn("Address");

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(350, 30, 400, 350);
        add(sp);

        connectDatabase();
        loadData();

        submit.addActionListener(e -> submitForm());
        reset.addActionListener(e -> resetForm());

        setVisible(true);
    }

    private void connectDatabase() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registrationdb", "netbeansuser", "password123"); // replace password if needed
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            model.setRowCount(0); // clear previous rows
            rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("mobile"),
                        rs.getString("gender"),
                        rs.getString("dob"),
                        rs.getString("address")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitForm() {
        if (!terms.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please accept the terms and conditions.");
            return;
        }
        String name = tfName.getText();
        String mobile = tfMobile.getText();
        String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "";
        String dob = tfDOB.getText();
        String address = taAddress.getText();

        try {
            String query = "INSERT INTO users (name, mobile, gender, dob, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, mobile);
            ps.setString(3, gender);
            ps.setString(4, dob);
            ps.setString(5, address);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data submitted successfully!");
            loadData();
            resetForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetForm() {
        tfName.setText("");
        tfMobile.setText("");
        tfDOB.setText("");
        taAddress.setText("");
        male.setSelected(false);
        female.setSelected(false);
        terms.setSelected(false);
    }

    public static void main(String[] args) {
        new RegistrationForm();
    }
}

/**
 *
 * @author sam
 */
