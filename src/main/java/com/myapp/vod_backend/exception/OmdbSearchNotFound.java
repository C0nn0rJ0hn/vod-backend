package com.myapp.vod_backend.exception;

public class OmdbSearchNotFound extends RuntimeException {
    public OmdbSearchNotFound(String message) {
        super(message);
    }
}
