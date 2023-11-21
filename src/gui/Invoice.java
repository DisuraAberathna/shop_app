/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.InvoiceItem;
import model.MySQL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author SINGER
 */
public class Invoice extends javax.swing.JFrame {

    HashMap<String, InvoiceItem> invoiceItemMap = new HashMap<>();
    HashMap<String, String> paymentMethodMap = new HashMap<>();

    /**
     * Creates new form Invoice
     */
    public Invoice() {
        initComponents();
//        jLabel18.setText(SignIn.getEmployeeEmail());
jLabel18.setText("disura005@gmail.com");
        generateInvoiceNumber();
        loadPaymentMethods();
    }

    private void generateInvoiceNumber() {
        long id = System.currentTimeMillis();
        jTextField1.setText(String.valueOf(id));
    }

    //mobile
    public JTextField getJTextField2() {
        return jTextField2;
    }

    //name
    public JLabel getJLable23() {
        return jLabel23;
    }

    //stock id
    public JTextField getjTextField3() {
        return jTextField3;
    }

    //brand
    public JLabel getjLabel11() {
        return jLabel11;
    }

    //stock name
    public JLabel getjLabel12() {
        return jLabel12;
    }

//    selling price
    public JTextField getjTextField4() {
        return jTextField4;
    }

    //mfg
    public JLabel getjLabel15() {
        return jLabel15;
    }

    //exp
    public JLabel getjLabel21() {
        return jLabel21;
    }

    //qty
    public JFormattedTextField getjFormattedTextField2() {
        return jFormattedTextField2;
    }

//available qty
    public JLabel getjLabel25() {
        return jLabel25;
    }

    //points
    public JFormattedTextField getjFormattedTextField4() {
        return jFormattedTextField4;
    }

    private void loadInvoiceItems() {
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);

        total = 0;

        for (InvoiceItem invoiceItem : invoiceItemMap.values()) {
            Vector<String> vector = new Vector<>();
            vector.add(invoiceItem.getStockId());
            vector.add(invoiceItem.getBrand());
            vector.add(invoiceItem.getName());
            vector.add(invoiceItem.getQty());
            vector.add(invoiceItem.getSellingPrice());
            vector.add(invoiceItem.getMfg());
            vector.add(invoiceItem.getExp());

            double itemTotal = Double.parseDouble(invoiceItem.getQty()) * Double.parseDouble(invoiceItem.getSellingPrice());
            total += itemTotal;
            vector.add(String.valueOf(itemTotal));

            dtm.addRow(vector);
        }
        jLabel5.setText(String.valueOf(total));

//        1
        calculate();
    }

    private void loadPaymentMethods() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `payment_method`");
            Vector<String> vector = new Vector<>();

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                this.paymentMethodMap.put(resultSet.getString("name"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        jFormattedTextField2.setText(null);
        jTextField4.setText(null);
        jTextField3.setText(null);
        jLabel11.setText("...");
        jLabel12.setText("...");
        jLabel15.setText("...");
        jLabel21.setText("...");
        jLabel25.setText("...");
    }

    private double total = 0;
    private double discount = 0;
    private double payment = 0;
    private boolean withdrawPoints = false;
    private double balance = 0;
    private String paymentMethod = "Select";
    private double newPoints = 0;

    private void calculate() {

        /////////settings
        if (jFormattedTextField1.getText().isEmpty()) {
            discount = 0;
        } else {
            discount = Double.parseDouble(jFormattedTextField1.getText());
        }

        if (jFormattedTextField3.getText().isEmpty()) {
            payment = 0;
        } else {
            payment = Double.parseDouble(jFormattedTextField3.getText());
        }

        total = Double.parseDouble(jLabel5.getText());

        if (jCheckBox1.isSelected()) {
            withdrawPoints = true;
        } else {
            withdrawPoints = false;
        }

        paymentMethod = String.valueOf(jComboBox1.getSelectedItem());

        ///////////settings
        total -= discount;

        if (total < 0) {
            //discount error
            jFormattedTextField1.setText("0");
        } else {
            //discount success

            if (withdrawPoints) {

                if (Double.parseDouble(jFormattedTextField4.getText()) == total) {
                    newPoints = 0;
                    total = 0;
                    //no payment required

                } else if (Double.parseDouble(jFormattedTextField4.getText()) < total) {
                    newPoints = 0;
                    total -= Double.parseDouble(jFormattedTextField4.getText());
                    //payment required

                } else {
                    newPoints = Double.parseDouble(jFormattedTextField4.getText()) - total;
                    total = 0;
                    //ne payment required
                }

            }

        }

        if (paymentMethod.equals("Cash")) {
            balance = payment - total;
            jFormattedTextField3.setEditable(true);

            if (balance < 0) {
                jButton5.setEnabled(false);
            } else {
                jButton5.setEnabled(true);
            }

        } else {
            //card payment
            payment = total;
            balance = 0;
            jFormattedTextField3.setText(String.valueOf(payment));
            jFormattedTextField3.setEditable(false);
            jButton5.setEnabled(true);

        }

        jLabel22.setText(String.valueOf(balance));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel17.setText("Employee");

        jLabel18.setText("...");

        jLabel1.setText("Invoice Number");

        jTextField1.setEditable(false);

        jLabel2.setText("Customer");

        jTextField2.setEditable(false);

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setText("Stock");

        jTextField3.setEditable(false);

        jButton2.setText("...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jLabel14.setText("Qty");

        jLabel10.setText("Brand");

        jLabel11.setText("...");

        jLabel13.setText("Name");

        jLabel12.setText("...");

        jLabel16.setText("Selling Price");

        jLabel19.setText("MFD");

        jLabel20.setText("EXP");

        jLabel15.setText("...");

        jLabel21.setText("...");

        jButton3.setText("Add To Invoice");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel23.setText("...");

        jTextField4.setEditable(false);

        jLabel24.setText("Available Qty");

        jLabel25.setForeground(new java.awt.Color(255, 0, 0));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("...");

        jLabel26.setText("Available Points");

        jFormattedTextField4.setEditable(false);
        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jButton6.setText("Home");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(199, 199, 199))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton1)))
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(54, 54, 54)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton2)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton6)
                                        .addContainerGap())
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton4))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel14)
                                                        .addGap(25, 25, 25)
                                                        .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel10))
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel16)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jTextField4))))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel19)
                                                    .addComponent(jLabel20))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(146, 146, 146)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jLabel18))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jButton2)
                        .addComponent(jButton6)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jLabel26)
                            .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel10))
                                        .addGap(31, 31, 31))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel16)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel25))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel14)
                                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel24)
                                        .addGap(3, 3, 3)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton3)
                                    .addComponent(jButton4)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel15))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel21)
                                        .addGap(3, 3, 3))
                                    .addComponent(jLabel20))))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock Id ", "Brand ", "Name", "Qty", "Selling Price", "MFG", "Exp", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setText("Total");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("0.00");

        jLabel3.setText("Discount");

        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyReleased(evt);
            }
        });

        jLabel6.setText("Payment Method");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel7.setText("Payment");

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFormattedTextField3KeyTyped(evt);
            }
        });

        jLabel8.setText("Balance");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("0.00");

        jButton5.setText("Print Invoice");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel27.setText("Withdraw Points");

        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox1))
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField3)
                            .addComponent(jFormattedTextField1)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, 129, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CustomerRegistration customerRegistration = new CustomerRegistration();
        customerRegistration.setVisible(true);
        customerRegistration.setInvoice(this);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Stock stock = new Stock();
        stock.setVisible(true);
        stock.setInvoice(this);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jFormattedTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField3KeyReleased
//        4
        calculate();
    }//GEN-LAST:event_jFormattedTextField3KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        reset();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String stockId = jTextField3.getText();
        String brand = jLabel11.getText();
        String name = jLabel12.getText();
        String qty = jFormattedTextField2.getText();
        String sellingPrice = jTextField4.getText();
        String mfg = jLabel15.getText();
        String exp = jLabel21.getText();

        if (stockId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Somthing went wrong", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (brand.isEmpty() && sellingPrice.isEmpty() && name.isEmpty() && mfg.isEmpty() && exp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select stock", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (qty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter qty", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setStockId(stockId);
            invoiceItem.setName(name);
            invoiceItem.setBrand(brand);
            invoiceItem.setQty(qty);
            invoiceItem.setSellingPrice(sellingPrice);
            invoiceItem.setExp(exp);
            invoiceItem.setMfg(mfg);

            if (invoiceItemMap.get(stockId) == null) {
                invoiceItemMap.put(stockId, invoiceItem);
            } else {
                InvoiceItem found = invoiceItemMap.get(stockId);

                int option = JOptionPane.showConfirmDialog(this, "Do you want to Update the qty of Product : " + name, "Message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    found.setQty(String.valueOf(Double.parseDouble(found.getQty()) + Double.parseDouble(qty)));
                }
            }
            loadInvoiceItems();
            reset();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jFormattedTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyReleased
//       2
        calculate();
    }//GEN-LAST:event_jFormattedTextField1KeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
//      3
        calculate();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
//        5
        calculate();
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String id = jTextField1.getText();
        String customerMobile = jTextField2.getText();
        String employeeEmail = jLabel18.getText();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String paidAmount = jFormattedTextField3.getText();
        String paymentMethodId = paymentMethodMap.get(String.valueOf(jComboBox1.getSelectedItem()));
        String discount = String.valueOf(jFormattedTextField1.getText());

        //validation
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Somthing went wrong", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (customerMobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer mobile", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (employeeEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Somthing went wrong. Sign in again", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (paidAmount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter paid amount", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (paymentMethodId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select payment method", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (discount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter discount", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                //add to invoice
                MySQL.execute("INSERT INTO `invoice` "
                        + "VALUES ('" + id + "','" + employeeEmail + "','" + customerMobile + "','" + dateTime + "','" + paidAmount + "','" + paymentMethodId + "','" + discount + "')");
                //add to invoice

                //add to invoice item
                for (InvoiceItem item : invoiceItemMap.values()) {
                    MySQL.execute("INSERT INTO `invoice_item` (`stock_id`,`qty`,`invoice_id`)"
                            + "VALUES ('" + item.getStockId() + "','" + item.getQty() + "','" + id + "')");

                    //update stock
                    MySQL.execute("UPDATE `stock` SET `qty` = `qty` - '" + item.getQty() + "' WHERE `id` = '" + item.getStockId() + "'");
                    //update stock

                }
                //add to invoice item

                //customer points
                double points = Double.parseDouble(jLabel5.getText()) / 100;

                if (withdrawPoints) {
                    newPoints += points;
                    MySQL.execute("UPDATE `customer` SET `points` = '" + newPoints + "' WHERE `mobile` = '" + customerMobile + "'");
                } else {
                    MySQL.execute("UPDATE `customer` SET `points` = `points` + '" + points + "' WHERE `mobile` = '" + customerMobile + "'");
                }
                //customer points

                // open report 
                String path = "src//resources/Blank_A4.jasper";

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("Parameter1", jLabel5.getText());
                parameters.put("Parameter2", jFormattedTextField1.getText());
                parameters.put("Parameter3", jComboBox1.getSelectedItem());
                parameters.put("Parameter4", jFormattedTextField3.getText());
                parameters.put("Parameter5", jLabel22.getText());
                parameters.put("Parameter6", jTextField1.getText());
                parameters.put("Parameter7", jLabel23.getText());
                parameters.put("Parameter8", jLabel18.getText());
                parameters.put("Parameter9", dateTime);

                JRTableModelDataSource datasource = new JRTableModelDataSource(jTable1.getModel());

                JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, datasource);
                JasperViewer.viewReport(jasperPrint, true);

                // open report            
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //validation
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jFormattedTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField3KeyTyped
        String total = jLabel5.getText();
        String payment = jFormattedTextField3.getText();
        
        if (!payment.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            jLabel22.setText("Invalid");
            jLabel2.setForeground(Color.RED);
        } else {
            double balance = Double.parseDouble(payment) - Double.parseDouble(total);
            jLabel22.setText(String.valueOf(balance));
//            jLabel8.setForeground((Color) FlatDarkLaf.getSystemColorGetter());
            jLabel22.setForeground(jLabel5.getForeground());
        }
    }//GEN-LAST:event_jFormattedTextField3KeyTyped

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new Home().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatDarkLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Invoice().setVisible(true);
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
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
