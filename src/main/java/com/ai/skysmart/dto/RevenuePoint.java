package com.ai.skysmart.dto;

public class RevenuePoint {
    private String label;  // Month or Day
    private double revenue;

    public RevenuePoint(String label, double revenue) {
        this.label = label;
        this.revenue = revenue;
    }

    public RevenuePoint() {
    }

    // Getters and Setters
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
}
