package com.scentify.backend.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id; // format "ORD123456" ditentukan manual di service

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payMethod;

    private double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    public Order() {}

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Buyer getBuyer() { return buyer; }
    public void setBuyer(Buyer buyer) { this.buyer = buyer; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Payment getPayMethod() { return payMethod; }
    public void setPayMethod(Payment payMethod) { this.payMethod = payMethod; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) {
        this.items = items;
        if (items != null) {
            for (OrderItem item : items) {
                item.setOrder(this);
            }
        }
    }

    public boolean processPayment() {
        if (payMethod != null && totalAmount > 0) {
            this.status = "PAID";
            return true;
        }
        this.status = "PAYMENT_FAILED";
        return false;
    }
}
