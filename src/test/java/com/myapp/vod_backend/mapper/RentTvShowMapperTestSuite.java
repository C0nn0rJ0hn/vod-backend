package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.domain.dto.RentTvShowDto;
import com.myapp.vod_backend.repository.AccountRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class RentTvShowMapperTestSuite {

    @Autowired
    private RentTvShowMapper mapper;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TvShowRepository tvShowRepository;

    private Account account;
    private TvShow tvShow;

    @BeforeEach
    public void beforeEachTest() {
        WatchList watchList = new WatchList(10, new ArrayList<>(), new ArrayList<>());
        User user = new User(12, "Name", "Lastname", "Birth Date", Gender.MALE);
        Buy buy = new Buy(13, new ArrayList<>(), new ArrayList<>());

        account = new Account(1, "Created At", "Updated at", "Password",
                "Email", "Country", "Language", Role.USER, false, user, watchList,
                buy, new ArrayList<>(), new ArrayList<>());
        tvShow = new TvShow(66, "Daredevil", 500.0, "2016", 8.2, 20000, "Overview Daredevil");
    }

    @Test
    public void shouldMapToRentTvShow() {
        //Given
        RentTvShowDto rentTvShowDto = new RentTvShowDto(7 ,account.getId(), "Rent Date", "Expire Date", tvShow.getId());

        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        when(tvShowRepository.findById(any())).thenReturn(Optional.of(tvShow));

        //When
        RentTvShow result = mapper.mapToRentTvShow(rentTvShowDto);

        //Then
        assertEquals(7, result.getId());
        assertEquals(13, result.getAccount().getBuy().getId());
        assertEquals("Name", result.getAccount().getUser().getName());
        assertEquals("Daredevil", result.getTvShow().getName());
    }

    @Test
    public void shouldMapToRentTvShowDto() {
        //Given
        RentTvShow rentTvShow = new RentTvShow(9 ,account, "Rent Date", "Expire Date", tvShow);

        //When
        RentTvShowDto result = mapper.mapToRentTvShowDto(rentTvShow);

        //Then
        assertEquals(9, result.getId());
        assertEquals(66, result.getTvShowId());
        assertEquals(1, result.getAccountId());
    }

    @Test
    public void shouldMapToListRentTvShowDto() {
        //Given
        List<RentTvShow> rentTvShows = List.of(new RentTvShow(9 ,account, "Rent Date", "Expire Date", tvShow));

        //When
        List<RentTvShowDto> result = mapper.mapToListRentTvShowDto(rentTvShows);

        //Then
        assertEquals(1, result.size());
        assertEquals(9, result.get(0).getId());
        assertEquals("Expire Date", result.get(0).getExpireDate());
    }
}
