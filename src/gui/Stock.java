/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Stock extends javax.swing.JFrame {

    HashMap<String, String> brandMap = new HashMap<>();

    private Grn grn;
    private Invoice invoice;

    public void setGrn(Grn grn) {
        this.grn = grn;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /**
     * Creates new form Stock
     */
    public Stock() {
        initComponents();
        loadBrand();
        loadProducts();
        loadStock();
    }

    private void loadStock() {
        try {

            String query = "SELECT * FROM `stock` "
                    + "INNER JOIN `product` ON `stock`.`product_id`=`product`.`id`"
                    + "INNER JOIN `brand` ON `product`.`brand_id`=`brand`.`id` ";

            int row = jTable2.getSelectedRow();
            if (row != -1) {
                String pid = String.valueOf(jTable2.getValueAt(row, 0));
                query += "WHERE `stock`.`product_id`='" + pid + "' ";
            }

            if (query.contains("WHERE")) {
                query += "AND ";
            } else {
                query += "WHERE ";
            }

            double min_price = 0;
            double max_price = 0;

            if (!jFormattedTextField2.getText().isEmpty()) {
                min_price = Double.parseDouble(jFormattedTextField2.getText());
            }
            if (!jFormattedTextField3.getText().isEmpty()) {
                max_price = Double.parseDouble(jFormattedTextField3.getText());
            }

            if (min_price > 0 && max_price == 0) {
                query += "`stock`.`selling_price` > '" + min_price + "' ";
            } else if (min_price == 0 && max_price > 0) {
                query += "`stock`.`selling_price` < '" + max_price + "' ";
            } else if (min_price > 0 && max_price > 0) {
                query += "`stock`.`selling_price` > '" + min_price + "' AND `stock`.`selling_price` < '" + max_price + "' ";
            }

//            exp
            Date start = null;
            Date end = null;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            if (jDateChooser1.getDate() != null) {
                start = jDateChooser1.getDate();
                query += "`stock`.`exp` > '" + format.format(start) + "' AND ";
            }
            if (jDateChooser2.getDate() != null) {
                end = jDateChooser2.getDate();
                query += "`stock`.`exp` < '" + format.format(end) + "' ";
            }

            String sort = String.valueOf(jComboBox2.getSelectedItem());
            query += "ORDER BY ";

            query = query.replace("WHERE ORDER BY ", "ORDER BY ");
            query = query.replace("AND ORDER BY ", "ORDER BY ");

            if (sort.equals("Stock Id ASC")) {
                query += "`stock`.`id` ASC";
            } else if (sort.equals("Stock Id DESC")) {
                query += "`stock`.`id` DESC";
            } else if (sort.equals("Brand ASC")) {
                query += "`brand`.`name` ASC";
            } else if (sort.equals("Brand DESC")) {
                query += "`brand`.`name` DESC";
            } else if (sort.equals("Name ASC")) {
                query += "`product`.`name` ASC";
            } else if (sort.equals("Name DESC")) {
                query += "`product`.`name` DESC";
            } else if (sort.equals("Selling Price ASC")) {
                query += "`stock`.`selling_price` ASC";
            } else if (sort.equals("Selling Price DESC")) {
                query += "`stock`.`selling_price` DESC";
            } else if (sort.equals("Qty ASC")) {
                query += "`stock`.`qty` ASC";
            } else if (sort.equals("Qty DESC")) {
                query += "`stock`.`qty` DESC";
            }

            ResultSet resultSet = MySQL.execute(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("stock.id"));
                vector.add(resultSet.getString("product.id"));
                vector.add(resultSet.getString("brand.name"));
                vector.add(resultSet.getString("product.name"));
                vector.add(resultSet.getString("selling_price"));
                vector.add(resultSet.getString("qty"));
                vector.add(resultSet.getString("mfg"));
                vector.add(resultSet.getString("exp"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProducts() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `product` "
                    + "INNER JOIN `brand` ON `product`.`brand_id`=`brand`.`id`");

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("product.id"));
                vector.add(resultSet.getString("product.name"));
                vector.add(resultSet.getString("brand.id"));
                vector.add(resultSet.getString("brand.name"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBrand() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `brand`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                brandMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }
            jComboBox1.setModel(new DefaultComboBoxModel<>(vector));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetProductUi() {
        jTable2.clearSelection();
        jTextField1.setText("");
        jTextField1.setEditable(true);
        jComboBox1.setSelectedIndex(0);
        jTextField2.setText("");
        jTextField3.setText("");
    }

    private void resetStockUi() {
        jComboBox2.setSelectedIndex(0);
        jFormattedTextField2.setText("0.00");
        jFormattedTextField3.setText("0.00");
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Product Id", "Brand", "Name", "Selling Price", "Qty", "MFD", "EXP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Id", "Product Name", "Brand Id", "Brand"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel1.setText("Product Id");

        jLabel2.setText("Brand");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Product Name");

        jButton1.setText("Add New Product");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Update Product");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("+");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton7.setText("...");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel4.setText("Sort By");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stock Id ASC", "Stock Id DESC", "Brand ASC", "Brand DESC", "Name ASC", "Name DESC", "Selling Price ASC", "Selling Price DESC", "Qty ASC", "Qty DESC" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel5.setText("Selling Price");

        jLabel6.setText("To");

        jLabel7.setText("EXP");

        jDateChooser1.setDateFormatString("yyyy-MM-dd");

        jLabel8.setText("To");

        jDateChooser2.setDateFormatString("yyyy-MM-dd");

        jButton4.setText("Find");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("...");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Find");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.setText("0.00");

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField3.setText("0.00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addGap(54, 54, 54))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(jButton5))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jButton6)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String brand = jTextField3.getText();

        if (brand.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter brand name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultSet = MySQL.execute("SELECT * FROM `brand` WHERE `name`='" + brand + "'");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Brand already exsits", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (jComboBox1.getSelectedIndex() == 0) {
                        MySQL.execute("INSERT INTO `brand` (`name`) VALUES ('" + brand + "')");
                    } else {
                        int response = JOptionPane.showConfirmDialog(this, "Do you want to update this brand?", "Update", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            MySQL.execute("UPDATE `brand` SET `name`='" + brand + "' WHERE `name`='" + String.valueOf(jComboBox1.getSelectedItem()) + "'");
                            JOptionPane.showMessageDialog(this, "Brand updated", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }

                    loadBrand();
                    jTextField3.setText(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String id = jTextField1.getText();
        String brand = String.valueOf(jComboBox1.getSelectedItem());
        String name = jTextField2.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product id", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (brand.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select brand", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultSet = MySQL.execute("SELECT * FROM `product` WHERE `id` = '" + id + "' OR (`name` = '" + name + "' AND `brand_id` = '" + brandMap.get(brand) + "')");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Product already added", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL.execute("INSERT INTO `product` VALUES('" + id + "','" + brandMap.get(brand) + "','" + name + "')");
                    JOptionPane.showMessageDialog(this, "New Product Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                    resetProductUi();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        loadProducts();
        resetProductUi();
        loadStock();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int row = jTable2.getSelectedRow();

        jTextField1.setText(String.valueOf(jTable2.getValueAt(row, 0)));
        jTextField1.setEditable(false);

        jComboBox1.setSelectedItem(String.valueOf(jTable2.getValueAt(row, 3)));
        jTextField2.setText(String.valueOf(jTable2.getValueAt(row, 1)));

        loadStock();

        if (evt.getClickCount() == 2) {
            if (grn != null) {
                grn.getjTextField3().setText(String.valueOf(jTable2.getValueAt(row, 0)));
                grn.getjLabel11().setText(String.valueOf(jTable2.getValueAt(row, 3)));
                grn.getjLabel12().setText(String.valueOf(jTable2.getValueAt(row, 1)));
                grn.getjFormattedTextField2().requestFocus();
                this.dispose();
            }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String id = jTextField1.getText();
        String brand = String.valueOf(jComboBox1.getSelectedItem());
        String name = jTextField2.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product id", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (brand.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select brand", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultSet = MySQL.execute("SELECT * FROM `product` WHERE `name` = '" + name + "' AND `brand_id` = '" + brandMap.get(brand) + "' AND `id` != '" + id + "'");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Product already added", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL.execute("UPDATE `product` SET `brand_id` = '" + brandMap.get(brand) + "', `name` = '" + name + "' WHERE `id` = '" + id + "'");
                    loadProducts();
                    resetProductUi();
                    JOptionPane.showMessageDialog(this, "Product Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        loadStock();
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String priceMin = jFormattedTextField2.getText();
        String priceMax = jFormattedTextField3.getText();

        if (Double.parseDouble(priceMin) > Double.parseDouble(priceMax)) {
            JOptionPane.showMessageDialog(this, "Min price grater than max price", "Warning", JOptionPane.WARNING_MESSAGE);
            loadStock();
            jFormattedTextField3.setText(null);
        } else {
            loadStock();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
//        Date date1 = jDateChooser1.getDate();
//        Date date2 = jDateChooser2.getDate();
//
//        if (date1 == null) {
//            JOptionPane.showMessageDialog(this, "Please select start date", "Warning", JOptionPane.WARNING_MESSAGE);
//        } else if (date2 == null) {
//            JOptionPane.showMessageDialog(this, "Please select end date", "Warning", JOptionPane.WARNING_MESSAGE);
//        } else if (date1.after(date2)) {
//            JOptionPane.showMessageDialog(this, "Start date grater than end date", "Warning", JOptionPane.WARNING_MESSAGE);
//        } else {
        loadStock();
//        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        resetProductUi();
        resetStockUi();
        loadProducts();
        loadStock();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            int row = jTable1.getSelectedRow();
            invoice.getjTextField3().setText(String.valueOf(jTable1.getValueAt(row, 0)));
            invoice.getjLabel11().setText(String.valueOf(jTable1.getValueAt(row, 2)));
            invoice.getjLabel12().setText(String.valueOf(jTable1.getValueAt(row, 3)));
            invoice.getjTextField4().setText(String.valueOf(jTable1.getValueAt(row, 4)));
            invoice.getjLabel25().setText(String.valueOf(jTable1.getValueAt(row, 5)));
            invoice.getjLabel15().setText(String.valueOf(jTable1.getValueAt(row, 6)));
            invoice.getjLabel21().setText(String.valueOf(jTable1.getValueAt(row, 7)));
            invoice.getjFormattedTextField2().requestFocus();
            this.dispose();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatDarkLaf.setup();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Stock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
