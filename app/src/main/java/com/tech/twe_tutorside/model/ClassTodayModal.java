package com.tech.twe_tutorside.model;

public class ClassTodayModal {


    private String name;
    private String orderId;

    private String rating;

    private String payment;


    public ClassTodayModal(String name, String orderId, String rating, String payment) {
        this.name = name;
        this.orderId = orderId;
        this.rating = rating;
        this.payment = payment;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
