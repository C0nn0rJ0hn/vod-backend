package com.myapp.vod_backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentMovieDto {
    private Integer id;
    private Integer accountId;
    private String rentDate;
    private String expireDate;
    private Integer movieId;

    public RentMovieDto(Integer accountId, Integer movieId) {
        this.accountId = accountId;
        this.movieId = movieId;
    }
}
