package com.rabin.onlinebookstore.service;

import com.rabin.onlinebookstore.model.Cart;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.repository.CartRepository;
import com.rabin.onlinebookstore.utils.CartDto;
import com.rabin.onlinebookstore.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<CartDto> cartResDtoList = new ArrayList<>();
        for (Cart cart : cartList) {
            cartResDtoList.add(new CartDto(cart.getCartId(), cart.getBook(), cart.getQuantity()));
        }
        return cartResDtoList;
    }

    public CartDto addBookToCart(Cart cart) {
        Cart newCart = cartRepository.save(cart);
        return new CartDto(newCart.getCartId(), newCart.getBook(), newCart.getQuantity());
    }
    public CartDto updateCart(Long cartId, Integer quantity, Integer userId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            if (existingCart.getUser().getUserId()==userId) {

                existingCart.setQuantity(quantity);
                Cart cart = cartRepository.save(existingCart);
                return new CartDto(cart.getCartId(), cart.getBook(), cart.getQuantity());
            } else {
                return null;
            }

        }
        return null;
    }

    public void deleteCart(Long id, Integer userId) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            if (existingCart.getUser().getUserId()==userId) {
                cartRepository.deleteById(id);
            } else {

                throw new UserNotFoundException("User not found with ID: " + id);
            }
        }
    }

}
