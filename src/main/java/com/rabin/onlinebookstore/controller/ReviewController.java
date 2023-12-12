package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.model.Review;
import com.rabin.onlinebookstore.service.ReviewService;
import com.rabin.onlinebookstore.model.NewReviewDto;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.model.ReviewDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping()
    public ResponseWrapper addReview(@Valid @RequestBody NewReviewDto newReviewDto, HttpServletRequest request){
        int userId = (int) request.getAttribute("userId");
        String username = (String) request.getAttribute("username") ;

        Review prevReview = reviewService.getReviewByUserIdAndBookId(userId, newReviewDto.getBookId());

        if(prevReview == null){
            ReviewDto newReview = reviewService.addReview(newReviewDto, userId, username);
            if(newReview == null){
                return new ResponseWrapper(false, 400, "Error adding review", null);
            }else{
                return new ResponseWrapper(true,200 , "Review added",newReview);
            }
        }else{
            return reviewService.updateReview(newReviewDto, userId, prevReview.getReviewId());
        }
    }

}
