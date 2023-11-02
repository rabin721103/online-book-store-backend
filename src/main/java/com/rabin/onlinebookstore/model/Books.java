package com.rabin.onlinebookstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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


}
