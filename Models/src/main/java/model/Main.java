package model;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Игорь Ергин\\Desktop\\AnaliticProject\\AnaliticsProject\\java-rtf.csv"; // Путь к вашему CSV файлу

        CSVParser parser = new CSVParser(filePath);
        List<Student> students = parser.parseCSV();

        if (students.isEmpty()) {
            System.out.println("Данные о студентах не найдены.");
        } else {
            PerformanceAnalyzer analyzer = new PerformanceAnalyzer(students);
            analyzer.displayAllStudentData(); // Вывод всех данных о студентах

            // Сохранение данных в базу данных
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.createTable(); // Создание таблиц, если они еще не существуют
            dbManager.insertStudentData(students); // Запись данных студентов в базу
        }
    }
}
