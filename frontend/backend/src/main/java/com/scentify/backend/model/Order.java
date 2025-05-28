package com.scentify.backend.model;

import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private Buyer buyer;
    private String status;
    private Payment payMethod;
    private double totalAmount;
    private Date orderDate;
    private List<Product> items;

    public Order() {
    }

    public Order(String orderId, Buyer buyer, String status, Payment payMethod, double totalAmount, Date orderDate, List<Product> items) {
        this.orderId = orderId;
        this.buyer = buyer;
        this.status = status;
        this.payMethod = payMethod;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Payment payMethod) {
        this.payMethod = payMethod;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public boolean processPayment() {
        // Contoh implementasi sederhana
        if (payMethod != null && totalAmount > 0) {
            this.status = "PAID";
            return true;
        }
        this.status = "PAYMENT_FAILED";
        return false;
    }

    public String generateInvoice() {
        // Contoh implementasi sederhana
        return "Invoice\nOrder ID: " + orderId +
               "\nBuyer: " + (buyer != null ? buyer.toString() : "N/A") +
               "\nTotal: " + totalAmount +
               "\nStatus: " + status +
               "\nDate: " + (orderDate != null ? orderDate.toString() : "N/A") +
               "\nItems: " + (items != null ? items.toString() : "N/A");
    }
}