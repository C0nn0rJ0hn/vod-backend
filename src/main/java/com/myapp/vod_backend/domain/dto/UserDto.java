package com.myapp.vod_backend.domain.dto;

import com.myapp.vod_backend.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private String lastname;
    private String birthDate;
    private Gender gender;
}
