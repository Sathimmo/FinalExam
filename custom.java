package pkgfinal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class custom extends javax.swing.JFrame {

    private JScrollPane jScrollPane1;
    private JTable table;
    private JTextField tph;
    private JTextField tid;
    private JTextField tln;
    private JTextField tfn;
    private JButton jButton2;
    private JButton showButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;

    private Connection conn;
    private PreparedStatement pstmt;

    public custom() {
        initComponents();
        connectToDatabase();
        fetchTableData();
    }

    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        table = new JTable();
        tph = new JTextField();
        tid = new JTextField();
        tln = new JTextField();
        jButton2 = new JButton();
        tfn = new JTextField();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        showButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(736, 672));
        setMinimumSize(new java.awt.Dimension(736, 672));
        getContentPane().setLayout(null);

        table.setModel(new DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID", "Last Name", "First Name", "Phone"
                }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(60, 20, 600, 170);
        getContentPane().add(tph);
        tph.setBounds(80, 500, 240, 40);
        getContentPane().add(tid);
        tid.setBounds(80, 270, 240, 40);
        getContentPane().add(tln);
        tln.setBounds(80, 350, 240, 40);

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(90, 570, 110, 30);
        getContentPane().add(tfn);
        tfn.setBounds(80, 420, 240, 40);

        jLabel1.setText("Phone");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 470, 150, 30);

        jLabel2.setText("Code");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(80, 237, 150, 30);

        jLabel3.setText("Last Name");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(80, 320, 150, 30);

        jLabel4.setText("First Name");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(80, 390, 150, 30);

        showButton.setText("Show Customers");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });
        getContentPane().add(showButton);
        showButton.setBounds(250, 570, 150, 30);

        pack();
    }

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int row = table.getSelectedRow();
        tid.setText(model.getValueAt(row, 0).toString());
        tln.setText(model.getValueAt(row, 1).toString());
        tfn.setText(model.getValueAt(row, 2).toString());
        tph.setText(model.getValueAt(row, 3).toString());
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int code = Integer.parseInt(tid.getText());
            String lname = tln.getText();
            String fname = tfn.getText();
            String phone = tph.getText();

            String sql = "INSERT INTO customers (customer_id, customer_first_name, customer_last_name, customer_phone) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, code);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, phone);
            pstmt.executeUpdate();

            tid.setText("");
            tln.setText("");
            tfn.setText("");
            tph.setText("");

            fetchTableData();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "username";
        String password = "password";

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to database: " + ex.getMessage());
        }
    }

    private void fetchTableData() {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            String sql = "SELECT * FROM customers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int code = rs.getInt("customer_id");
                String lname = rs.getString("customer_last_name");
                String fname = rs.getString("customer_first_name");
                String phone = rs.getString("customer_phone");

                Object[] row = {code, lname, fname, phone};
                model.addRow(row);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from database: " + ex.getMessage());
        }
    }

    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {
        fetchTableData();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new custom().setVisible(true);
            }
        });
    }
}
