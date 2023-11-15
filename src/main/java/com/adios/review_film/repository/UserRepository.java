package com.adios.review_film.repository;

import com.adios.review_film.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByName(String name);
}
