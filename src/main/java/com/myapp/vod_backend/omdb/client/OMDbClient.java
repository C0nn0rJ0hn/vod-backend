package com.myapp.vod_backend.omdb.client;

import com.myapp.vod_backend.exception.OmdbSearchNotFound;
import com.myapp.vod_backend.omdb.config.OMDbConfig;
import com.myapp.vod_backend.omdb.domain.MediaSearchDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OMDbClient {

    private final OMDbConfig omDbConfig;
    private final RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(OMDbClient.class);

    public byte[] getPosterForMedia(String imdbId) {
        URI url = UriComponentsBuilder.fromHttpUrl(omDbConfig.getOmdbApiPosterEndpoint())
                .queryParam("apikey", omDbConfig.getOmdbApiKey())
                .queryParam("i", imdbId)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, byte[].class);
    }


    public MediaSearchDto getMediaDetails(String title) {
        URI url = UriComponentsBuilder.fromHttpUrl(omDbConfig.getOmdbApiDataEndpoint())
                .queryParam("apikey", omDbConfig.getOmdbApiKey())
                .queryParam("t", title)
                .build()
                .encode()
                .toUri();

        try{
            MediaSearchDto apiResponse = restTemplate.getForObject(url, MediaSearchDto.class);
            return Optional.ofNullable(apiResponse).orElseThrow(() -> new OmdbSearchNotFound("No search results"));
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
