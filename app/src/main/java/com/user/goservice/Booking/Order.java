package com.user.goservice.Booking;

public class Order {
    public String serviceCost, serviceStatus, date, vehicle, oid;

    public Order(String serviceCost, String serviceStatus, String date, String vehicle, String oid) {
        this.serviceCost = serviceCost;
        this.serviceStatus = serviceStatus;
        this.date = date;
        this.vehicle = vehicle;
        this.oid = oid;
    }
}
