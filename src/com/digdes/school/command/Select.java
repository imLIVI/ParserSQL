package com.digdes.school.command;

import com.digdes.school.query.Query;

import java.util.Map;

import static com.digdes.school.JavaSchoolStarter.data;

public class Select extends Command {

    @Override
    public void actionWithoutConditional(Query query) {
        System.out.printf("%7s %12s %12s %12s %12s\n", "id", "lastName", "cost", "age", "active");
        for (int i = 0; i < data.size(); i++) {
            selectOneRow(i);
        }
    }

    @Override
    public void actionWithConditional(Query query, int i) {
        System.out.printf("%7s %12s %12s %12s %12s\n", "id", "lastName", "cost", "age", "active");
        selectOneRow(i);
    }

    public void selectOneRow(int i) {
        Long id = null;
        String lastName = null;
        Double cost = null;
        Long age = null;
        Boolean active = false;
        for(Map.Entry column : data.get(i).entrySet()) {
            if (column.getKey().equals("id"))
                id = (Long) column.getValue();
            else if (column.getKey().equals("lastname"))
                lastName = column.getValue().toString();
            else if (column.getKey().equals("cost"))
                cost = (Double) column.getValue();
            else if (column.getKey().equals("age"))
                age = (Long) column.getValue();
            else if (column.getKey().equals("active"))
                active = (Boolean) column.getValue();
        }
        System.out.printf("%7s %12s %12s %12s %12s\n", id, lastName, cost, age, active);
    }

}
