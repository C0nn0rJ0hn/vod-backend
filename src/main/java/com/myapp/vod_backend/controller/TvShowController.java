package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.exception.TvShowNotFoundException;
import com.myapp.vod_backend.mapper.TvShowMapper;
import com.myapp.vod_backend.service.TvShowService;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class TvShowController {

    @Autowired
    private TvShowService service;

    @Autowired
    private TvShowMapper mapper;

    @GetMapping(value = "/tvShows")
    public List<TvShowDto> getTvShows() {
        return mapper.mapToListTvShowDto(service.getTvShows());
    }

    @GetMapping(value = "/tvShows/{tvShowId}")
    public TvShowDto getTvShow(@PathVariable Integer tvShowId) {
        return mapper.mapToTvShowDto(service.getTvShowById(tvShowId).orElseThrow(() -> new TvShowNotFoundException("Tv show not found")));
    }

    @GetMapping(value = "/tvShows/searchByRatings")
    public List<TvShowDto> getTvShowsWithAverageAbove(@RequestParam Double average) {
        return mapper.mapToListTvShowDto(service.getTvShowsWithAverageAbove(average));
    }

    @GetMapping(value = "/tvShows/searchByKeyword")
    public List<TvShowDto> getTvShowsByKeyword(@RequestParam String keyword) {
        return mapper.mapToListTvShowDto(service.getTvShowsByKeyword(keyword));
    }
}
