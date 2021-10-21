package com.myapp.vod_backend.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "RENT_TV_SHOWS")

public class RentTvShow {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "rent_date")
    private String rentDate;

    @Column(name = "expire_date")
    private String expireDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tv_show_id")
    private TvShow tvShow;

    public RentTvShow(Account account, TvShow tvShow) {
        this.account = account;
        this.tvShow = tvShow;
    }
}
