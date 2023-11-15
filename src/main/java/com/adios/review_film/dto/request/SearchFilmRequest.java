package com.adios.review_film.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchFilmRequest {
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;
    private String name;
}
