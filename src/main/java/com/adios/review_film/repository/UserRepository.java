package com.adios.review_film.repository;

import com.adios.review_film.entity.User;

import java.util.List;

public interface UserRepository {
    User saveAndFlush(User user);
    User update(User user);
    void delete(User user);
    List<User> findAll();
    User findById(String s);
    List<User> findByName(String name);
}
