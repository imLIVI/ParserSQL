package com.digdes.school.comparison;

import com.digdes.school.exceptions.WrongComparing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComparisonFunctions {
    public static boolean checkEquals(Object condVal, Object mapVal) {
        if (mapVal instanceof String && condVal instanceof String) {
            if (mapVal.equals(condVal))
                return true;
        } else if ((mapVal instanceof Long && condVal instanceof Long) ||
                (mapVal instanceof Double && condVal instanceof Double) ||
                (mapVal instanceof Boolean && condVal instanceof Boolean)) {
            if (mapVal == condVal)
                return true;
        }
        return false;
    }

    public static boolean checkNotEquals(Object condVal, Object mapVal) {
        return checkEquals(condVal, mapVal) ? false : true;
    }

    public static boolean moreEquals(Object condVal, Object mapVal) throws WrongComparing {
        if ((mapVal instanceof Long && condVal instanceof Long)) {
            if ((Long) mapVal >= (Long) condVal)
                return true;
        } else if (mapVal instanceof Double && condVal instanceof Double) {
            if ((Double) mapVal >= (Double) condVal)
                return true;
        } else if (mapVal instanceof Double && condVal instanceof Long) {
            if ((Double) mapVal >= (Long) condVal)
                return true;
        } else if (mapVal instanceof Long && condVal instanceof Double) {
            if ((Long) mapVal >= (Double) condVal)
                return true;
        } else if (mapVal instanceof String || condVal instanceof String ||
                mapVal instanceof Boolean || condVal instanceof Boolean)
            throw new WrongComparing();

        return false;
    }

    public static boolean lessEquals(Object condVal, Object mapVal) throws WrongComparing {
        if ((mapVal instanceof Long && condVal instanceof Long)) {
            if ((Long) mapVal <= (Long) condVal)
                return true;
        } else if (mapVal instanceof Double && condVal instanceof Double) {
            if ((Double) mapVal <= (Double) condVal)
                return true;
        } else if (mapVal instanceof Double && condVal instanceof Long) {
            if ((Double) mapVal <= (Long) condVal)
                return true;
        } else if (mapVal instanceof Long && condVal instanceof Double) {
            if ((Long) mapVal <= (Double) condVal)
                return true;
        } else if (mapVal instanceof String || condVal instanceof String ||
                mapVal instanceof Boolean || condVal instanceof Boolean)
            throw new WrongComparing();

        return false;
    }

    public static boolean less(Object condVal, Object mapVal) throws WrongComparing {
        if ((mapVal instanceof Long && condVal instanceof Long)) {
            if ((Long) mapVal < (Long) condVal)
                return true;
        } else if (mapVal instanceof Double && condVal instanceof Double) {
            if ((Double) mapVal < (Double) condVal)
                return true;
        } else if (mapVal instanceof Double && condVal instanceof Long) {
            if ((Double) mapVal < (Long) condVal)
                return true;
        } else if (mapVal instanceof Long && condVal instanceof Double) {
            if ((Long) mapVal < (Double) condVal)
                return true;
        } else if (mapVal instanceof String || condVal instanceof String ||
                mapVal instanceof Boolean || condVal instanceof Boolean)
            throw new WrongComparing();

        return false;
    }

    public static boolean more(Object condVal, Object mapVal) throws WrongComparing {
        if ((mapVal instanceof Long && condVal instanceof Long)) {
            if ((Long) mapVal > (Long) condVal)
                return true;
        } else if (mapVal instanceof Double && condVal instanceof Double) {
            if ((Double) mapVal > (Double) condVal)
                return true;
        } else if (mapVal instanceof Double && condVal instanceof Long) {
            if ((Double) mapVal > (Long) condVal)
                return true;
        } else if (mapVal instanceof Long && condVal instanceof Double) {
            if ((Long) mapVal > (Double) condVal)
                return true;
        } else if (mapVal instanceof String || condVal instanceof String ||
                mapVal instanceof Boolean || condVal instanceof Boolean)
            throw new WrongComparing();

        return false;
    }

    public static boolean like(Object condVal, Object mapVal) throws WrongComparing {
        if ((mapVal instanceof String && condVal instanceof String)) {
            String map = (String) mapVal;
            String cond = ((String) condVal).replaceAll("%", ".*");
            Matcher matcher = Pattern.compile(cond).matcher(map);
            if (matcher.find()) {
                return true;
            }
        } else
            throw new WrongComparing();
        return false;
    }

    public static boolean ilike(Object condVal, Object mapVal) throws WrongComparing {
        if ((mapVal instanceof String && condVal instanceof String)) {
            String map = (String) ((String) mapVal).toLowerCase();
            String cond = ((String) condVal).replaceAll("%", ".*");
            Matcher matcher = Pattern.compile(cond).matcher(map);
            if (matcher.find()) {
                return true;
            }
        } else
            throw new WrongComparing();
        return false;
    }
}
