package com.newspaper.invoice;

public class InvoiceItem {

    private String pubName;
    private Double pubCost;
    private int amt;

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    private double totalCost;

    public InvoiceItem(String pubName, Double pubCost, int amt, double totalCost) {
        this.pubName = pubName;
        this.pubCost = pubCost;
        this.amt = amt;
        this.totalCost = totalCost;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public Double getPubCost() {
        return pubCost;
    }

    public void setPubCost(Double pubCost) {
        this.pubCost = pubCost;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "pubName='" + pubName + '\'' +
                ", pubCost=" + pubCost +
                ", amt=" + amt +
                ", totalCost=" + totalCost +
                '}';
    }
}
