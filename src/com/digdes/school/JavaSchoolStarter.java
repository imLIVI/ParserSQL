package com.digdes.school;

import com.digdes.school.command.Command;
import com.digdes.school.comparison.ComparisonExpressions;
import com.digdes.school.comparison.ComparisonFunctions;
import com.digdes.school.exceptions.AllFieldsAreNull;
import com.digdes.school.exceptions.WrongComparing;
import com.digdes.school.parser.QueryParser;
import com.digdes.school.query.Query;

import java.util.*;

public class JavaSchoolStarter {
    public static List<Map<String, Object>> data = new ArrayList<>();

    //Дефолтный конструктор
    public JavaSchoolStarter() {
    }

    //На вход запрос, на выход результат выполнения запроса
    public List<Map<String, Object>> execute(String request) throws Exception {
        System.out.println(request);

        // Получение запроса после парсинга
        Query query = QueryParser.parse(request);

        // Получение класса команды - SELECT / UPDATE / DELETE / INSERT
        Command commandClass = Command.defineCommand(query.getComand());

        if (query.isFlagWhere()) {
            List<ComparisonExpressions> conditions = query.getConditions();

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
                    commandClass.actionWithConditional(query, i);
                }
            }
        } else {
            commandClass.actionWithoutConditional(query);
        }

        return data;
    }

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
