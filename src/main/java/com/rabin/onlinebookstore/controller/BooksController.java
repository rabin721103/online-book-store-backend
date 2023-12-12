package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.service.ReviewService;
import com.rabin.onlinebookstore.model.BookResDto;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.model.Books;
import com.rabin.onlinebookstore.service.BooksService;
import com.rabin.onlinebookstore.model.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    private final BooksService booksService;
    private final ReviewService reviewService;
    @Autowired
    public BooksController(BooksService booksService, ReviewService reviewService) {
    this.booksService = booksService;
    this.reviewService = reviewService;
    }
    @GetMapping("/")
    public ResponseWrapper getAllBooks(@RequestParam(name = "query", defaultValue = "", required = false) String title,
                                                       @RequestParam(name = "query", defaultValue = "", required = false) String author,
                                                       @RequestParam(name = "query", defaultValue = "", required = false) String genre,
                                                       @RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {
        Page<Books> booksPage = booksService.getAllBooks(title,author,genre,pageNo);
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
    @GetMapping("/{bookId}")
    public ResponseWrapper getBooks(@PathVariable("bookId") int bookId)
    {
        Books books = booksService.getBooksById(bookId);
        if (books != null) {
            Map<String, Object> rating = reviewService.getSingleBookRating(books.getBookId());
            List<ReviewDto> reviews = reviewService.getAllReviewsByBook(books.getBookId());
            float overallRating = Float.parseFloat(rating.get("overall_rating").toString());
            long numRatings = Long.parseLong(rating.get("num_reviews").toString());
            BookResDto bookResDto = new BookResDto(books, overallRating, numRatings);
            bookResDto.setReviews(reviews);

            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(200);
            response.setMessage("Book retrieved successfully");
            response.setResponse(bookResDto);
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
    public ResponseWrapper saveBook(@RequestBody Books books)
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
