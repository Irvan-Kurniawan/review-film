package com.adios.review_film.repository;

import com.adios.review_film.entity.Review;
import com.adios.review_film.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository {
    Review saveAndFlush(Review review);
    Review update(Review review);
    void delete(Review review);
    List<Review> findAll();
    Review findById(String id);
    List<Review> findByName(String name);
}
