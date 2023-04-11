package com.digdes.school.parse;

import com.digdes.school.comparison.ComparisonExpressions;
import com.digdes.school.comparison.ComparisonFunctions;
import com.digdes.school.exceptions.AllFieldsAreNull;
import com.digdes.school.exceptions.InvalidParameterInTable;
import com.digdes.school.exceptions.WrongComparing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.digdes.school.JavaSchoolStarter.data;
import static com.digdes.school.parse.ParseExprBeforeWhere.parseParameters;
import static com.digdes.school.print.PrintData.printData;

public class ParseExprAfterWhere {
    public static void handleWhere(String request) throws InvalidParameterInTable, AllFieldsAreNull, WrongComparing {
        List<ComparisonExpressions> conditions = new ArrayList<>();

        // Divide the request on 2 parts - before WHERE and after WHERE
        String[] requestParts = request.split("where");
        Pattern pattern = Pattern.compile("'[a-z]+'\\s*[=!><]+\\s*([0-9]+[.]?[0-9]*|'?[a-zа-я]+'?)\\s*[(and)|(or)]*");

        // After where
        Matcher matcher = pattern.matcher(requestParts[1]);
        while (matcher.find()) {
            String substr = requestParts[1]
                    .substring(matcher.start(), matcher.end())
                    .replace("'", "");

            // Запись в лист всех условий после where
            conditions.add(parseConditions(substr));
        }

        // Сравниваем и меняем делаем ДЕЙСТВИЕ (UPDATE, DELETE, SELECT)
        boolean result = false, prevResult = false;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < conditions.size(); j++) {
                String field = conditions.get(j).getField();
                // Записываем результаты в список, чтобы потом сравнить на И и ИЛИ
                result = checkExpression(conditions.get(j), data.get(i).get(field));
                if (j != 0) {
                    result = logicalCalc(result, prevResult, conditions.get(j - 1).getNextOperator());
                }
                prevResult = result;
            }
            // Если строка подходит по условию - меняем ее (UPDATE)
            if (result) {
                if (request.contains("update"))
                    update(requestParts[0], i);
                else if (request.contains("delete"))
                    delete(i);
                else if (request.contains("select"))
                    select(i);
            }
        }
    }

    public static void update(String beforeWhere, int i) throws InvalidParameterInTable, AllFieldsAreNull {
        // Before where
        // Получаем строку с изменениями, которые надо внести в строки, подходящие по условию
        Map<String, Object> changes = parseParameters(beforeWhere);

        for (Map.Entry mapChanges : changes.entrySet()) {
            String key = String.valueOf(mapChanges.getKey());
            if (!data.get(i).containsKey(String.valueOf(mapChanges.getKey())))
                data.get(i).put(key, mapChanges.getValue());
            else
                data.get(i).replace(key, mapChanges.getValue());
        }
    }

    public static void delete(int i) {
        data.remove(i);
    }

    public static void select(int i) {
        System.out.println("SELECT" + data.get(i));
    }


    // Функция для вычисления логического выражения (И и ИЛИ)
    public static boolean logicalCalc(boolean result, boolean prevResult, String operator) {
        if (operator.equals("and"))
            return result & prevResult;
        else if (operator.equals("or"))
            return result | prevResult;
        return false;
    }

    public static boolean checkExpression(ComparisonExpressions condition, Object mapVal) throws WrongComparing {
        Object condVal = condition.getValue();
        String sign = condition.getSign();
        boolean result = false;

        // TODO: 13)Значения которые передаются на сравнение не могут быть null
        if (condVal == null || mapVal == null)
            return false;

        switch (sign) {
            case "!=":
                result = ComparisonFunctions.checkNotEquals(condVal, mapVal);
                break;
            case ">=":
                result = ComparisonFunctions.moreEquals(condVal, mapVal);
                break;
            case "<=":
                result = ComparisonFunctions.lessEquals(condVal, mapVal);
                break;
            case ">":
                result = ComparisonFunctions.more(condVal, mapVal);
                break;
            case "<":
                result = ComparisonFunctions.less(condVal, mapVal);
                break;
            case "=":
                result = ComparisonFunctions.checkEquals(condVal, mapVal);
                break;
        }

        return result;
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
