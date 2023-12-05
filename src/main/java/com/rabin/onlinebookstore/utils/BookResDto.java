package com.rabin.onlinebookstore.utils;

import com.rabin.onlinebookstore.model.Books;

import java.util.List;

public class BookResDto {
    private int bookId;
    private String title;
    private String author;
    private String genre;
    private int price;
    private Boolean availability;
    private double overallRating;
    private long numberOfReviews;
    private List<ReviewDto> reviews;


    public BookResDto(Books book, double overallRating, long numberOfReviews) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.price = book.getPrice();
        this.availability=book.isAvailability();
        this.overallRating = overallRating;
        this.numberOfReviews = numberOfReviews;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public long getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(long numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
