package com.digdes.school.parse;

import com.digdes.school.exceptions.AllFieldsAreNull;
import com.digdes.school.exceptions.InvalidParameterInTable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseExprBeforeWhere {
    public static Map<String, Object> parseParameters(String request) throws InvalidParameterInTable, AllFieldsAreNull {
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

        if (row.size() == 0)
            throw new AllFieldsAreNull();

        return row;
    }
}
