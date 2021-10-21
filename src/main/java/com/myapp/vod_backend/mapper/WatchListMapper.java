package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.domain.WatchList;
import com.myapp.vod_backend.domain.dto.WatchListDto;
import com.myapp.vod_backend.exception.MovieNotFoundException;
import com.myapp.vod_backend.exception.TvShowNotFoundException;
import com.myapp.vod_backend.repository.AccountRepository;
import com.myapp.vod_backend.repository.MovieRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchListMapper {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TvShowRepository tvShowRepository;

    public WatchList mapToWatchList(WatchListDto watchListDto) {
        return new WatchList(
                watchListDto.getId(),
                watchListDto.getMoviesId().stream().map(movieRepository::findById)
                        .map(m -> m.orElseThrow(() -> new MovieNotFoundException("Movie not found in database")))
                .collect(Collectors.toList()),
                watchListDto.getTvShowsId().stream().map(tvShowRepository::findById)
                .map(t -> t.orElseThrow(() -> new TvShowNotFoundException("Tv show not found in database")))
                .collect(Collectors.toList())
        );
    }

    public WatchListDto mapToWatchListDto(WatchList watchList) {
        return new WatchListDto(
                watchList.getId(),
                watchList.getMovies().stream().map(Movie::getId).collect(Collectors.toList()),
                watchList.getTvShows().stream().map(TvShow::getId).collect(Collectors.toList())
        );
    }

    public List<WatchListDto> mapToListWatchListDto(List<WatchList> watchLists) {
        return watchLists.stream().map(this::mapToWatchListDto).collect(Collectors.toList());
    }
}
