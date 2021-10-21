package com.myapp.vod_backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuyDto {
    private Integer id;
    private List<Integer> moviesId = new ArrayList<>();
    private List<Integer> tvShowsId = new ArrayList<>();
}
