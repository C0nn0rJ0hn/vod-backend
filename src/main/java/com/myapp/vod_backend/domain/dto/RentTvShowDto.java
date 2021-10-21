package com.myapp.vod_backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentTvShowDto {
    private Integer id;
    private Integer accountId;
    private String rentDate;
    private String expireDate;
    private Integer tvShowId;

    public RentTvShowDto(Integer accountId, Integer tvShowId) {
        this.accountId = accountId;
        this.tvShowId = tvShowId;
    }
}
