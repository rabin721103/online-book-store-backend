package com.rabin.onlinebookstore.repository;

import com.rabin.onlinebookstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "SELECT * FROM cart WHERE user_id = :userId", nativeQuery = true)
    List<Cart> findAllBooksByUser(long userId);

    @Query(value = "SELECT * FROM cart WHERE user_id = :userId AND book_id = :bookId", nativeQuery = true)
    Cart findCartByUserIdAndBookId(int userId, int bookId);

}
