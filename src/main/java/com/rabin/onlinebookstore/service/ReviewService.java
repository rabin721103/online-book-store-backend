package com.rabin.onlinebookstore.service;

import com.rabin.onlinebookstore.model.Books;
import com.rabin.onlinebookstore.model.Review;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.repository.ReviewRepository;
import com.rabin.onlinebookstore.utils.NewReviewDto;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.utils.ReviewDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDto> getAllReviewsByBook(long bookId){
        List<Review> reviewList = reviewRepository.getReviewByBookId(bookId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto(review);
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    public Review getReviewByUserIdAndBookId(long userId, long bookId){
        return reviewRepository.getReviewByUserIdAndBookId(userId, bookId);
    }

    public ReviewDto addReview(NewReviewDto newReviewDto, int id, String name) {
        Books book = new Books();
        book.setBookId(newReviewDto.getBookId());

        Users user = new Users();
        user.setUserId(id);
        user.setUsername(name);

        Review newReview = new Review(user, book, newReviewDto.getRating(), newReviewDto.getComment());

        try {
            Review review = reviewRepository.save(newReview);
            return new ReviewDto(review);
        } catch (Exception exception) {
            return null;
        }
    }
    public ResponseWrapper updateReview(NewReviewDto newReviewDto, long userId, long reviewId) {
        Review prevReview = reviewRepository.findById(reviewId).orElse(null);

        if (prevReview == null) {
            return new ResponseWrapper(false, 400, "Can not found review at the moment",null );
        } else {
            if (prevReview.getUser().getUserId() != userId) {
                return new ResponseWrapper(false, 400, "Error updating review", null);
            } else {
                prevReview.setRating(newReviewDto.getRating());
                prevReview.setComment(newReviewDto.getComment());

                Review newReview =  reviewRepository.save(prevReview);

                ReviewDto reviewDto1 = new ReviewDto(newReview);
                return new ResponseWrapper(true, 200, "Successfully updated review", reviewDto1);
            }
        }
    }
    public Map<String, Object> getSingleBookRating(long bookId) {
        List<Map<String, Object>> reviews = reviewRepository.getOverallRatingAndNumReviewsForSingleBook(bookId);
        try {
            return reviews.get(0);
        } catch (IndexOutOfBoundsException ex) {
            Map<String, Object> emptyRating = new HashMap<>();
            emptyRating.put("book_id", bookId);
            emptyRating.put("overall_rating", 0);
            emptyRating.put("num_reviews", 0);
            return emptyRating;
        }
    }

    public List<Map<String, Object>> getRatingOfBooks(List<Long> bookIds) {
        return reviewRepository.getOverallRatingAndNumReviewsForBooks(bookIds);
    }
}
