package com.digdes.school.parser;

import com.digdes.school.comparison.ComparisonExpressions;
import com.digdes.school.exceptions.AllFieldsAreNull;
import com.digdes.school.query.Query;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
    private static final String WHERE = "where";
    private static final String REGEX_EXPRESSIONS = "'[a-z]+'\\s*[=!><]+\\s*([0-9]+[.]?[0-9]*|'?[a-zа-я]+'?)\\s*[(and)|(or)]*";
    private static final String REGEX_SIGN_IN_EXPRESSIONS = "[<>=!]+";

    private static Query queryObj = new Query();

    public static Query parse(String query) throws AllFieldsAreNull {
        query = query.toLowerCase(Locale.ROOT);

        // Поиск имени команды - SELECT / UPDATE / DELETE / INSERT
        queryObj.setComand(query.split(" ")[0]);

        // Устанавливаем флаг WHERE, changes, conditions в Query
        if (query.contains(WHERE)) {
            queryObj.setFlagWhere(true);
            // Поиск выражений до WHERE (без учета знака и оператора - AND / OR)
            queryObj.setChanges(parseBeforeWhere(query.split(WHERE)[0]));
            // Поиск выражений после WHERE (с учетом знака и оператора - AND / OR)
            queryObj.setConditions(parseAfterWhere(query.split(WHERE)[1]));
        } else {
            queryObj.setFlagWhere(false);
            // Поиск выражений
            queryObj.setChanges(parseBeforeWhere(query));
        }
        return queryObj;
    }

    public static Map<String, Object> parseBeforeWhere(String query) throws AllFieldsAreNull {
        Map<String, Object> row = new HashMap<>();

        Pattern pattern = Pattern.compile(REGEX_EXPRESSIONS);
        Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            String[] substr = query
                    .substring(matcher.start(), matcher.end())
                    .replace("'", "")
                    .replace(" ", "")
                    .split(REGEX_SIGN_IN_EXPRESSIONS);

            // Проверка соответствия колонок таблицы и параметров запроса
            switch (substr[0]) {
                case "id":
                case "age":
                    row.put(substr[0], Long.parseLong(substr[1]));
                    break;
                case "lastname":
                    row.put(substr[0], substr[1]);
                    break;
                case "cost":
                    row.put(substr[0], Double.parseDouble(substr[1]));
                    break;
                case "active":
                    row.put(substr[0], Boolean.parseBoolean(substr[1]));
                    break;
            }
        }

        // Проверка, что не вся строка из null значений
        if (row.size() == 0 && queryObj.getComand().equals("insert"))
            throw new AllFieldsAreNull();

        return row;
    }

    public static List<ComparisonExpressions> parseAfterWhere(String query) throws AllFieldsAreNull {
        List<ComparisonExpressions> conditions = new ArrayList<>();

        Pattern pattern = Pattern.compile(REGEX_EXPRESSIONS);
        Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            String substr = query
                    .substring(matcher.start(), matcher.end())
                    .replace("'", "");

            // Запись в лист всех условий после where
            conditions.add(parseConditions(substr));
        }
        return conditions;
    }

    public static ComparisonExpressions parseConditions(String condition) {
        ComparisonExpressions conditionObj = new ComparisonExpressions();

        // Устанавливаем логический оператор
        if (condition.contains("and"))
            condition = setOperator("and", conditionObj, condition);
        else if (condition.contains("or"))
            condition = setOperator("or", conditionObj, condition);

        // Убираем все пробелы
        condition = condition.replace(" ", "");

        // Устанавливаем знак в условии
        String sign = null;
        Pattern pattern = Pattern.compile("[<>=!]+");
        Matcher matcher = pattern.matcher(condition);
        if (matcher.find())
            sign = condition.substring(matcher.start(), matcher.end());
        String[] substr = setSign(sign, conditionObj, condition);

        // Устанавливаем имя и значение переменной
        conditionObj.setField(substr[0]);
        switch (substr[0]) {
            case "id":
            case "age":
                conditionObj.setValue(Long.parseLong(substr[1]));
                break;
            case "lastname":
                conditionObj.setValue(substr[1]);
                break;
            case "cost":
                conditionObj.setValue(Double.parseDouble(substr[1]));
                break;
            case "active":
                conditionObj.setValue(Boolean.parseBoolean(substr[1]));
                break;
        }

        return conditionObj;
    }

    public static String setOperator(String operator, ComparisonExpressions conditionObj, String condition) {
        conditionObj.setNextOperator(operator);
        return condition.replace(operator, "");
    }

    public static String[] setSign(String sign, ComparisonExpressions conditionObj, String condition) {
        conditionObj.setSign(sign);
        return condition.split(sign);
    }



}
