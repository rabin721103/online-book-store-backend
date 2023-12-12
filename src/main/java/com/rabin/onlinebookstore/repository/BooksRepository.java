package com.rabin.onlinebookstore.repository;

import com.rabin.onlinebookstore.model.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BooksRepository extends JpaRepository <Books, Integer> {

//  Page<Books> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(String title,String author,String genre, Pageable pageable);

    @Query(value = "SELECT * FROM Books WHERE LOWER(title) LIKE LOWER(CONCAT('%', :title, '%')) OR LOWER(author) LIKE LOWER(CONCAT('%', :author, '%'))OR LOWER(genre) LIKE LOWER(CONCAT('%', :genre, '%'))", nativeQuery = true)
    Page<Books> searchBooks(String title, String author, String genre, Pageable pageable);

}
