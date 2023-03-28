package com.digdes.school.exceptions;

public class InvalidParameterInTable extends Exception{
    public InvalidParameterInTable(String column){
        super("[ERROR] " + column + " column is not in the table");
    }
}
