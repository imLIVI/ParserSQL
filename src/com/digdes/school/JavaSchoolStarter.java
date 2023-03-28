package com.digdes.school;

import com.digdes.school.exceptions.InvalidRequest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaSchoolStarter {
    private List<Map<String, Object>> result;

    //Дефолтный конструктор
    public JavaSchoolStarter() {
        this.result = new ArrayList<>();
    }

    //На вход запрос, на выход результат выполнения запроса
    public List<Map<String, Object>> execute(String request) throws Exception {
        // Приводим к нижнему регистру чтобы запросы были универсальными для разных регистров
        request = request.toLowerCase(Locale.ROOT);
        parseQuery(request);
        System.out.println(request);
        //Здесь начало исполнения вашего кода
        // return new ArrayList<>();
        return null;
    }

    public void parseQuery(String request) {
        if (request.contains("insert"))
            insert();
        else if (request.contains("update"))
            update();
        else if (request.contains("delete"))
            delete();
        else if (request.contains("select"))
            select();
        else throw new InvalidRequest("[ERROR] Invalid request");
    }

    public void insert() {
        Map<String, Object> row = new HashMap<>();
        row.put("id", 3);
        row.put("lastName", "Федоров");
        row.put("age", 40);
        row.put("active", true);
        //data.add(row3);
    }

    public void update() {

    }

    public void delete() {

    }

    public void select() {

    }

    public void parseParameters(String request) {
        Pattern pattern = Pattern.compile("'[a-z]+'\\s*[=!><]+\\s*([0-9]+[.]?[0-9]*|'?[a-zа-я]+'?)");
        Matcher matcher = pattern.matcher(request);
        while (matcher.find()) {
            System.out.println(request.substring(matcher.start(), matcher.end()));
        }
    }
}
