/*
package com.digdes.school.execute;

import com.digdes.school.comparison.ComparisonExpressions;
import com.digdes.school.comparison.ComparisonFunctions;
import com.digdes.school.exceptions.WrongComparing;

import java.util.List;
import java.util.Map;

import static com.digdes.school.JavaSchoolStarter.data;

public class ExecuteOperation {
    public static void findingString(Map<String, Object> changes,
                                     List<ComparisonExpressions> conditions,
                                     String comand) throws WrongComparing {
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
                switch (comand) {
                    case "update":
                        update(changes, i);
                        break;
                    case "delete":
                        delete(changes, i);
                        break;
                    */
/*case "select":
                        update(changes, i);
                        break;*//*

                }
            }
        }
    }

    public static void update(Map<String, Object> changes, int i) {
        for (Map.Entry mapChanges : changes.entrySet()) {
            String key = String.valueOf(mapChanges.getKey());
            if (!data.get(i).containsKey(String.valueOf(mapChanges.getKey())))
                data.get(i).put(key, mapChanges.getValue());
            else
                data.get(i).replace(key, mapChanges.getValue());
        }
    }

    public static void delete(Map<String, Object> changes, int i) {
        for (Map.Entry mapChanges : changes.entrySet()) {
            String key = String.valueOf(mapChanges.getKey());
            if (!data.get(i).containsKey(String.valueOf(mapChanges.getKey())))
                data.get(i).put(key, mapChanges.getValue());
            else
                data.get(i).replace(key, mapChanges.getValue());
        }
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
}
*/
