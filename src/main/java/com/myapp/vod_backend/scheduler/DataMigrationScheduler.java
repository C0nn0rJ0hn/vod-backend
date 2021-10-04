package com.myapp.vod_backend.scheduler;

import com.myapp.vod_backend.mapper.MovieMapper;
import com.myapp.vod_backend.mapper.TvShowMapper;
import com.myapp.vod_backend.service.MovieService;
import com.myapp.vod_backend.service.TvShowService;
import com.myapp.vod_backend.tmdb.client.TMDBClient;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class DataMigrationScheduler {
    private final TMDBClient tmdbClient;
    private final MovieMapper movieMapper;
    private final MovieService movieService;
    private final TvShowMapper tvShowMapper;
    private final TvShowService tvShowService;

    @Scheduled(cron = "0 2 21 * * *")
    public void updateMovieDb() {
        tmdbClient.getPopularMovies("1");
        for (int i = 1; i <= tmdbClient.getMoviesTotalPages(); i++) {
            tmdbClient.getPopularMovies("" + i);
            for (MovieDto moviedto : tmdbClient.getPopularMovies("" + i)) {
                if (!movieService.getMovies().contains(movieMapper.mapToMovie(moviedto))) {
                    movieService.saveMovie(movieMapper.mapToMovie(moviedto));
                }
            }
        }
    }

    //minimum 15 minutes later
    @Scheduled(cron = "0 20 21 * * *")
    public void updateTvShowDb() {
        tmdbClient.getPopularTvShows("1");
        for (int i = 1; i <= tmdbClient.getTvShowsTotalPages(); i++) {
            tmdbClient.getPopularTvShows("" + i);
            for (TvShowDto tvShowDto : tmdbClient.getPopularTvShows("" + i)) {
                if (!tvShowService.getTvShows().contains(tvShowMapper.mapToTvShow(tvShowDto))) {
                    tvShowService.save(tvShowMapper.mapToTvShow(tvShowDto));
                }
            }
        }
    }
}
