package model;

import java.util.List;

public class PerformanceAnalyzer {
    private List<Student> students;

    public PerformanceAnalyzer(List<Student> students) {
        this.students = students;
    }

    public void displayAllStudentData() {
        for (Student student : students) {
            System.out.println(student.displayScores());
        }
    }
}