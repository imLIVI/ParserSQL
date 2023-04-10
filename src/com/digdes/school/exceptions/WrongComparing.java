package com.digdes.school.exceptions;

public class WrongComparing extends Exception {
    public WrongComparing() {
        super("[ERROR] " + "incorrect type in comparison");
    }
}
