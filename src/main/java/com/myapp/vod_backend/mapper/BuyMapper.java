package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Buy;
import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.domain.dto.BuyDto;
import com.myapp.vod_backend.exception.MovieNotFoundException;
import com.myapp.vod_backend.exception.TvShowNotFoundException;
import com.myapp.vod_backend.repository.MovieRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyMapper {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TvShowRepository tvShowRepository;

    public Buy mapToBuy(BuyDto buyDto) {
        return new Buy(
                buyDto.getId(),
                buyDto.getMoviesId().stream().map(movieRepository::findById)
                        .map(m -> m.orElseThrow(() -> new MovieNotFoundException("Movie not found"))).collect(Collectors.toList()),
                buyDto.getTvShowsId().stream().map(tvShowRepository::findById)
                        .map(t -> t.orElseThrow(() -> new TvShowNotFoundException("Tv show not found"))).collect(Collectors.toList())
        );
    }

    public BuyDto mapToBuyDto(Buy buy) {
        return new BuyDto(
                buy.getId(),
                buy.getMovies().stream().map(Movie::getId).collect(Collectors.toList()),
                buy.getTvShows().stream().map(TvShow::getId).collect(Collectors.toList())
        );
    }

    public List<BuyDto> mapToListBuyDto(List<Buy> buys) {
        return buys.stream().map(this::mapToBuyDto).collect(Collectors.toList());
    }
}
