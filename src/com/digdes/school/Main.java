package com.digdes.school;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
            //Вставка строки в коллекцию
          List<Map<String, Object>>   result = starter.execute(
                    "INSERT VALUES 'lastName' = 'Федоров', 'id' = 3, 'age' = 40, 'active' = true ");

            result = starter.execute("SELECT");

            result = starter.execute(
                    "INSERT VALUES 'id' = 4, 'age' = 40, 'active' = false ");

            result = starter.execute("SELECT");

            result = starter.execute(
                    "INSERT VALUES 'id' = 1, 'lastName' = 'Поxов', 'active' = true ");

            result = starter.execute("SELECT WHERE 'lastName' like '%п%'");

            result = starter.execute("SELECT WHERE 'lastName' ilike '%п%'");

            //Изменение значения которое выше записывали
            result = starter.execute(
                    "UPDATE VALUES 'active' = false, 'cost' = 10.1, 'lastName' = 'Попов' where 'id' != 3 AND 'active' = true");

            result = starter.execute("SELECT");

            result = starter.execute("SELECT WHERE 'lastName' like '%п%'");

            result = starter.execute("SELECT WHERE 'lastName' ilike '%п%' AND 'active' = false");

            result = starter.execute(
                    "DELETE WHERE 'id'=3");

            result = starter.execute("SELECT");

            //Получение всех данных из коллекции (т.е. в данном примере вернется 1 запись)
            result = starter.execute("SELECT WHERE 'id'<3");

            //printData(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
