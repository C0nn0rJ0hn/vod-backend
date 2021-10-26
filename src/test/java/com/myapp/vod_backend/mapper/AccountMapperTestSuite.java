package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.domain.dto.AccountDto;
import com.myapp.vod_backend.repository.BuyRepository;
import com.myapp.vod_backend.repository.UserRepository;
import com.myapp.vod_backend.repository.WatchListRepository;
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
import static org.mockito.Mockito.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountMapperTestSuite {

    @Autowired
    private AccountMapper mapper;

    @MockBean
    private WatchListRepository watchListRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BuyRepository buyRepository;

    @Test
    public void shouldMapToAccount() {
        //Given
        WatchList watchList = new WatchList(10, new ArrayList<>(), new ArrayList<>());
        User user = new User(12, "Name", "Lastname", "Birth Date", Gender.MALE);
        Buy buy = new Buy(13, new ArrayList<>(), new ArrayList<>());

        AccountDto accountDto = new AccountDto(1, "Created At", "Updated at", "Password",
                "Email", "Country", "Language", Role.USER, false, user.getId(), watchList.getId(),
                buy.getId(), new ArrayList<>(), new ArrayList<>());

        when(watchListRepository.findById(any())).thenReturn(Optional.of(watchList));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(buyRepository.findById(any())).thenReturn(Optional.of(buy));

        //When
        Account result = mapper.mapToAccount(accountDto);

        //Then
        assertEquals(1, result.getId());
        assertEquals("Password", result.getPassword());
        assertEquals(10, result.getWatchList().getId());
        assertEquals(12, result.getUser().getId());
        assertEquals(13, result.getBuy().getId());
    }

    @Test
    public void shouldMapToAccountDto() {
        //Given
        WatchList watchList = new WatchList(10, new ArrayList<>(), new ArrayList<>());
        User user = new User(12, "Name", "Lastname", "Birth Date", Gender.MALE);
        Buy buy = new Buy(13, new ArrayList<>(), new ArrayList<>());

        Account account = new Account(1, "Created At", "Updated at", "Password",
                "Email", "Country", "Language", Role.USER, false, user, watchList,
                buy, new ArrayList<>(), new ArrayList<>());

        //When
        AccountDto result = mapper.mapToAccountDto(account);
        System.out.println(result);

        //Then
        assertEquals(1, result.getId());
        assertEquals("Email", result.getEmail());
        assertEquals(10, result.getWatchlistId());
        assertEquals(12, result.getUserId());
        assertEquals(13, result.getBuyId());
    }

    @Test
    public void shouldMapToListAccountDto() {
        //Given
        WatchList watchList = new WatchList(10, new ArrayList<>(), new ArrayList<>());
        User user = new User(12, "Name", "Lastname", "Birth Date", Gender.MALE);
        Buy buy = new Buy(13, new ArrayList<>(), new ArrayList<>());

        List<Account> accounts = List.of(new Account(1, "Created At", "Updated at", "Password",
                "Email", "Country", "Language", Role.USER, false, user, watchList,
                buy, new ArrayList<>(), new ArrayList<>()));

        //When
        List<AccountDto> result = mapper.mapToListAccountDto(accounts);


        //Then
        assertEquals(1, result.size());
        assertEquals(12, result.get(0).getUserId());
        assertEquals(Role.USER, result.get(0).getRole());
    }
}
