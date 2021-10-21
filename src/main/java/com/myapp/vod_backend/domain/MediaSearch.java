package com.myapp.vod_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaSearch {
    private String title;
    private String year;
    private String released;
    private String plot;
    private String poster;
    private String imdbRating;
    private String imdbId;
}
