package model;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ChartVisualizer {

    public static void displayCharts(List<Student> students, Map<String, Double> maxScores) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Performance Charts");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);

            // Dropdown menu for selecting students
            JComboBox<String> studentSelector = new JComboBox<>(students.stream()
                    .map(Student::getLastName)
                    .toArray(String[]::new));

            JPanel mainPanel = new JPanel(new BorderLayout());

            // Panel for the current chart
            JPanel chartPanel = new JPanel();
            chartPanel.setLayout(new BorderLayout());

            JScrollPane chartScrollPane = new JScrollPane(chartPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            // Add action listener to update chart on selection
            studentSelector.addActionListener(e -> {
                int selectedIndex = studentSelector.getSelectedIndex();
                Student selectedStudent = students.get(selectedIndex);
                chartPanel.removeAll();
                chartPanel.add(createChartPanel(selectedStudent, maxScores), BorderLayout.CENTER);
                chartPanel.revalidate();
                chartPanel.repaint();
            });

            // Initialize with the first student's chart
            chartPanel.add(createChartPanel(students.get(0), maxScores), BorderLayout.CENTER);

            mainPanel.add(studentSelector, BorderLayout.NORTH);
            mainPanel.add(chartScrollPane, BorderLayout.CENTER);

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }

    private static JPanel createChartPanel(Student student, Map<String, Double> maxScores) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Performance of: " + student.getLastName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(1, student.getScores().size()));

        for (Map.Entry<String, Double> entry : student.getScores().entrySet()) {
            String topic = entry.getKey();
            double studentScore = entry.getValue();
            double maxScore = maxScores.getOrDefault(topic, 0.0);

            JPanel barPanel = new JPanel();
            barPanel.setLayout(new BorderLayout());

            // Bar representation
            JPanel bar = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;

                    int maxHeight = getHeight();
                    int studentHeight = (int) ((studentScore / maxScore) * maxHeight);

                    // Draw max bar (dark green)
                    g2d.setColor(new Color(0, 100, 0)); // Darker green
                    g2d.fillRect(10, maxHeight - maxHeight, getWidth() / 3, maxHeight);

                    // Draw student bar (dark blue)
                    g2d.setColor(new Color(0, 0, 139)); // Darker blue
                    g2d.fillRect(getWidth() / 3 + 20, maxHeight - studentHeight, getWidth() / 3, studentHeight);

                    // Add numerical labels
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, 12));
                    g2d.drawString(String.format("%.1f", maxScore), 10, maxHeight - maxHeight - 5);
                    g2d.drawString(String.format("%.1f", studentScore), getWidth() / 3 + 20, maxHeight - studentHeight - 5);
                }
            };

            bar.setPreferredSize(new Dimension(100, 400)); // Increased size for better readability
            barPanel.add(bar, BorderLayout.CENTER);

            // Add topic label and max score label
            JPanel labelPanel = new JPanel(new GridLayout(2, 1));
            JLabel topicLabel = new JLabel(topic, JLabel.CENTER);
            topicLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            JLabel maxScoreLabel = new JLabel("Max: " + String.format("%.1f", maxScore), JLabel.CENTER);
            maxScoreLabel.setFont(new Font("Arial", Font.ITALIC, 10));

            labelPanel.add(topicLabel);
            labelPanel.add(maxScoreLabel);

            barPanel.add(labelPanel, BorderLayout.SOUTH);

            chartPanel.add(barPanel);
        }

        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }
}
