package com.digdes.school.command;

import com.digdes.school.query.Query;

public abstract class Command {

    public static Command defineCommand(String commandName) {
        switch (commandName) {
            case "insert":
                return new Insert();
            case "update":
                return new Update();
            case "delete":
                return new Delete();
            case "select":
                return new Select();
            default:
                System.out.println("[ERROR] wrong command in query");;
        }
        return null;
    }

    // Action when there isn`t WHERE
    public abstract void actionWithoutConditional(Query query);

    // Action when there isn`t WHERE
    public abstract void actionWithConditional(Query query, int i);

}
