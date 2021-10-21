package com.myapp.vod_backend.domain.dto;

import com.myapp.vod_backend.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Integer id;
    private String createdAt;
    private String updatedAt;
    private String password;
    private String email;
    private String country;
    private String language;
    private Role role;
    private boolean loggedIn;
    private Integer userId;
    private Integer watchlistId;
    private Integer buyId;
    private List<Integer> rentMoviesId = new ArrayList<>();
    private List<Integer> rentTvShowsId = new ArrayList<>();

    public AccountDto(String password, String email, String country, String language) {
        this.password = password;
        this.email = email;
        this.country = country;
        this.language = language;
    }
}
