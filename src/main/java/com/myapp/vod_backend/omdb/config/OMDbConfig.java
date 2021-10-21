package com.myapp.vod_backend.omdb.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OMDbConfig {

    @Value("${omdb.api.data.endpoint}")
    private String omdbApiDataEndpoint;

    @Value("${omdb.api.poster.endpoint}")
    private String omdbApiPosterEndpoint;

    @Value("${omdb.api.key}")
    private String omdbApiKey;
}
