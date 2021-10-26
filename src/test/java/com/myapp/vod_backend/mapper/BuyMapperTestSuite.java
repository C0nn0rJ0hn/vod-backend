package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Buy;
import com.myapp.vod_backend.domain.dto.BuyDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyMapperTestSuite {

    @Autowired
    private BuyMapper mapper;

    @Test
    public void shouldMapToBuy() {
        //Given
        BuyDto buyDto = new BuyDto(5, new ArrayList<>(), new ArrayList<>());

        //When
        Buy result = mapper.mapToBuy(buyDto);

        //Then
        assertEquals(5, result.getId());
        assertEquals(0, result.getMovies().size());
    }

    @Test
    public void shouldMapToBuyDto() {
        //Given
        Buy buy = new Buy(7, new ArrayList<>(), new ArrayList<>());

        //When
        BuyDto result = mapper.mapToBuyDto(buy);

        //Then
        assertEquals(7, result.getId());
        assertEquals(0, result.getTvShowsId().size());
    }

    @Test
    public void shouldMapToListBuyDto() {
        //Given
        List<Buy> buys = List.of(new Buy(7, new ArrayList<>(), new ArrayList<>()));

        //When
        List<BuyDto> result = mapper.mapToListBuyDto(buys);

        //Then
        assertEquals(1, result.size());
        assertEquals(7, result.get(0).getId());
    }
}
