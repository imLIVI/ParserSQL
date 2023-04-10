package com.digdes.school;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
            //Вставка строки в коллекцию
            List<Map<String, Object>> result1 = starter.execute(
                    "INSERT VALUES 'lastName' = 'Федоров', 'id' = 3, 'age' = 40, 'active' = true ");
            List<Map<String, Object>> result11 = starter.execute(
                    "INSERT VALUES 'id' = 4, 'age' = 40, 'active' = true ");
            List<Map<String, Object>> result111 = starter.execute(
                    "INSERT VALUES 'id' = 1, 'lastName' = 'Попов', 'active' = true ");
            //Изменение значения которое выше записывали
            List<Map<String, Object>> result2 = starter.execute(
                    "UPDATE VALUES 'active' = false, 'cost' = 10.1 where 'id' != 3 AND 'active' = true OR 'active' = false");
            //Получение всех данных из коллекции (т.е. в данном примере вернется 1 запись)
            List<Map<String, Object>> result3 = starter.execute("SELECT");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
