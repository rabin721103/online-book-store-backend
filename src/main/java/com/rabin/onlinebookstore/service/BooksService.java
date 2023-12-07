package com.rabin.onlinebookstore.service;

import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.model.Books;
import com.rabin.onlinebookstore.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    @Autowired
    BooksRepository booksRepository;
    int size = 4;
    List<Books> books = new ArrayList<Books>();
    public Page<Books> getAllBooks(String title,String author, String genre, int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo -1 , size);
        return booksRepository.findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase( title, author, genre, pageable);
    }

    public Books getBooksById(int id) {
        Optional<Books> optionalBooks = booksRepository.findById(id);
        return optionalBooks.orElse(null);
    }
    public Books saveOrUpdate(Books books) {
        return booksRepository.save(books);
    }
    public Books updateBook(int id, Books updatedBook) {
        Optional<Books> optionalBook = booksRepository.findById(id);
        if (optionalBook.isPresent()) {
            Books existingBook = optionalBook.get();
            //updating existing book from updatedBook
            existingBook.setBookId(id);
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setAvailability(updatedBook.isAvailability());
            return booksRepository.save(existingBook);
        }
        return null;
    }
    public ResponseWrapper deleteBook(int id) {
        Optional<Books> optionalUsers = booksRepository.findById(id);
        if (optionalUsers.isPresent()){
            booksRepository.deleteById(id);
        }
        return null;
    }
}
