package com.digdes.school;

import com.digdes.school.exceptions.AllFieldsAreNull;
import com.digdes.school.exceptions.InvalidParameterInTable;
import com.digdes.school.exceptions.InvalidRequest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaSchoolStarter {
    private List<Map<String, Object>> data;

    //Дефолтный конструктор
    public JavaSchoolStarter() {
        this.data = new ArrayList<>();
    }

    //На вход запрос, на выход результат выполнения запроса
    public List<Map<String, Object>> execute(String request) throws Exception {
        // Приводим к нижнему регистру чтобы запросы были универсальными для разных регистров
        request = request.toLowerCase(Locale.ROOT);
        parseQuery(request);
        //Здесь начало исполнения вашего кода
        // return new ArrayList<>();
        return null;
    }

    public void parseQuery(String request) {
        if (request.contains("insert"))
            insert(request);
        else if (request.contains("update"))
            update();
        else if (request.contains("delete"))
            delete();
        else if (request.contains("select"))
            select();
        else throw new InvalidRequest(request);
    }

    public void insert(String request) {
        // Добавление карты (строки таблицы) в список
        try {
            data.add(parseParameters(request));
            System.out.println(request);
            printData();
        } catch (InvalidParameterInTable e) {
            throw new RuntimeException(e);
        } catch (AllFieldsAreNull e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {

    }

    public void delete() {

    }

    public void select() {

    }

    public Map<String, Object> parseParameters(String request) throws InvalidParameterInTable, AllFieldsAreNull {
        Map<String, Object> row = new HashMap<>();
        int counter = 0;

        Pattern pattern = Pattern.compile("'[a-z]+'\\s*[=!><]+\\s*([0-9]+[.]?[0-9]*|'?[a-zа-я]+'?)");
        Matcher matcher = pattern.matcher(request);

        while (matcher.find()) {
            String[] substr = request
                    .substring(matcher.start(), matcher.end())
                    .replace("'", "")
                    .replace(" ", "")
                    .split("=");

            // Проверка соответствия колонок таблицы и параметров запроса
            if (substr[0].equals("id")) {
                row.put(substr[0], Long.parseLong(substr[1]));
                counter++;
            } else if (substr[0].equals("lastname")) {
                row.put(substr[0], substr[1]);
                counter++;
            } else if (substr[0].equals("cost")) {
                row.put(substr[0], Double.parseDouble(substr[1]));
                counter++;
            } else if (substr[0].equals("age")) {
                row.put(substr[0], Long.parseLong(substr[1]));
                counter++;
            } else if (substr[0].equals("active")) {
                row.put(substr[0], Boolean.parseBoolean(substr[1]));
                counter++;
            } else throw new InvalidParameterInTable(substr[0]);

            // Проверка, что не все поля null
            if (counter == 0)
                throw new AllFieldsAreNull();
        }
        return row;
    }

    public void printData() {
        System.out.printf("%7s %12s %12s %12s %12s\n", "id", "lastName", "cost", "age", "active");
        long id = 0;
        String lastName = null;
        double cost = 0;
        long age = 0;
        boolean active = false;
        for (int i = 0; i < data.size(); i++) {
            for (Map.Entry<String, Object> row : data.get(i).entrySet()) {
                if (row.getKey().equals("id"))
                    id = (long) row.getValue();
                else if (row.getKey().equals("lastname"))
                    lastName = row.getValue().toString();
                else if (row.getKey().equals("cost"))
                    cost = (double) row.getValue();
                else if (row.getKey().equals("age"))
                    age = (long) row.getValue();
                else if (row.getKey().equals("active"))
                    active = (boolean) row.getValue();
            }
            System.out.printf("%7s %12s %12s %12s %12s\n", id, lastName, cost, age, active);
        }
    }

}
