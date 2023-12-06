package com.rabin.onlinebookstore.model;

import jakarta.persistence.*;

import java.awt.print.Book;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Books book;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name="date")
    private LocalDateTime date = LocalDateTime.now();
    @Column(name = "status")
    private String status;
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public Order (Cart cart){
        this.user = cart.getUser();
        this.book = cart.getBook();
        this.quantity = cart.getQuantity();
        this.price = cart.getBook().getPrice();
        this.totalPrice = cart.getQuantity() * cart.getBook().getPrice();
        this.status = "processing";
        this.updatedOn = LocalDateTime.now();
    }

    public Order (Users user, Books book, int quantity, double totalPrice, String status, LocalDateTime updatedOn){
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.updatedOn = updatedOn;
    }

    public Order() {

    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
