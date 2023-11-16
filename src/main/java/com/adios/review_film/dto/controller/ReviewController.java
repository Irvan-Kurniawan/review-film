package com.adios.review_film.dto.controller;

import com.adios.review_film.dto.request.NewReviewRequest;
import com.adios.review_film.dto.request.UpdateReviewRequest;
import com.adios.review_film.dto.response.CommonResponse;
import com.adios.review_film.dto.response.ReviewResponse;
import com.adios.review_film.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(value = "/api/reviews")
    public ResponseEntity<?> createReview(@RequestBody NewReviewRequest newReviewRequest){
        ReviewResponse review = reviewService.createReview(newReviewRequest);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Create new Review successful")
                .statusCode(HttpStatus.CREATED.value())
                .data(review)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
    @GetMapping(value = "/api/reviews/{id}")
    public ResponseEntity<?> findReviewByID(@PathVariable String id){
        ReviewResponse reviewByIdResponse = reviewService.findReviewByIdResponse(id);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Find Review by ID successful")
                .statusCode(HttpStatus.OK.value())
                .data(reviewByIdResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @GetMapping(value = "/api/reviews/search")
    public ResponseEntity<?> findReviewByName(@RequestParam String name) {
        List<ReviewResponse> reviewByIdResponse = reviewService.findReviewByNameResponse(name);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Find Review by Name successful")
                .statusCode(HttpStatus.OK.value())
                .data(reviewByIdResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @GetMapping(value = "/api/reviews")
    public ResponseEntity<?> findReviewAll(){
            List<ReviewResponse> reviewServiceAll = reviewService.findReviewAll();

            CommonResponse<List<ReviewResponse>> commonResponse = CommonResponse.<List<ReviewResponse>>builder()
                    .message("All review get")
                    .statusCode(HttpStatus.OK.value())
                    .data(reviewServiceAll)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @PutMapping(value = "/api/reviews")
    public ResponseEntity<?> updateReview(@RequestBody UpdateReviewRequest updateReviewRequest){
        ReviewResponse reviewResponse = reviewService.updateReview(updateReviewRequest);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Update Review Successful")
                .statusCode(HttpStatus.OK.value())
                .data(reviewResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @DeleteMapping(value = "/api/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable String id){
        reviewService.deleteReview(id);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Delete Review successful")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
