package com.myapp.vod_backend.tmdb.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountListsDto {
    private Integer page;
    private Integer totalResults;
    private Integer totalPages;
    private List<ListDetailsDto> results;
}
