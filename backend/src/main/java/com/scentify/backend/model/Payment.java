package com.scentify.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;     // Contoh: "VISA", "ShopeePay", "COD"
    private String status;     // Contoh: "SUCCESS", "FAILED"
    private double amount;     // Jumlah uang yang dibayarkan

    public Payment() {}

    public Payment(String method, String status, double amount) {
        this.method = method;
        this.status = status;
        this.amount = amount;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
