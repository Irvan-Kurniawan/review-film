package com.adios.review_film.repository.impl;

import com.adios.review_film.entity.Review;
import com.adios.review_film.entity.User;
import com.adios.review_film.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final EntityManager entityManager;

    @Override
    public Review saveAndFlush(Review review) {
        entityManager.persist(review);
        entityManager.flush();
        return review;
    }

    @Override
    public Review update(Review review) {
        Review reviewFound = entityManager.find(Review.class, review.getId());
        reviewFound.setUser(review.getUser());
        reviewFound.setFilm(review.getFilm());
        reviewFound.setReviewDate(review.getReviewDate());
        reviewFound.setReview(review.getReview());
        entityManager.persist(reviewFound);
        entityManager.flush();
        return review;
    }


    @Override
    public void delete(Review review) {
        review = entityManager.find(Review.class, review.getId());
        entityManager.remove(review);
    }

    @Override
    public List<Review> findAll() {
        TypedQuery<Review> query = entityManager.createQuery("select f from Review f", Review.class);
        return query.getResultList();
    }

    @Override
    public Review findById(String id) {
        Review review = entityManager.find(Review.class, id);
        return review;
    }

    @Override
    public List<Review> findByName(String name) {
        TypedQuery<User> queryUser = entityManager.createQuery("select f from User f where lower(f.name) LIKE ?1", User.class);
        queryUser.setParameter(1, "%"+name.toLowerCase()+"%");
        List<User> userList = queryUser.getResultList();

        TypedQuery<Review> queryReview = entityManager.createQuery("select f from Review f where f.user = ?1", Review.class);
        List<Review> reviewList = new ArrayList<>();
        for (User user : userList) {
            queryReview.setParameter(1, user);
            reviewList.addAll(queryReview.getResultList());
        }

        return reviewList;
    }
}
