package com.adios.review_film.service;

import com.adios.review_film.dto.request.NewReviewRequest;
import com.adios.review_film.dto.request.UpdateReviewRequest;
import com.adios.review_film.dto.response.ReviewResponse;
import com.adios.review_film.entity.Review;

import java.util.List;


public interface ReviewService {
    ReviewResponse createReview(NewReviewRequest newReviewRequest);
    void deleteReview(String id);
    ReviewResponse updateReview(UpdateReviewRequest updateReviewRequest);
    List<ReviewResponse> findReviewAll();
    Review findReviewById(String id);
    List<Review> findReviewByName(String name);
    List<ReviewResponse> findReviewByNameResponse(String name);
    ReviewResponse findReviewByIdResponse(String id);

}
