package com.rabin.onlinebookstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String genre;
    @Column
    private int price;
    @Column
    private boolean availability;
  /*  @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Cart> carts;
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Order> orders;
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Review> reviews;*/


}
