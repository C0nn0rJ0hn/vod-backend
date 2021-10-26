package com.myapp.vod_backend.service;


import com.myapp.vod_backend.domain.RentMovie;
import com.myapp.vod_backend.repository.RentMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class RentMovieService {

    @Autowired
    private RentMovieRepository rentMovieRepository;

    public List<RentMovie> getRentedMovies() {
        return rentMovieRepository.findAll();
    }

    public Optional<RentMovie> getRentedMovie(final Integer rentMovieId) {
        return rentMovieRepository.findById(rentMovieId);
    }

    public void deleteRentedMovie(final Integer rentMovieId) {
        rentMovieRepository.deleteById(rentMovieId);
    }

    public RentMovie saveRentMovie(final RentMovie rentMovie) {
        return rentMovieRepository.save(rentMovie);
    }

    public RentMovie rentMovie(final RentMovie rentMovie) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        rentMovie.setRentDate(LocalDateTime.now().format(formatter));
        rentMovie.setExpireDate(LocalDateTime.now().plusHours(48).format(formatter));

        return rentMovieRepository.save(rentMovie);
    }
}
