package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:student_performance.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("SQLite JDBC Driver not found.");
        }
    }

    public void createTable() {
        String createStudentTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "last_name TEXT NOT NULL" +
                ");";
        String createScoresTableSQL = "CREATE TABLE IF NOT EXISTS scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "student_id INTEGER," +
                "topic TEXT NOT NULL," +
                "score REAL," +
                "FOREIGN KEY (student_id) REFERENCES students(id)" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.createStatement().execute(createStudentTableSQL);
            conn.createStatement().execute(createScoresTableSQL);
            System.out.println("Таблицы созданы или уже существуют.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertStudentData(List<Student> students) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);

            String insertStudentSQL = "INSERT INTO students (last_name) VALUES (?);";
            PreparedStatement studentStmt = conn.prepareStatement(insertStudentSQL);

            String insertScoreSQL = "INSERT INTO scores (student_id, topic, score) VALUES (?, ?, ?);";
            PreparedStatement scoreStmt = conn.prepareStatement(insertScoreSQL);

            for (Student student : students) {
                studentStmt.setString(1, student.getLastName());
                studentStmt.executeUpdate();

                int studentId = getLastInsertedId(conn);

                for (Map.Entry<String, Double> entry : student.getScores().entrySet()) {
                    scoreStmt.setInt(1, studentId);
                    scoreStmt.setString(2, entry.getKey());
                    scoreStmt.setDouble(3, entry.getValue());
                    scoreStmt.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("Данные студентов успешно добавлены в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getLastInsertedId(Connection conn) throws SQLException {
        String query = "SELECT last_insert_rowid();";
        var rs = conn.createStatement().executeQuery(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }
}
