package com.rabin.onlinebookstore.repository;

import com.rabin.onlinebookstore.model.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository <Books, Integer> {

    Page<Books> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(String title,String author,String genre, Pageable pageable);

}
