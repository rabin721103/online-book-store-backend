package com.rabin.onlinebookstore.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @ManyToOne
    @JoinColumn(name="book_id",nullable = false)
    private Books book;
    @Column(name="rating")
    private int rating;
    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();

    public Review(Users user, Books book, int rating, String comment) {
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.comment = comment;
    }

    public Review() {

    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewIdd) {
        this.reviewId = reviewId;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}
