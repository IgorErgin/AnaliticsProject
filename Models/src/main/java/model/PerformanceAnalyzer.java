package model;

import java.util.List;
import java.util.Map;

public class PerformanceAnalyzer {
    private List<Student> students;
    private Map<String, Double> maxScores;

    public PerformanceAnalyzer(List<Student> students, Map<String, Double> maxScores) {
        this.students = students;
        this.maxScores = maxScores;
    }

    public void displayAllStudentDataWithComparison() {
        for (Student student : students) {
            System.out.println(student.displayScores());
            System.out.println(compareStudentToMax(student));
        }
    }

    /**
     * Сравнивает результаты указанного студента с максимальными баллами.
     *
     * @param student студент, которого нужно сравнить
     * @return строка с отчетом о сравнении
     */
    private String compareStudentToMax(Student student) {
        StringBuilder comparisonReport = new StringBuilder("Сравнение для " + student.getLastName() + ":\n");

        for (Map.Entry<String, Double> entry : student.getScores().entrySet()) {
            String topic = entry.getKey();
            double studentScore = entry.getValue();
            double maxScore = maxScores.getOrDefault(topic, 0.0);

            comparisonReport.append(topic).append(": ")
                    .append("Студент: ").append(studentScore)
                    .append(", Максимум: ").append(maxScore);

            if (studentScore == maxScore) {
                comparisonReport.append(" (Максимальный результат!)");
            } else if (studentScore < maxScore) {
                comparisonReport.append(" (Недостаточно)");
            }
            comparisonReport.append("\n");
        }

        return comparisonReport.toString();
    }
}
