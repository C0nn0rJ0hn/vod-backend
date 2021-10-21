package com.myapp.vod_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "gender", columnDefinition = "enum('MALE', 'FEMALE')")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public User(String name, String lastname, String birthDate, Gender gender) {
        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
