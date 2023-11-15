package com.adios.review_film.dto.controller;

import com.adios.review_film.dto.request.NewFilmRequest;
import com.adios.review_film.dto.request.UpdateFilmRequest;
import com.adios.review_film.dto.response.CommonResponse;
import com.adios.review_film.dto.response.FilmResponse;
import com.adios.review_film.service.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping(value = "/api/films")
    public ResponseEntity<?> createFilm(@RequestBody NewFilmRequest newFilmRequest){
        FilmResponse film = filmService.createFilm(newFilmRequest);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Create new Film successful")
                .statusCode(HttpStatus.CREATED.value())
                .data(film)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
    @GetMapping(value = "/api/films/{id}")
    public ResponseEntity<?> findFilmByID(@PathVariable String id){
        FilmResponse filmByIdResponse = filmService.findFilmByIdResponse(id);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Find Film by ID successful")
                .statusCode(HttpStatus.OK.value())
                .data(filmByIdResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @GetMapping(value = "/api/films/search")
    public ResponseEntity<?> findFilmByName(@RequestParam String name) {
        List<FilmResponse> filmByIdResponse = filmService.findFilmByNameResponse(name);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Find Film by Name successful")
                .statusCode(HttpStatus.OK.value())
                .data(filmByIdResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @GetMapping(value = "/api/films")
    public ResponseEntity<?> findFilmAll(){
            List<FilmResponse> filmServiceAll = filmService.findFilmAll();

            CommonResponse<List<FilmResponse>> commonResponse = CommonResponse.<List<FilmResponse>>builder()
                    .message("All film get")
                    .statusCode(HttpStatus.OK.value())
                    .data(filmServiceAll)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @PutMapping(value = "/api/films")
    public ResponseEntity<?> updateFilm(@RequestBody UpdateFilmRequest updateFilmRequest){
        FilmResponse filmResponse = filmService.updateFilm(updateFilmRequest);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Update Film Successful")
                .statusCode(HttpStatus.OK.value())
                .data(filmResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @DeleteMapping(value = "/api/films/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable String id){
        filmService.deleteFilm(id);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Delete Film successful")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
