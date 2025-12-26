package com.ai.skysmart.dto;

import java.util.List;

public class AdminDashboardResponse {
    private long totalFlights;
    private long totalBookings;
    private double totalRevenue;
    private double avgTicketPrice;
    private long todaysBookings;
    private long cancelledBookings;

    private List<RouteStat> topRoutes;
    private List<FlightDto> lowAvailabilityFlights;
    private List<FlightDto> highDemandFlights;

    private List<RevenuePoint> revenueByMonth;

    // Getters and Setters

    public long getTotalFlights() {
        return totalFlights;
    }

    public void setTotalFlights(long totalFlights) {
        this.totalFlights = totalFlights;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getAvgTicketPrice() {
        return avgTicketPrice;
    }

    public void setAvgTicketPrice(double avgTicketPrice) {
        this.avgTicketPrice = avgTicketPrice;
    }

    public long getTodaysBookings() {
        return todaysBookings;
    }

    public void setTodaysBookings(long todaysBookings) {
        this.todaysBookings = todaysBookings;
    }

    public long getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public List<RouteStat> getTopRoutes() {
        return topRoutes;
    }

    public void setTopRoutes(List<RouteStat> topRoutes) {
        this.topRoutes = topRoutes;
    }

    public List<FlightDto> getLowAvailabilityFlights() {
        return lowAvailabilityFlights;
    }

    public void setLowAvailabilityFlights(List<FlightDto> lowAvailabilityFlights) {
        this.lowAvailabilityFlights = lowAvailabilityFlights;
    }

    public List<FlightDto> getHighDemandFlights() {
        return highDemandFlights;
    }

    public void setHighDemandFlights(List<FlightDto> highDemandFlights) {
        this.highDemandFlights = highDemandFlights;
    }

    public List<RevenuePoint> getRevenueByMonth() {
        return revenueByMonth;
    }

    public void setRevenueByMonth(List<RevenuePoint> revenueByMonth) {
        this.revenueByMonth = revenueByMonth;
    }

    public AdminDashboardResponse(long totalFlights, long totalBookings, double totalRevenue, double avgTicketPrice, long todaysBookings, long cancelledBookings, List<RouteStat> topRoutes, List<FlightDto> lowAvailabilityFlights, List<FlightDto> highDemandFlights, List<RevenuePoint> revenueByMonth) {
        this.totalFlights = totalFlights;
        this.totalBookings = totalBookings;
        this.totalRevenue = totalRevenue;
        this.avgTicketPrice = avgTicketPrice;
        this.todaysBookings = todaysBookings;
        this.cancelledBookings = cancelledBookings;
        this.topRoutes = topRoutes;
        this.lowAvailabilityFlights = lowAvailabilityFlights;
        this.highDemandFlights = highDemandFlights;
        this.revenueByMonth = revenueByMonth;
    }

    public AdminDashboardResponse() {
    }
}
