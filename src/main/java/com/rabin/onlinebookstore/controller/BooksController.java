package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.model.Books;
import com.rabin.onlinebookstore.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    @Autowired
    BooksService booksService;
    @GetMapping("/")
    private ResponseEntity<ResponseWrapper> getAllBooks()
    {
        booksService.getAllBooks();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Book retrieved successfully");
        response.setResponse(booksService.getAllBooks());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{bookId}")
    private ResponseEntity<ResponseWrapper> getBooks(@PathVariable("bookId") int bookId)
    {
        Books books = booksService.getBooksById(bookId);
        if (books != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Book retrieved successfully");
            response.setResponse(books);
            return ResponseEntity.ok(response);
        } else
        {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Book not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PostMapping("/")
    private ResponseEntity<ResponseWrapper> saveBook(@RequestBody Books books)
    {
        booksService.saveOrUpdate(books);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Book inserted successfully");
        response.setResponse(booksService.saveOrUpdate(books));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{bookId}")
    public ResponseEntity<ResponseWrapper> updateBook(@PathVariable("bookId")int bookId, @RequestBody Books books) {
        Books updatedBooks = booksService.updateBook(bookId, books);
        ResponseWrapper response = new ResponseWrapper();
        if (updatedBooks != null ) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("book retrieved successfully");
            response.setResponse(updatedBooks);
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("book not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @DeleteMapping("/{bookId}")
    public ResponseWrapper deleteBook(@PathVariable int bookId) {
        booksService.deleteBook(bookId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("book deleted successfully");
        return new ResponseWrapper(true, 200, "Successfully deleted", null);
    }
}
