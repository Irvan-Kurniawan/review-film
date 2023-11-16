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
    private final EntityManager entityManager;

    @Override
    public Film saveAndFlush(Film film) {
        entityManager.persist(film);
        entityManager.flush();
        return film;
    }

    @Override
    public Film update(Film film) {
        Film filmFound = entityManager.find(Film.class, film.getId());
        filmFound.setName(film.getName());
        filmFound.setReleaseDate(film.getReleaseDate());
        entityManager.persist(filmFound);
        entityManager.flush();
        return film;
    }


    @Override
    public void delete(Film film) {
        film = entityManager.find(Film.class, film.getId());
        entityManager.remove(film);
    }

    @Override
    public List<Film> findAll() {
        TypedQuery<Film> query = entityManager.createQuery("select f from Film f", Film.class);
        return query.getResultList();
    }

    @Override
    public Film findById(String id) {
        Film film = entityManager.find(Film.class, id);
        return film;
    }

    @Override
    public List<Film> findByName(String name) {
        TypedQuery<Film> query = entityManager.createQuery("select f from Film f where lower(f.name) LIKE ?1", Film.class);
        query.setParameter(1, "%"+name.toLowerCase()+"%");
        return query.getResultList();
    }
}
