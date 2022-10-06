
package com.hst.kirteerefinedoil;


public class Item {

    private String itemUid;
    private String date;
    private String itemName;
    private String itemPrice;
    private String itemLogo;
    private String itemRate;
    private String itemQty;

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public Item withItemUid(String itemUid) {
        this.itemUid = itemUid;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Item withDate(String date) {
        this.date = date;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Item withItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Item withItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public String getItemLogo() {
        return itemLogo;
    }

    public void setItemLogo(String itemLogo) {
        this.itemLogo = itemLogo;
    }

    public Item withItemLogo(String itemLogo) {
        this.itemLogo = itemLogo;
        return this;
    }

    public String getItemRate() {
        return itemRate;
    }

    public void setItemRate(String itemRate) {
        this.itemRate = itemRate;
    }

    public Item withItemRate(String itemRate) {
        this.itemRate = itemRate;
        return this;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public Item withItemQty(String itemQty) {
        this.itemQty = itemQty;
        return this;
    }

}
