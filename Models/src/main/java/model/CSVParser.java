package model;

import java.io.*;
import java.util.*;

public class CSVParser {
    private String filePath;

    public CSVParser(String filePath) {
        this.filePath = filePath;
    }


    public List<Student> parseCSV() {
        List<Student> students = new ArrayList<>();
        Map<String, Double> maxScores = new LinkedHashMap<>();
        
        boolean isFirstStudentSkipped = false; // Флаг для пропуска первого студента

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            // Прочитать строки с заголовком и названиями тем
            String headerLine = br.readLine();
            String topicsLine = br.readLine();
            String[] topics = topicsLine.split(";");

            // Инициализировать карту для максимальных значений
            for (int i = 3; i < topics.length; i++) {
                maxScores.put(topics[i], Double.MIN_VALUE);
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (!isFirstStudentSkipped) {
                    // Пропустить первую строку данных (первого студента)
                    isFirstStudentSkipped = true;
                    continue;
                }

                String[] columns = line.split(";");
                if (columns.length < 4) continue; // Пропустить строки с недостаточным количеством данных

                // Извлечь фамилию студента
                String lastName = columns[0];
                // Создать карту для хранения оценок по темам
                Map<String, Double> scores = new LinkedHashMap<>();

                // Обработать оценки по темам, начиная с 4-го столбца
                for (int i = 3; i < columns.length; i++) {
                    String topic = topics[i]; // Получить название темы
                    double score = columns[i].isEmpty() ? 0.0 : Double.parseDouble(columns[i]); // Парсинг оценки
                    scores.put(topic, score);

                    // Обновить максимальный балл
                    maxScores.put(topic, Math.max(maxScores.get(topic), score));
                }

                // Создать объект Student и добавить его в список
                students.add(new Student(lastName, scores));
            }

            // Добавить строку с максимальными баллами (для тестирования, здесь просто вывод в консоль)
            System.out.println("Максимальные баллы по темам:");
            maxScores.forEach((topic, maxScore) -> System.out.println(topic + ": " + maxScore));


        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return students;
    }



}
