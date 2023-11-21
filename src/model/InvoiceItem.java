/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author SINGER
 */
public class InvoiceItem {
    
    private String invoiceId;
    private String stockId;
    private String brand;
    private String name;
    private String qty;
    private String sellingPrice;
    private String mfg;
    private String exp;

    /**
     * @return the invoiceId
     */
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * @return the stockId
     */
    public String getStockId() {
        return stockId;
    }

    /**
     * @param stockId the stockId to set
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the qty
     */
    public String getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     * @return the sellingPrice
     */
    public String getSellingPrice() {
        return sellingPrice;
    }

    /**
     * @param sellingPrice the sellingPrice to set
     */
    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * @return the mfg
     */
    public String getMfg() {
        return mfg;
    }

    /**
     * @param mfg the mfg to set
     */
    public void setMfg(String mfg) {
        this.mfg = mfg;
    }

    /**
     * @return the exp
     */
    public String getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(String exp) {
        this.exp = exp;
    }
    
}
