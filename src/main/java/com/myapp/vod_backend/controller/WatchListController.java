package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.domain.WatchList;
import com.myapp.vod_backend.domain.dto.WatchListDto;
import com.myapp.vod_backend.exception.WatchListNotFoundException;
import com.myapp.vod_backend.mapper.MovieMapper;
import com.myapp.vod_backend.mapper.TvShowMapper;
import com.myapp.vod_backend.mapper.WatchListMapper;
import com.myapp.vod_backend.service.WatchListService;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class WatchListController {

    @Autowired
    private WatchListService service;

    @Autowired
    private WatchListMapper mapper;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private TvShowMapper tvShowMapper;

    @GetMapping(value = "/watchlists")
    public List<WatchListDto> getAllWatchLists() {
        return mapper.mapToListWatchListDto(service.getWatchLists());
    }

    @GetMapping(value = "/watchlists/{watchlistId}")
    public WatchListDto getWatchList(@PathVariable Integer watchlistId) throws WatchListNotFoundException{
        return mapper.mapToWatchListDto(service.getWatchList(watchlistId).orElseThrow(() -> new WatchListNotFoundException("Watchlist not found")));
    }

    @PostMapping(value = "/watchlists", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveWatchlist(@RequestBody WatchListDto watchListDto) {
        service.save(mapper.mapToWatchList(watchListDto));
    }

    @DeleteMapping(value = "/watchlists/{watchlistId}")
    public void deleteWatchList(@PathVariable Integer watchlistId) {
        service.deleteWatchList(watchlistId);
    }

    @GetMapping(value = "/watchlists/{watchlistId}/movies")
    public List<MovieDto> getAllMoviesFromWatchList(@PathVariable Integer watchlistId) {
        return movieMapper.mapToListMovieDto(service.getAllMoviesInWatchlist(watchlistId));
    }

    @GetMapping(value = "/watchlists/{watchlistId}/tvShows")
    public List<TvShowDto> getAllTvShowsFromWatchList(@PathVariable Integer watchlistId) {
        return tvShowMapper.mapToListTvShowDto(service.getAllTvShowsInWatchList(watchlistId));
    }

    @PutMapping(value = "/watchlists/{watchlistId}/addMovie")
    public void addMovieToWatchlist(@PathVariable Integer watchlistId, @RequestParam Integer movieId) throws WatchListNotFoundException {
        service.addMovieToWatchList(movieId, watchlistId);
    }

    @PutMapping(value = "/watchlists/{watchlistId}/addTvShow")
    public void addTvShowToWatchList(@PathVariable Integer watchlistId, @RequestParam Integer tvShowId) throws WatchListNotFoundException {
        service.addTvShowToWatchList(tvShowId, watchlistId);
    }

    @DeleteMapping(value = "/watchlists/{watchlistId}/removeMovie")
    public void removeMovieFromWatchList(@PathVariable Integer watchlistId, @RequestParam Integer movieId) throws WatchListNotFoundException {
        service.deleteMovieFromWatchList(movieId, watchlistId);
    }

    @DeleteMapping(value = "/watchlists/{watchlistId}/removeTvShow")
    public void removeTvShowFromWatchList(@PathVariable Integer watchlistId, @RequestParam Integer tvShowId) throws WatchListNotFoundException {
        service.deleteTvShowFromWatchList(tvShowId, watchlistId);
    }
}
