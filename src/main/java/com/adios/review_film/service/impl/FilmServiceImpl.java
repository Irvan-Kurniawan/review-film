package com.adios.review_film.service.impl;

import com.adios.review_film.dto.request.NewFilmRequest;
import com.adios.review_film.dto.request.UpdateFilmRequest;
import com.adios.review_film.dto.response.FilmResponse;
import com.adios.review_film.entity.Film;
import com.adios.review_film.repository.FilmRepository;
import com.adios.review_film.service.FilmService;
import com.adios.review_film.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FilmResponse createFilm(NewFilmRequest newFilmRequest) {
        validationUtil.validate(newFilmRequest);
        Film film = Film.builder()
                .name(newFilmRequest.getName())
                .releaseDate(newFilmRequest.getReleaseDate())
                .build();
        filmRepository.saveAndFlush(film);
        return getFilmResponse(film);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFilm(String id) {
        Film orElseThrow = getOrElseThrow(id);
        filmRepository.delete(orElseThrow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FilmResponse updateFilm(UpdateFilmRequest updateFilmRequest) {
        validationUtil.validate(updateFilmRequest);
        try {
            getOrElseThrow(updateFilmRequest.getId());
            Film film = Film.builder()
                    .id(updateFilmRequest.getId())
                    .name(updateFilmRequest.getName())
                    .releaseDate(updateFilmRequest.getReleaseDate())
                    .build();
            filmRepository.update(film);
            return getFilmResponse(film);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Data already exists");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<FilmResponse> findFilmAll() {
        return filmRepository.findAll().stream().map(this::getFilmResponse).collect(Collectors.toList());
    }

    @Override
    public Film findFilmById(String id) {
        return getOrElseThrow(id);
    }

    @Override
    public List<Film> findFilmByName(String name) {
        return getOrElseThrowName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FilmResponse> findFilmByNameResponse(String name) {
        List<Film> orElseThrow = getOrElseThrowName(name);
        return orElseThrow.stream().map(this::getFilmResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public FilmResponse findFilmByIdResponse(String id) {
        Film orElseThrow = getOrElseThrow(id);
        return getFilmResponse(orElseThrow);
    }
    @Transactional(readOnly = true)
    private FilmResponse getFilmResponse(Film orElseThrow) {
        return FilmResponse.builder()
                .id(orElseThrow.getId())
                .name(orElseThrow.getName())
                .releaseDate(orElseThrow.getReleaseDate())
                .build();
    }

    @Transactional(readOnly = true)
    private Film getOrElseThrow(String id) {
        Film byId = filmRepository.findById(id);
        if(byId!=null){
            return byId;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film with such ID does not exist");
    }
    @Transactional(readOnly = true)
    private List<Film> getOrElseThrowName(String name) {
        return filmRepository.findByName(name);
    }
}
