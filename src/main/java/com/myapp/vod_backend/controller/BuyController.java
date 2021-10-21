package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.domain.dto.BuyDto;
import com.myapp.vod_backend.exception.BuyNotFoundException;
import com.myapp.vod_backend.mapper.BuyMapper;
import com.myapp.vod_backend.mapper.MovieMapper;
import com.myapp.vod_backend.mapper.TvShowMapper;
import com.myapp.vod_backend.service.BuyService;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class BuyController {

    @Autowired
    private BuyMapper mapper;

    @Autowired
    private BuyService service;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private TvShowMapper tvShowMapper;

    @GetMapping(value = "/buys")
    public List<BuyDto> getBuys() {
        return mapper.mapToListBuyDto(service.getBuys());
    }

    @GetMapping(value = "/buys/{buyId}")
    public BuyDto getBuy(@PathVariable Integer buyId) throws BuyNotFoundException {
        return mapper.mapToBuyDto(service.getBuy(buyId).orElseThrow(() -> new BuyNotFoundException("Buy not found")));
    }

    @PutMapping(value = "/buys")
    public BuyDto updateBuy(@RequestBody BuyDto buyDto) {
        return mapper.mapToBuyDto(service.save(mapper.mapToBuy(buyDto)));
    }

    @PostMapping(value = "/buys", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveBuy(@RequestBody BuyDto buyDto) {
        service.save(mapper.mapToBuy(buyDto));
    }

    @DeleteMapping(value = "/buys/{buyId}")
    public void deleteBuy(@PathVariable Integer buyId) {
        service.deleteBuy(buyId);
    }

    @PutMapping(value = "/buys/{buyId}/buyMovie")
    public BuyDto buyMovie(@PathVariable Integer buyId, @RequestParam Integer movieId) throws BuyNotFoundException {
        return mapper.mapToBuyDto(service.buyMovie(movieId, buyId));
    }

    @PutMapping(value = "/buys/{buyId}/buyTvShow")
    public BuyDto buyTvShow(@PathVariable Integer buyId, @RequestParam Integer tvShowId) throws BuyNotFoundException {
        return mapper.mapToBuyDto(service.buyTvShow(tvShowId, buyId));
    }

    @GetMapping(value = "/buys/{buyId}/movies")
    public List<MovieDto> getBoughtMovies(@PathVariable Integer buyId) throws BuyNotFoundException {
        return movieMapper.mapToListMovieDto(service.getBoughtMovies(buyId));
    }

    @GetMapping(value = "/buys/{buyId}/tvShows")
    public List<TvShowDto> getBoughtTvShows(@PathVariable Integer buyId) throws BuyNotFoundException {
        return tvShowMapper.mapToListTvShowDto(service.getBoughtTvShows(buyId));
    }

}
