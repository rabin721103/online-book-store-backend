package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.model.Books;
import com.rabin.onlinebookstore.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    @Autowired
    BooksService booksService;

    @GetMapping("/")
    private ResponseWrapper getAllBooks(@RequestParam(name = "page" ,defaultValue = "1") int page){

        Page<Books> booksPage = booksService.getAllBooks(page);
        long totalItems = booksPage.getTotalElements();
        int totalPages = booksPage.getTotalPages();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(200);
        response.setMessage("Books retrieved successfully");
        response.setTotalItems(totalItems);
        response.setTotalPages(totalPages);
        response.setResponse(booksPage.getContent());
        response.setSuccess(true);
        return response;
    }

    //    @GetMapping("/")
//    private ResponseEntity<ResponseWrapper> getAllBooks()
//    {
//        booksService.getAllBooks();
//        ResponseWrapper response = new ResponseWrapper();
//        response.setStatusCode(HttpStatus.OK.value());
//        response.setMessage("Book retrieved successfully");
//        response.setResponse(booksService.getAllBooks());
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/{bookId}")
    private ResponseWrapper getBooks(@PathVariable("bookId") int bookId)
    {
        Books books = booksService.getBooksById(bookId);
        if (books != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(200);
            response.setMessage("Book retrieved successfully");
            response.setResponse(books);
            response.setSuccess(true);
            return response;
        } else
        {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(400);
            response.setMessage("Book not found");
            response.setSuccess(false);
            return response;
        }
    }
    @PostMapping("/")
    private ResponseWrapper saveBook(@RequestBody Books books)
    {
        booksService.saveOrUpdate(books);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(200);
        response.setMessage("Book inserted successfully");
        response.setResponse(booksService.saveOrUpdate(books));
        response.setSuccess(true);
        return response;
    }
    @PutMapping("/{bookId}")
    public ResponseWrapper updateBook(@PathVariable("bookId")int bookId, @RequestBody Books books) {
        Books updatedBooks = booksService.updateBook(bookId, books);
        ResponseWrapper response = new ResponseWrapper();
        if (updatedBooks != null ) {
            response.setStatusCode(200);
            response.setMessage("book retrieved successfully");
            response.setResponse(updatedBooks);
            response.setSuccess(true);
            return response;
        } else {
            response.setStatusCode(400);
            response.setMessage("book not found");
            response.setSuccess(false);
            return response;
        }
    }
    @DeleteMapping("/{bookId}")
    public ResponseWrapper deleteBook(@PathVariable int bookId) {
        booksService.deleteBook(bookId);
        return new ResponseWrapper(true, 200, "Successfully deleted", null);
    }
}
