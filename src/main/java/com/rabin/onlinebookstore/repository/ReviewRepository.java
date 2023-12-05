package com.rabin.onlinebookstore.repository;

import com.rabin.onlinebookstore.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT * FROM reviews WHERE book_id = :bookId", nativeQuery = true)
    List<Review> getReviewByBookId(long bookId);

    @Query(value = "SELECT * from reviews WHERE user_id = :userId AND book_id =:bookId limit 1", nativeQuery = true)
    Review getReviewByUserIdAndBookId(long userId, long bookId);
    @Query(value = "SELECT book_id, AVG(rating) AS overall_rating, COUNT(*) AS num_reviews FROM reviews WHERE book_id IN :bookIds AND rating > 0 GROUP BY book_id HAVING AVG(rating) IS NOT NULL", nativeQuery = true)
    List<Map<String, Object>> getOverallRatingAndNumReviewsForBooks(List<Long> bookIds);
    @Query(value = "SELECT book_id, AVG(rating) AS overall_rating, COUNT(*) AS num_reviews FROM reviews WHERE book_id = :bookId AND rating > 0 GROUP BY book_id HAVING AVG(rating) IS NOT NULL", nativeQuery = true)
    List<Map<String, Object>> getOverallRatingAndNumReviewsForSingleBook(long bookId);
}
