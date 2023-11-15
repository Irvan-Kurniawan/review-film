package com.adios.review_film.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewFilmRequest {
    @NotBlank(message = "Film Name must not be empty")
    private String name;
    @NotBlank(message = "Release Date must not be empty")
    private LocalDateTime releaseDate;
}
