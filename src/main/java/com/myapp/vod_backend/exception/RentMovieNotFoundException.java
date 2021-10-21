package com.myapp.vod_backend.exception;

public class RentMovieNotFoundException extends RuntimeException {
    public RentMovieNotFoundException(String message) {
        super(message);
    }
}
