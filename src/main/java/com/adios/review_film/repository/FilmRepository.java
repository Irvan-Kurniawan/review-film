package com.adios.review_film.repository;

import com.adios.review_film.entity.Film;

import java.util.List;

public interface FilmRepository{
    Film saveAndFlush(Film film);
    Film update(Film film);
    void delete(Film film);
    List<Film> findAll();
    Film findById(String s);
    List<Film> findByName(String name);

}
