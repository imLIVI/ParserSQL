package com.digdes.school.command;

import com.digdes.school.query.Query;

import java.util.Map;

import static com.digdes.school.JavaSchoolStarter.data;

public class Update extends Command {

    @Override
    public void actionWithoutConditional(Query query) {
        // Delete all old values
        int listSize = data.size();
        for (int i = 0; i < listSize; i++)
            data.remove(0);

        // Replacing old values with new ones
        data.add(query.getChanges());
    }

    @Override
    public void actionWithConditional(Query query, int i) {
        Map<String, Object> changes = query.getChanges();

        for (Map.Entry mapChanges : changes.entrySet()) {
            String key = String.valueOf(mapChanges.getKey());
            if (!data.get(i).containsKey(String.valueOf(mapChanges.getKey())))
                data.get(i).put(key, mapChanges.getValue());
            else
                data.get(i).replace(key, mapChanges.getValue());
        }
    }
}
