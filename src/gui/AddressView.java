/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author SINGER
 */
public class AddressView extends javax.swing.JDialog {

    private String email;
    HashMap<String, Integer> cityMap = new HashMap<>();

    /**
     * Creates new form AddressView
     */
    public AddressView(java.awt.Frame parent, boolean model, String email) {
        super(parent, model);
        initComponents();

        jLabel5.setText(email);
        this.email = email;

        loadAddress();
        loadCities();
    }

    private void loadAddress() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `employee_address` "
                    + "INNER JOIN `city` ON `employee_address`.`city_id`=`city`.`id`"
                    + "WHERE `employee_email`='" + this.email + "'");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("line1"));
                vector.add(resultSet.getString("line2"));
                vector.add(resultSet.getString("city.name"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCities() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `city`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                cityMap.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        jTextField1.setText("");
        jTextField2.setText("");
        jComboBox1.setSelectedIndex(0);
        jTextField1.grabFocus();
        jTable1.setEnabled(true);
        jTable1.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        jLabel1.setText("Employee");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Address Line 01");

        jLabel3.setText("Address Line 02");

        jLabel4.setText("City");

        jLabel5.setText("...");

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Remove");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Line 1", "Line 2", "City"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 100, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String line1 = jTextField1.getText();
        String line2 = jTextField2.getText();
        String city = String.valueOf(jComboBox1.getSelectedItem());

        if (line1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter address line 1", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (line2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter address line 2", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (city == "Select") {
            JOptionPane.showMessageDialog(this, "Please select city", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            boolean isFound = false;

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String getLine1 = String.valueOf(jTable1.getValueAt(i, 1));
                String getLine2 = String.valueOf(jTable1.getValueAt(i, 2));
                String getCity = String.valueOf(jTable1.getValueAt(i, 3));

                if (line1.equals(getLine1) && line2.equals(getLine2) && city.equals(getCity)) {
                    JOptionPane.showMessageDialog(this, "Address already added", "Warning", JOptionPane.WARNING_MESSAGE);
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                try {
                    MySQL.execute("INSERT INTO `employee_address` "
                            + "(`line1`,`line2`,`city_id`,`employee_email`)"
                            + "VALUES ('" + line1 + "','" + line2 + "','" + cityMap.get(city) + "','" + this.email + "')");

                    loadAddress();
                    reset();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        jTable1.setEnabled(false);
        int row = jTable1.getSelectedRow();

        jTextField1.setText(String.valueOf(jTable1.getValueAt(row, 1)));
        jTextField2.setText(String.valueOf(jTable1.getValueAt(row, 2)));
        jComboBox1.setSelectedItem(String.valueOf(jTable1.getValueAt(row, 3)));
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int row = jTable1.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String id = String.valueOf(jTable1.getValueAt(row, 0));

            String line1 = jTextField1.getText();
            String line2 = jTextField2.getText();
            String city = String.valueOf(jComboBox1.getSelectedItem());

            if (line1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter address line 1", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (line2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter address line 2", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (city == "Select") {
                JOptionPane.showMessageDialog(this, "Please select city", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                boolean isFound = false;

                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    String getLine1 = String.valueOf(jTable1.getValueAt(i, 1));
                    String getLine2 = String.valueOf(jTable1.getValueAt(i, 2));
                    String getCity = String.valueOf(jTable1.getValueAt(i, 3));

                    if (line1.equals(getLine1) && line2.equals(getLine2) && city.equals(getCity)) {
                        JOptionPane.showMessageDialog(this, "Address already added", "Warning", JOptionPane.WARNING_MESSAGE);
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    try {
                        MySQL.execute("UPDATE `employee_address` SET"
                                + "`line1`='" + line1 + "',"
                                + "`line2`='" + line2 + "',"
                                + "`city_id`='" + cityMap.get(city) + "'"
                                + "WHERE `id`='" + id + "'");

                        loadAddress();
                        reset();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int row = jTable1.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String id = String.valueOf(jTable1.getValueAt(row, 0));

            try {
                MySQL.execute("DELETE FROM `employee_address` WHERE `id`='" + id + "'");

                loadAddress();
                reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
