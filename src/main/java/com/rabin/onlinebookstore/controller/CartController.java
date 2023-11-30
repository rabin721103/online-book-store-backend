package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.model.Books;
import com.rabin.onlinebookstore.model.Cart;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.service.CartService;
import com.rabin.onlinebookstore.utils.CartDto;
import com.rabin.onlinebookstore.utils.CustomException;
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
        Integer userId = (Integer) request.getAttribute("userId");
        return new ResponseWrapper(true,200,"Successfully fetched cart",cartService.getBooksFromCart(userId));
    }
    @PostMapping("/add")
    public ResponseWrapper addBookToCart(@RequestBody Integer bookId, HttpServletRequest request) {
        Cart newCart = new Cart();
        Books book = new Books();
        Users user = new Users();

        Integer decodedUserId = (Integer) request.getAttribute("userId");
        book.setBookId(bookId);
        user.setUserId(decodedUserId);

        newCart.setQuantity(1);
        newCart.setBook(book);
        newCart.setUser(user);
        return new ResponseWrapper(true, 200,"Book added to cart",cartService.addBookToCart(newCart));
    }
    @PutMapping("/update/{cartId}")
    public ResponseWrapper updateCart(@PathVariable Long cartId, @RequestBody Integer qty,  HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            CartDto newCartDto =cartService.updateCart(cartId, qty, userId);
            ResponseWrapper response = new ResponseWrapper(true,200,"Quantity in cart is updated successfully",newCartDto);
//            response.setStatusCode(200);
//            response.setMessage("Cart updated successfully");
//            response.setResponse(newCartDto);
            return response;

        }
        catch(CustomException e){
            throw new CustomException(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{cartId}")
    public ResponseWrapper deleteCart(@PathVariable Long cartId, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        cartService.deleteCart(cartId, userId);
        ResponseWrapper response = new ResponseWrapper(true,200,("Cart with"+cartId+"Deleted Successfully"),cartId);
//        response.setStatusCode(HttpStatus.OK.value());
//        response.setMessage("User deleted successfully");
//        response.setResponse(cartId);
        return response;

    }

}
