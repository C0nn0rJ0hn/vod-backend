package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.omdb.client.OMDbClient;
import com.myapp.vod_backend.omdb.domain.MediaSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/omdb")
public class OMDbController {

    @Autowired
    private OMDbClient client;

    @RequestMapping(value = "/image/{imdb}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable String imdb) {
        byte[] image = client.getPosterForMedia(imdb);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping(value = "/search/{title}")
    public MediaSearchDto getMediaDetails(@PathVariable String title) {
        return client.getMediaDetails(title);
    }

    @GetMapping(value = "/search/poster")
    public String getPoster(@RequestParam String title) {
        return client.getMediaDetails(title).getPoster();
    }

}
