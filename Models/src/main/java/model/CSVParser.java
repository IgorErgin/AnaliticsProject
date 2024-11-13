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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            // Прочитать строки с заголовком и названиями тем
            String headerLine = br.readLine();
            String topicsLine = br.readLine();
            String[] topics = topicsLine.split(";");

            String line;
            while ((line = br.readLine()) != null) {
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
                }

                // Создать объект Student и добавить его в список
                students.add(new Student(lastName, scores));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return students;
    }
}
