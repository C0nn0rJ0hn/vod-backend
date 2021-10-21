package com.myapp.vod_backend.exception;

public class TvShowNotFoundException extends RuntimeException {
    public TvShowNotFoundException(String message) {
        super(message);
    }
}
