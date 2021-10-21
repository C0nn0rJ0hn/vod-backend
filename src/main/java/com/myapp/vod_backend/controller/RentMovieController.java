package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.domain.dto.RentMovieDto;
import com.myapp.vod_backend.exception.RentMovieNotFoundException;
import com.myapp.vod_backend.mapper.RentMovieMapper;
import com.myapp.vod_backend.service.RentMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class RentMovieController {

    @Autowired
    private RentMovieService service;

    @Autowired
    private RentMovieMapper mapper;

    @GetMapping(value = "/rentMovies")
    public List<RentMovieDto> getRentedMovies() {
        return mapper.mapToListRentMovieDto(service.getRentedMovies());
    }

    @GetMapping(value = "/rentMovies/{rentMovieId}")
    public RentMovieDto getRentMovie(@PathVariable Integer rentMovieId) {
        return mapper.mapToRentMovieDto(service.getRentedMovie(rentMovieId).orElseThrow(() -> new RentMovieNotFoundException("Rent movie not found")));
    }

    @PostMapping(value = "/rentMovies", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveRentMovie(@RequestBody RentMovieDto rentMovieDto) {
        service.saveRentMovie(mapper.mapToRentMovie(rentMovieDto));
    }

    @DeleteMapping(value = "/rentMovies/{rentMovieId}")
    public void deleteRentMovie(@PathVariable Integer rentMovieId) {
        service.deleteRentedMovie(rentMovieId);
    }

    @PutMapping(value = "/rentMovies")
    public RentMovieDto rentMovie(@RequestBody RentMovieDto rentMovieDto) {
        return mapper.mapToRentMovieDto(service.rentMovie(mapper.mapToRentMovie(rentMovieDto)));
    }

}
