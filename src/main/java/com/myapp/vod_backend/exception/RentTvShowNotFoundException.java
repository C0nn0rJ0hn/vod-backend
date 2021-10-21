package com.myapp.vod_backend.exception;

public class RentTvShowNotFoundException extends RuntimeException {
    public RentTvShowNotFoundException(String message) {
        super(message);
    }
}
