import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionManager {
    private String database;
    private String user;
    private String password;
    private Connection connection;

    public QuestionManager(String database, String user, String password) {
        this.database = database;
        this.user = user;
        this.password = password;
        connect();
    }

    private void connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/" + database + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas łączenia z bazą danych: " + e.getMessage());
        }
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        // Pobierz wszystkie pytania z bazy danych
        String query = "SELECT * FROM questions";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String questionText = resultSet.getString("question");
                String[] answers = {
                        resultSet.getString("answer1"),
                        resultSet.getString("answer2"),
                        resultSet.getString("answer3"),
                        resultSet.getString("answer4")
                };
                int correctAnswer = resultSet.getInt("correct_answer");
                questions.add(new Question(id, questionText, answers, correctAnswer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public int getQuestionCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM questions";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void editQuestion(Question question) {
        // Aktualizuj pytanie w bazie danych
        String query = "UPDATE questions SET question = ?, answer1 = ?, answer2 = ?, answer3 = ?, answer4 = ?, correct_answer = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, question.getQuestion());
            statement.setString(2, question.getAnswers()[0]);
            statement.setString(3, question.getAnswers()[1]);
            statement.setString(4, question.getAnswers()[2]);
            statement.setString(5, question.getAnswers()[3]);
            statement.setInt(6, question.getCorrectAnswer());
            statement.setInt(7, question.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(int id) {
        // Usuń pytanie z bazy danych
        String query = "DELETE FROM questions WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Question getRandomQuestion() {
        // Pobierz losowe pytanie z bazy danych
        String query = "SELECT * FROM questions ORDER BY RAND() LIMIT 1";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String questionText = resultSet.getString("question");
                String[] answers = {
                        resultSet.getString("answer1"),
                        resultSet.getString("answer2"),
                        resultSet.getString("answer3"),
                        resultSet.getString("answer4")
                };
                int correctAnswer = resultSet.getInt("correct_answer");
                return new Question(id, questionText, answers, correctAnswer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Question> getRandomQuestions(int count) {
        List<Question> randomQuestions = new ArrayList<>();
        String query = "SELECT * FROM questions ORDER BY RAND() LIMIT ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, count);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String questionText = resultSet.getString("question");
                String[] answers = {
                        resultSet.getString("answer1"),
                        resultSet.getString("answer2"),
                        resultSet.getString("answer3"),
                        resultSet.getString("answer4")
                };
                int correctAnswer = resultSet.getInt("correct_answer");
                randomQuestions.add(new Question(id, questionText, answers, correctAnswer));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas pobierania pytań: " + e.getMessage());
        }
        return randomQuestions;
    }


    public Question getQuestion(int id) {
        // Pobierz pytanie o konkretnym ID
        String query = "SELECT * FROM questions WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String questionText = resultSet.getString("question");
                String[] answers = {
                        resultSet.getString("answer1"),
                        resultSet.getString("answer2"),
                        resultSet.getString("answer3"),
                        resultSet.getString("answer4")
                };
                int correctAnswer = resultSet.getInt("correct_answer");
                return new Question(id, questionText, answers, correctAnswer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addQuestion(Question question) {
        // Dodaj pytanie do bazy danych
        String query = "INSERT INTO questions (question, answer1, answer2, answer3, answer4, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, question.getQuestion());
            statement.setString(2, question.getAnswers()[0]);
            statement.setString(3, question.getAnswers()[1]);
            statement.setString(4, question.getAnswers()[2]);
            statement.setString(5, question.getAnswers()[3]);
            statement.setInt(6, question.getCorrectAnswer());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveScore(String username, int score) {
        String query = "INSERT INTO scores (username, score) VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setInt(2, score);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }


    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}