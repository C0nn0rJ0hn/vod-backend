package com.myapp.vod_backend.tmdb.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TMDBConfig {
    @Value("${tmdb.api.endpoint}")
    private String tmdbApiEndpoint;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    @Value("${tmdb.api.username}")
    private String tmdbUsername;

    @Value("${tmdb.api.password}")
    private String tmdbPassword;

    @Value("${tmdb.api.accountId}")
    private Integer accountId;

    @Value("${tmdb.api.sessionId}")
    private String sessionId;
}
