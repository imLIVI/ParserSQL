package com.digdes.school.command;

import com.digdes.school.query.Query;

import static com.digdes.school.JavaSchoolStarter.data;
import static com.digdes.school.print.PrintData.printData;

public class Select extends Command {

    @Override
    public void actionWithoutConditional(Query query) {
        printData(data);
    }

    @Override
    public void actionWithConditional(Query query, int i) {
        System.out.println("SELECT" + data.get(i));
    }
}
