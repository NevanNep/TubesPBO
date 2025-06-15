package com.scentify.backend.dto;

import java.util.Date;
import java.util.List;

public class OrderHistoryDTO {
    private String orderId;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private List<ItemBriefDTO> items;

    // ✅ Constructor lengkap
    public OrderHistoryDTO(String orderId, Date orderDate, double totalAmount, String status, List<ItemBriefDTO> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.items = items;
    }

    // ✅ Getter dan Setter
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemBriefDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemBriefDTO> items) {
        this.items = items;
    }
}
