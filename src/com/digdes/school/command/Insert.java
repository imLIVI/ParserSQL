package com.digdes.school.command;

import com.digdes.school.exceptions.InvalidRequest;
import com.digdes.school.query.Query;

import static com.digdes.school.JavaSchoolStarter.data;

public class Insert extends Command {

    @Override
    public void actionWithoutConditional(Query query) {
        data.add(query.getChanges());
    }

    @Override
    public void actionWithConditional(Query query, int i) {
        throw new InvalidRequest(query.getComand());
    }
}
