package com.adios.review_film.repository.impl;

import com.adios.review_film.entity.User;
import com.adios.review_film.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;

    @Override
    public User saveAndFlush(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    @Override
    public User update(User user) {
        User userFound = entityManager.find(User.class, user.getId());
        userFound.setName(user.getName());
        entityManager.persist(userFound);
        entityManager.flush();
        return user;
    }


    @Override
    public void delete(User user) {
        user = entityManager.find(User.class, user.getId());
        entityManager.remove(user);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("select f from User f", User.class);

        return query.getResultList();
    }

    @Override
    public User findById(String id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public List<User> findByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("select f from User f where lower(f.name) LIKE ?1", User.class);
        query.setParameter(1, "%"+name.toLowerCase()+"%");
        return query.getResultList();
    }
}
