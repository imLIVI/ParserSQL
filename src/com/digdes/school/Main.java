package com.digdes.school;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
            //Вставка строки в коллекцию
            List<Map<String, Object>> result = starter.execute(
                    "INSERT VALUES 'lastName' = 'Федоров', 'id' = 3, 'age' = 40, 'active' = true ");

            result = starter.execute("SELECT");

            result = starter.execute(
                    "INSERT VALUES 'id' = 4, 'age' = 40, 'active' = true ");

            result = starter.execute("SELECT");

            result = starter.execute(
                    "INSERT VALUES 'id' = 1, 'lastName' = 'Попов', 'active' = true ");

            result = starter.execute("SELECT");

            //Изменение значения которое выше записывали
            result = starter.execute(
                    "UPDATE VALUES 'active' = false, 'cost' = 10.1, 'lastName' = 'Попов' where 'id' != 3 AND 'active' = true OR 'active' = false");

            result = starter.execute("SELECT");

            result = starter.execute(
                    "DELETE WHERE 'id'=3");

            //Получение всех данных из коллекции (т.е. в данном примере вернется 1 запись)
            result = starter.execute("SELECT WHERE 'id'<3");

            //printData(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
