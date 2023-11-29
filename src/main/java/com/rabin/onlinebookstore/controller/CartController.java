package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.model.Books;
import com.rabin.onlinebookstore.model.Cart;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.service.CartService;
import com.rabin.onlinebookstore.utils.CartDto;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.utils.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping()
    public ResponseWrapper getAllBooks(HttpServletRequest request) {
        Users user = (Users) request.getAttribute("user");
        return new ResponseWrapper(true,200,"Successfully fetched cart",cartService.getBooksFromCart(user.getUserId()));
    }
    @PostMapping("/add")
    public ResponseWrapper addBookToCart(@RequestBody Integer bookId, HttpServletRequest request) {
        Cart newCart = new Cart();
        Books book = new Books();
        Users user = new Users();

        Users decodedUser = (Users) request.getAttribute("user");
        book.setBookId(bookId);
        user.setUserId(decodedUser.getUserId());

        newCart.setQuantity(1);
        newCart.setBook(book);
        newCart.setUser(user);
        return new ResponseWrapper(true, 200,"Book added to cart",cartService.addBookToCart(newCart));
    }
    @PutMapping("/update/{cartId}")
    public ResponseWrapper editCart(@PathVariable Long cartId, @RequestBody Integer qty,  HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            CartDto newCardDto =cartService.updateCart(cartId, qty, userId);
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("Cart updated successfully");
            response.setResponse(newCardDto);
            return response;
        }
        catch(UserNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{cartId}")
    public ResponseWrapper deleteCart(@PathVariable Long cartId, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        cartService.deleteCart(cartId, userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        return response;

    }

}
