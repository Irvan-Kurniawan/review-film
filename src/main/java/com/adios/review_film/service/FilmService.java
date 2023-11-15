package com.adios.review_film.service;

import com.adios.review_film.dto.request.NewFilmRequest;
import com.adios.review_film.dto.request.SearchFilmRequest;
import com.adios.review_film.dto.request.UpdateFilmRequest;
import com.adios.review_film.dto.response.FilmResponse;
import com.adios.review_film.entity.Film;
import org.springframework.data.domain.Page;

import java.util.List;


public interface FilmService {
    FilmResponse createFilm(NewFilmRequest newFilmRequest);
    void deleteFilm(String id);
    FilmResponse updateFilm(UpdateFilmRequest updateFilmRequest);
    List<FilmResponse> findFilmAll();
    Film findFilmById(String id);
    List<Film> findFilmByName(String name);
    List<FilmResponse> findFilmByNameResponse(String name);
    FilmResponse findFilmByIdResponse(String id);

}
