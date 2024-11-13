package model;

import java.util.Map;

public class Student {
    private String lastName;
    private Map<String, Double> scores;

    public Student(String lastName, Map<String, Double> scores) {
        this.lastName = lastName;
        this.scores = scores;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<String, Double> getScores() {
        return scores;
    }

    public String displayScores() {
        StringBuilder sb = new StringBuilder("Результаты для " + lastName + ":\n");
        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
