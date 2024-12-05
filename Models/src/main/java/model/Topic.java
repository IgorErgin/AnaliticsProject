package model;

class Topic {
    private String name;
    private Double difficulty;


    public Topic(String name, double difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public double getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return name + " (Сложность: " + difficulty + ")";
    }
}
