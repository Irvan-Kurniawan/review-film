package com.adios.review_film.repository.impl;

import com.adios.review_film.entity.Film;
import com.adios.review_film.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Locale;

@Repository
@RequiredArgsConstructor
public class FilmRepositoryImpl implements FilmRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Film saveAndFlush(Film film) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(film);
        entityManager.flush();
        entityTransaction.commit();
        entityManager.close();
        return film;
    }

    @Override
    public Film update(Film film) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        film = entityManager.find(Film.class, film.getId());
        entityManager.persist(film);
        entityManager.flush();
        entityTransaction.commit();
        entityManager.close();
        return film;
    }


    @Override
    public void delete(Film film) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        film = entityManager.find(Film.class, film.getId());
        entityManager.remove(film);
        transaction.commit();
        entityManager.close();
    }

    @Override
    public List<Film> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Film> query = entityManager.createQuery("select f from Film f", Film.class);
        entityManager.close();
        return query.getResultList();
    }

    @Override
    public Film findById(String id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Film film = entityManager.find(Film.class, id);
        entityManager.close();
        return film;
    }

    @Override
    public List<Film> findByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Film> query = entityManager.createQuery("select f from Film f where lower(f.name) LIKE ?1", Film.class);
        query.setParameter(1, "%"+name.toLowerCase()+"%");
        entityManager.close();
        return query.getResultList();
    }
}
