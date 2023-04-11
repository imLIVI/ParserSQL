package com.digdes.school;

import com.digdes.school.exceptions.AllFieldsAreNull;
import com.digdes.school.exceptions.InvalidParameterInTable;
import com.digdes.school.exceptions.InvalidRequest;
import com.digdes.school.exceptions.WrongComparing;

import java.util.*;

import static com.digdes.school.parse.ParseExprAfterWhere.handleWhere;
import static com.digdes.school.parse.ParseExprBeforeWhere.parseParameters;
import static com.digdes.school.print.PrintData.printData;

public class JavaSchoolStarter {
    public static List<Map<String, Object>> data;

    //Дефолтный конструктор
    public JavaSchoolStarter() {
        this.data = new ArrayList<>();
    }

    //На вход запрос, на выход результат выполнения запроса
    public List<Map<String, Object>> execute(String request) {
        // Приводим к нижнему регистру чтобы запросы были универсальными для разных регистров
        request = request.toLowerCase(Locale.ROOT);
        parseQuery(request);
        //Здесь начало исполнения вашего кода
        // return new ArrayList<>();
        return null;
    }

    public void parseQuery(String request) {
        System.out.println(request);

        if (request.contains("insert"))
            insert(request);
        else if (request.contains("update"))
            update(request);
        else if (request.contains("delete"))
            delete();
        else if (request.contains("select"))
            select();
        else throw new InvalidRequest(request);

        printData(data);
    }

    public void insert(String request) {
        // Добавление карты (строки таблицы) в список
        try {
            if (!request.contains("where")) {
                data.add(parseParameters(request));
            }
        } catch (InvalidParameterInTable e) {
            throw new RuntimeException(e);
        } catch (AllFieldsAreNull e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String request) {
        Map<String, Object> row = new HashMap<>();

        if (request.contains("where")) {
            try {
                handleWhere(request);
            } catch (InvalidParameterInTable e) {
                throw new RuntimeException(e);
            } catch (AllFieldsAreNull e) {
                throw new RuntimeException(e);
            } catch (WrongComparing e) {
                throw new RuntimeException(e);
            }
        }
        // TODO: Команды DELETE, SELECT, UPDATE могут выполняться без условия WHERE. В этом
        //  случае все записи должны быть получены, изменены или удалены
        else {
            try {
                // Delete all old values
                int listSize = data.size();
                for (int i = 0; i < listSize; i++)
                    data.remove(0);

                // Replacing old values with new ones
                data.add(parseParameters(request));
            } catch (InvalidParameterInTable e) {
                throw new RuntimeException(e);
            } catch (AllFieldsAreNull e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void delete() {

    }

    public void select() {

    }




}
