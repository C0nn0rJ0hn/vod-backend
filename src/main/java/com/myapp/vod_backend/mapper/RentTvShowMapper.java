package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.RentTvShow;
import com.myapp.vod_backend.domain.dto.RentTvShowDto;
import com.myapp.vod_backend.exception.TvShowNotFoundException;
import com.myapp.vod_backend.repository.AccountRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentTvShowMapper {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TvShowRepository tvShowRepository;

    public RentTvShow mapToRentTvShow(RentTvShowDto rentTvShowDto) {
        return new RentTvShow(
                rentTvShowDto.getId(),
                rentTvShowDto.getAccountId() != null ? accountRepository.findById(rentTvShowDto.getAccountId()).orElse(null) : null,
                rentTvShowDto.getRentDate(),
                rentTvShowDto.getExpireDate(),
                rentTvShowDto.getTvShowId() != null ? tvShowRepository.findById(rentTvShowDto.getTvShowId())
                        .orElseThrow(() -> new TvShowNotFoundException("Tv show not found")) : null
        );
    }

    public RentTvShowDto mapToRentTvShowDto(RentTvShow rentTvShow) {
        return new RentTvShowDto(
                rentTvShow.getId(),
                rentTvShow.getAccount() != null ? rentTvShow.getAccount().getId() : null,
                rentTvShow.getRentDate(),
                rentTvShow.getExpireDate(),
                rentTvShow.getTvShow() != null ? rentTvShow.getTvShow().getId() : null
        );
    }

    public List<RentTvShowDto> mapToListRentTvShowDto(List<RentTvShow> rentTvShows) {
        return rentTvShows.stream().map(this::mapToRentTvShowDto).collect(Collectors.toList());
    }
}
