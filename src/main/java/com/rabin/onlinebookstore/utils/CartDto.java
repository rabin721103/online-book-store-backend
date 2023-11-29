package com.rabin.onlinebookstore.utils;

import com.rabin.onlinebookstore.model.Books;

public class CartDto {
    private long cartId;
    private Books book;
    private int quantity;
    public CartDto(){

    }

    public CartDto(long cartId, Books book, int quantity) {
        this.cartId = cartId;
        this.book = book;
        this.quantity = quantity;
    }

    public long getCartxId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
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
}
