package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.domain.dto.RentTvShowDto;
import com.myapp.vod_backend.exception.RentTvShowNotFoundException;
import com.myapp.vod_backend.mapper.RentTvShowMapper;
import com.myapp.vod_backend.service.RentTvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class RentTvShowController {

    @Autowired
    private RentTvShowMapper mapper;

    @Autowired
    private RentTvShowService service;

    @GetMapping(value = "/rentTvShows")
    public List<RentTvShowDto> getAllRentsForTvShows() {
        return mapper.mapToListRentTvShowDto(service.getAllRentsForTvShows());
    }

    @GetMapping(value = "/rentTvShows/{rentTvShowId}")
    public RentTvShowDto getRentForTvShow(@PathVariable Integer rentTvShowId) {
        return mapper.mapToRentTvShowDto(service.getRentForTvShow(rentTvShowId).orElseThrow(() -> new RentTvShowNotFoundException("Rent tv show id not found")));
    }

    @DeleteMapping(value = "/rentTvShows/{rentTvShowId}")
    public void deleteRentTvShow(@PathVariable Integer rentTvShowId) {
        service.deleteRentTvShow(rentTvShowId);
    }

    @PostMapping(value = "/rentTvShows", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveRentTvShow(@RequestBody RentTvShowDto rentTvShowDto) {
        service.saveRentTvShow(mapper.mapToRentTvShow(rentTvShowDto));
    }

    @PutMapping(value = "/rentTvShows")
    public RentTvShowDto rentTvShow(@RequestBody RentTvShowDto rentTvShowDto) {
        return mapper.mapToRentTvShowDto(service.rentTvShow(mapper.mapToRentTvShow(rentTvShowDto)));
    }

}
