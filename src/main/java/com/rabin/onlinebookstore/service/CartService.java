package com.rabin.onlinebookstore.service;

import com.rabin.onlinebookstore.model.Cart;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.repository.CartRepository;
import com.rabin.onlinebookstore.utils.CartDto;
import com.rabin.onlinebookstore.utils.CustomException;
import com.rabin.onlinebookstore.utils.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartDto> getBooksFromCart(long userId) {
        List<Cart> cartList = cartRepository.findAllBooksByUser(userId);
        List<CartDto> cartDtoList = new ArrayList<>();
        for (Cart cart : cartList) {
            cartDtoList.add(new CartDto(cart.getCartId(), cart.getBook(), cart.getQuantity()));
        }
        return cartDtoList;
    }

    public Cart findCartByUserIdAndBookId(int userId, int bookId) {
        return cartRepository.findCartByUserIdAndBookId(userId, bookId);
    }


    public CartDto addBookToCart(Cart newCart) {
        Cart savedCart = cartRepository.save(newCart);
        return new CartDto(savedCart.getCartId(), savedCart.getBook(), savedCart.getQuantity());

    }

    public CartDto updateCart(Long cartId, Integer quantity, Integer userId) {
        try {
            Optional<Cart> optionalCart = cartRepository.findById(cartId);
            if (optionalCart.isPresent()) {
                Cart existingCart = optionalCart.get();
                if (existingCart.getUser().getUserId() == userId) {

                    existingCart.setQuantity(quantity);
                    Cart cart = cartRepository.save(existingCart);
                    return new CartDto(cart.getCartId(), cart.getBook(), cart.getQuantity());
                } else {
                    return null;
                }
            }
            throw new CustomException("Cart not found with ID: " + cartId);
        } catch (CustomException ex) {
            // Log the exception and rethrow or handle as appropriate
            throw new CustomException("Failed to update cart");
        }
    }

    public void deleteCart(Long id, Integer userId) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            if (existingCart.getUser().getUserId() == userId) {
                cartRepository.deleteById(id);
            } else {

                throw new CustomException("User not found with ID: " + id);
            }
        }
    }

}
