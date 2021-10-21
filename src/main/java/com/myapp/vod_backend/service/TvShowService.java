package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TvShowService {

    @Autowired
    private TvShowRepository repository;

    public TvShow save(final TvShow tvShow) {
        return repository.save(tvShow);
    }

    public List<TvShow> getTvShows() {
        return repository.findAll();
    }

    public Optional<TvShow> getTvShowById(final Integer tvShowId) {
        return repository.findById(tvShowId);
    }

    public void deleteTvShowById(final Integer tvShowId) {
        repository.deleteById(tvShowId);
    }

    public List<TvShow> getTvShowsWithAverageAbove(Double voteAverage) {
        List<TvShow> result = new ArrayList<>();
        List<TvShow> tvShows = repository.findAll();

        for (TvShow tvShow : tvShows) {
            if (tvShow.getVoteAverage() >= voteAverage) {
                result.add(tvShow);
            }
        }
        return result;
    }

    public List<TvShow> getTvShowsByKeyword(String keyword) {
        List<TvShow> shows = new ArrayList<>();
        List<TvShow> tvShows = repository.findAll();

        for (TvShow tvShow : tvShows) {
            if (tvShow.getName().toLowerCase().contains(keyword.toLowerCase())) {
                shows.add(tvShow);
            }
        }
        return shows;
    }


}
