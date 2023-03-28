package com.digdes.school.exceptions;

public class InvalidRequest extends RuntimeException {
    public InvalidRequest(String request) {
        super("[ERROR] invalid request" + request);
    }
}
