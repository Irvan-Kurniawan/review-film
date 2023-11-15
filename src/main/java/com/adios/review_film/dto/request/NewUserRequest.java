package com.adios.review_film.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserRequest {
    @NotBlank(message = "Film Name must not be empty")
    private String name;
    @NotNull(message = "Release Date must not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
}
