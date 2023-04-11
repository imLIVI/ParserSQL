package com.digdes.school.print;

import java.util.List;
import java.util.Map;

public class PrintData {
    public static void printData(List<Map<String, Object>> data) {
        System.out.printf("%7s %12s %12s %12s %12s\n", "id", "lastName", "cost", "age", "active");
        for (int i = 0; i < data.size(); i++) {
            Long id = null;
            String lastName = null;
            Double cost = null;
            Long age = null;
            Boolean active = false;
            for (Map.Entry<String, Object> row : data.get(i).entrySet()) {
                if (row.getKey().equals("id"))
                    id = (Long) row.getValue();
                else if (row.getKey().equals("lastname"))
                    lastName = row.getValue().toString();
                else if (row.getKey().equals("cost"))
                    cost = (Double) row.getValue();
                else if (row.getKey().equals("age"))
                    age = (Long) row.getValue();
                else if (row.getKey().equals("active"))
                    active = (Boolean) row.getValue();
            }
            System.out.printf("%7s %12s %12s %12s %12s\n", id, lastName, cost, age, active);
        }
    }

    public static void printQuery(String query) {
        System.out.println(query);
    }
}
