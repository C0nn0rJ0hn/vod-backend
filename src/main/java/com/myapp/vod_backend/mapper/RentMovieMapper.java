package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.RentMovie;
import com.myapp.vod_backend.domain.dto.RentMovieDto;
import com.myapp.vod_backend.exception.MovieNotFoundException;
import com.myapp.vod_backend.repository.AccountRepository;
import com.myapp.vod_backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentMovieMapper {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovieRepository movieRepository;

    public RentMovie mapToRentMovie(RentMovieDto rentMovieDto) {
        return new RentMovie(
                rentMovieDto.getId(),
                rentMovieDto.getAccountId() != null ? accountRepository.findById(rentMovieDto.getAccountId()).orElse(null) : null,
                rentMovieDto.getRentDate(),
                rentMovieDto.getExpireDate(),
                rentMovieDto.getMovieId() != null ? movieRepository.findById(rentMovieDto.getMovieId()).orElseThrow(() -> new MovieNotFoundException("Movie not found")) : null
        );
    }

    public RentMovieDto mapToRentMovieDto(RentMovie rentMovie) {
        return new RentMovieDto(
                rentMovie.getId(),
                rentMovie.getAccount() != null ? rentMovie.getAccount().getId() : null,
                rentMovie.getRentDate(),
                rentMovie.getExpireDate(),
                rentMovie.getMovie() != null ? rentMovie.getMovie().getId() : null
        );
    }

    public List<RentMovieDto> mapToListRentMovieDto(List<RentMovie> rentMovies) {
        return rentMovies.stream().map(this::mapToRentMovieDto).collect(Collectors.toList());
    }
}
