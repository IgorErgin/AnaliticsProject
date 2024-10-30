package model;

import java.util.*;


public class Student {
    private String lastName;
    private String group;
    private Map<String, Double> scores; // словарь {название темы: сложность}

    public Student(String lastName, String group) {
        this.lastName = lastName;
        this.group = group;
        this.scores = new HashMap<>();
    }

    public void addScore(String topicName, double difficulty) {
        scores.put(topicName, difficulty);
    }

    public List<Map.Entry<String, Double>> getDifficultTopics(int topN) {
        // Сортируем темы по сложности и выбираем topN самых сложных
        List<Map.Entry<String, Double>> sortedTopics = new ArrayList<>(scores.entrySet());
        sortedTopics.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        return sortedTopics.subList(0, Math.min(topN, sortedTopics.size()));
    }

    public String getLastName() {
        return lastName;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "Студент(" + lastName + ", Группа: " + group + ")";
    }
}