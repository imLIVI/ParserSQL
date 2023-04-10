package com.digdes.school;

import com.digdes.school.comparison.ComparisonFunctions;
import com.digdes.school.exceptions.AllFieldsAreNull;
import com.digdes.school.exceptions.InvalidParameterInTable;
import com.digdes.school.exceptions.InvalidRequest;
import com.digdes.school.exceptions.WrongComparing;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.digdes.school.print.PrintData.printData;

public class JavaSchoolStarter {
    private List<Map<String, Object>> data;

    //Дефолтный конструктор
    public JavaSchoolStarter() {
        this.data = new ArrayList<>();
    }

    //На вход запрос, на выход результат выполнения запроса
    public List<Map<String, Object>> execute(String request) {
        // Приводим к нижнему регистру чтобы запросы были универсальными для разных регистров
        request = request.toLowerCase(Locale.ROOT);
        parseQuery(request);
        //Здесь начало исполнения вашего кода
        // return new ArrayList<>();
        return null;
    }

    public void parseQuery(String request) {
        System.out.println(request);

        if (request.contains("insert"))
            insert(request);
        else if (request.contains("update"))
            update(request);
        else if (request.contains("delete"))
            delete();
        else if (request.contains("select"))
            select();
        else throw new InvalidRequest(request);

        printData(data);
    }

    public void insert(String request) {
        // Добавление карты (строки таблицы) в список
        try {
            if (!request.contains("where")) {
                data.add(parseParameters(request));
            }
        } catch (InvalidParameterInTable e) {
            throw new RuntimeException(e);
        } catch (AllFieldsAreNull e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String request) {
        Map<String, Object> row = new HashMap<>();

        if (request.contains("where")) {
            try {
                handleWhere(request);
            } catch (InvalidParameterInTable e) {
                throw new RuntimeException(e);
            } catch (AllFieldsAreNull e) {
                throw new RuntimeException(e);
            } catch (WrongComparing e) {
                throw new RuntimeException(e);
            }
        }
        // TODO: Команды DELETE, SELECT, UPDATE могут выполняться без условия WHERE. В этом
        //  случае все записи должны быть получены, изменены или удалены
        else {
            try {
                // Delete all old values
                int listSize = data.size();
                for (int i = 0; i < listSize; i++)
                    data.remove(0);

                // Replacing old values with new ones
                data.add(parseParameters(request));
            } catch (InvalidParameterInTable e) {
                throw new RuntimeException(e);
            } catch (AllFieldsAreNull e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void delete() {

    }

    public void select() {

    }

    public void handleWhere(String request) throws InvalidParameterInTable, AllFieldsAreNull, WrongComparing {
        List<Condition> conditions = new ArrayList<>();

        // Divide the request on 2 parts
        String[] requestParts = request.split("where");
        Pattern pattern = Pattern.compile("'[a-z]+'\\s*[=!><]+\\s*([0-9]+[.]?[0-9]*|'?[a-zа-я]+'?)\\s*[(and)|(or)]*");

        // After where
        Matcher matcherR = pattern.matcher(requestParts[1]);
        while (matcherR.find()) {
            String substr = requestParts[1]
                    .substring(matcherR.start(), matcherR.end())
                    .replace("'", "");

            // Запись в лист всех условий после where
            conditions.add(parseConditions(substr));
        }

        // Before where
        // Получаем строку с изменениями, которые надо внести в строки, подходящие по условию
        Map<String, Object> changes = new HashMap<>();
        changes = parseParameters(requestParts[0]);

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
            // Если строка подходит по условию - меняем ее
            if (result) {
                for (Map.Entry mapChanges : changes.entrySet()) {
                    String key = String.valueOf(mapChanges.getKey());
                    if (!data.get(i).containsKey(String.valueOf(mapChanges.getKey())))
                        data.get(i).put(key, mapChanges.getValue());
                    else
                        data.get(i).replace(key, mapChanges.getValue());
                }
            }
        }
    }

    // Функция для вычисления логического выражения (И и ИЛИ)
    public boolean logicalCalc(boolean result, boolean prevResult, String operator) {
        if (operator.equals("and"))
            return result & prevResult;
        else if (operator.equals("or"))
            return result | prevResult;
        return false;
    }

    public boolean checkExpression(Condition condition, Object mapVal) throws WrongComparing {
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


    public Condition parseConditions(String condition) {
        Condition conditionObj = new Condition();

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

    public String setOperator(String operator, Condition conditionObj, String condition) {
        conditionObj.setNextOperator(operator);
        return condition.replace(operator, "");
    }

    public String[] setSign(String sign, Condition conditionObj, String condition) {
        conditionObj.setSign(sign);
        return condition.split(sign);
    }

    public Map<String, Object> parseParameters(String request) throws InvalidParameterInTable, AllFieldsAreNull {
        Map<String, Object> row = new HashMap<>();

        Pattern pattern = Pattern.compile("'[a-z]+'\\s*[=!><]+\\s*([0-9]+[.]?[0-9]*|'?[a-zа-я]+'?)");
        Matcher matcher = pattern.matcher(request);

        while (matcher.find()) {
            String[] substr = request
                    .substring(matcher.start(), matcher.end())
                    .replace("'", "")
                    .replace(" ", "")
                    .split("=");

            // Проверка соответствия колонок таблицы и параметров запроса
            row = typeConversation(substr, row);
        }

        if (row.size() == 0)
            throw new AllFieldsAreNull();

        return row;
    }

    public Map<String, Object> typeConversation(String[] substr, Map<String, Object> row) {

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
        return row;
    }
}
