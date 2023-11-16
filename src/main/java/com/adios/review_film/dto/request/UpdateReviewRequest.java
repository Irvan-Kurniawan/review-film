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
public class UpdateReviewRequest {
    @NotBlank(message = "Review ID must not be empty")
    private String id;
    @NotBlank(message = "User ID must not be empty")
    private String userId;
    @NotBlank(message = "Film ID must not be empty")
    private String filmId;
    @NotBlank(message = "Review must not be empty")
    private String review;
}
