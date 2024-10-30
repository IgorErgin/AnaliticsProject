package model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

class PerformanceAnalyzer {
    private List<Student> students;

    public PerformanceAnalyzer(List<Student> students) {
        this.students = students;
    }

    public Student getStudent(String lastName) {
        // Поиск студента по фамилии
        for (Student student : students) {
            if (student.getLastName().equalsIgnoreCase(lastName)) {
                return student;
            }
        }
        return null;
    }
    public List<Map.Entry<String, Double>> getDifficultTopicsForStudent(String lastName) {
        // Получение топ 3 сложных тем для заданного студента
        Student student = getStudent(lastName);
        if (student != null) {
            return student.getDifficultTopics(3);
        }
        return Collections.emptyList();
    }
}
