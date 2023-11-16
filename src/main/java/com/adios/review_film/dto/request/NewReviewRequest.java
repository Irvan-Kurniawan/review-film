package com.adios.review_film.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewReviewRequest {
    @NotBlank(message = "User ID must not be empty")
    private String userId;
    @NotBlank(message = "Film ID must not be empty")
    private String filmId;
    @NotBlank(message = "Review must not be empty")
    private String review;
}
