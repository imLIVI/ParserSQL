package com.digdes.school.command;

import com.digdes.school.query.Query;

import static com.digdes.school.StarterTest.data;

public class Delete extends Command{

    @Override
    public void actionWithoutConditional(Query query) {
        // Delete all old values
        int listSize = data.size();
        for (int i = 0; i < listSize; i++)
            data.remove(0);
    }

    @Override
    public void actionWithConditional(Query query, int i) {
        data.remove(i);
    }
}
