package com.rabin.onlinebookstore.repository;

import com.rabin.onlinebookstore.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository <Books, Integer> {

}
