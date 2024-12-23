package model;

import model.CSVParser;
import model.PerformanceAnalyzer;
import model.Student;
import model.ChartVisualizer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Игорь Ергин\\Desktop\\AnaliticProject\\AnaliticsProject\\java-rtf.csv";

        // Парсинг данных CSV
        CSVParser parser = new CSVParser(filePath);
        List<Student> students = parser.parseCSV();

        if (students.isEmpty()) {
            System.out.println("Данные о студентах не найдены.");
            return;
        }

        // Извлечение максимальных баллов из данных первого студента
        Map<String, Double> maxScores = students.stream()
                .flatMap(student -> student.getScores().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Double::max
                ));

        // Анализ данных
        PerformanceAnalyzer analyzer = new PerformanceAnalyzer(students, maxScores);
        analyzer.displayAllStudentDataWithComparison();

        DatabaseManager dbManager = new DatabaseManager();
        dbManager.createTable();
        dbManager.insertStudentData(students);

        // Визуализация данных
        ChartVisualizer.displayCharts(students, maxScores);
    }
}
