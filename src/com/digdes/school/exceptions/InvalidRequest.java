package com.digdes.school.exceptions;

public class InvalidRequest extends RuntimeException {
    public InvalidRequest(String ex) {
        super(ex);
    }
}
