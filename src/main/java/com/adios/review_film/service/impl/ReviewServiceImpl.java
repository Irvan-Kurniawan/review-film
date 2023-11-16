package com.adios.review_film.service.impl;

import com.adios.review_film.dto.request.NewReviewRequest;
import com.adios.review_film.dto.request.UpdateReviewRequest;
import com.adios.review_film.dto.response.ReviewResponse;
import com.adios.review_film.entity.Film;
import com.adios.review_film.entity.Review;
import com.adios.review_film.entity.User;
import com.adios.review_film.repository.FilmRepository;
import com.adios.review_film.repository.ReviewRepository;
import com.adios.review_film.repository.UserRepository;
import com.adios.review_film.service.ReviewService;
import com.adios.review_film.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReviewResponse createReview(NewReviewRequest newReviewRequest) {
        validationUtil.validate(newReviewRequest);
        try {
            User user = userRepository.findById(newReviewRequest.getUserId());
            Film film = filmRepository.findById(newReviewRequest.getFilmId());
            Review review = Review.builder()
                    .user(user)
                    .film(film)
                    .reviewDate(LocalDate.now())
                    .review(newReviewRequest.getReview())
                    .build();
            reviewRepository.saveAndFlush(review);
            return getReviewResponse(review);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Review already exist");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteReview(String id) {
        Review orElseThrow = getOrElseThrow(id);
        reviewRepository.delete(orElseThrow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReviewResponse updateReview(UpdateReviewRequest updateReviewRequest) {
        validationUtil.validate(updateReviewRequest);
        try {
            getOrElseThrow(updateReviewRequest.getId());
            User user = userRepository.findById(updateReviewRequest.getUserId());
            Film film = filmRepository.findById(updateReviewRequest.getFilmId());
            Review review = Review.builder()
                    .id(updateReviewRequest.getId())
                    .user(user)
                    .film(film)
                    .reviewDate(LocalDate.now())
                    .review(updateReviewRequest.getReview())
                    .build();
            reviewRepository.update(review);
            return getReviewResponse(review);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Data already exists");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReviewResponse> findReviewAll() {
        return reviewRepository.findAll().stream().map(this::getReviewResponse).collect(Collectors.toList());
    }

    @Override
    public Review findReviewById(String id) {
        return getOrElseThrow(id);
    }

    @Override
    public List<Review> findReviewByName(String name) {
        return getOrElseThrowName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReviewResponse> findReviewByNameResponse(String name) {
        List<Review> orElseThrow = getOrElseThrowName(name);
        return orElseThrow.stream().map(this::getReviewResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ReviewResponse findReviewByIdResponse(String id) {
        Review orElseThrow = getOrElseThrow(id);
        return getReviewResponse(orElseThrow);
    }
    @Transactional(readOnly = true)
    private ReviewResponse getReviewResponse(Review orElseThrow) {
        return ReviewResponse.builder()
                .id(orElseThrow.getId())
                .userName(orElseThrow.getUser().getName())
                .filmName(orElseThrow.getFilm().getName())
                .reviewDate(orElseThrow.getReviewDate())
                .review(orElseThrow.getReview())
                .build();
    }

    @Transactional(readOnly = true)
    private Review getOrElseThrow(String id) {
        Review byId = reviewRepository.findById(id);
        if(byId!=null){
            return byId;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with such ID does not exist");
    }
    @Transactional(readOnly = true)
    private List<Review> getOrElseThrowName(String name) {
        return reviewRepository.findByName(name);
    }
}
