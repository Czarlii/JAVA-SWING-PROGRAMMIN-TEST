import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuizGUI {
    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] answerButtons;
    private ButtonGroup buttonGroup;
    private int currentQuestionIndex = 0;
    private List<Question> questions;
    private QuestionManager questionManager;
    private int score;

    private JButton startButton;
    private JButton nextButton;
    private JButton showAllQuestionsButton;
    private JButton showQuestionCountButton;
    private JButton addQuestionButton;
    private JButton deleteQuestionButton;
    private JButton editQuestionButton;
    private JButton showScoresButton;
    private JButton deleteScoresButton;

    public QuizGUI() {
        initializeGUI();
        questionManager = new QuestionManager("quiz_db", "root", "karol1");
    }

    private void initializeGUI() {
        frame = new JFrame("Programming Quiz by Karol Przewuski");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        startButton = createButton("Rozpocznij quiz", this::startQuiz);
        showAllQuestionsButton = createButton("Pokaż wszystkie pytania", this::showAllQuestions);
        showQuestionCountButton = createButton("Ilość pytań", this::showQuestionCount);
        addQuestionButton = createButton("Dodaj Pytanie", this::addQuestion);
        deleteQuestionButton = createButton("Usuń Pytanie", this::deleteQuestion);
        editQuestionButton = createButton("Edytuj Pytanie", this::editQuestion);
        showScoresButton = createButton("Wyświetl wyniki", this::showScores);
        deleteScoresButton = createButton("Usuń dotychczasowe wyniki", this::deleteScores);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                questionManager.closeConnection();
            }
        });

        displayWelcomeScreen();
        frame.setVisible(true);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
        return button;
    }


    private void displayWelcomeScreen() {
        // Tworzenie etykiety powitalnej
        JLabel welcomeLabel = new JLabel("Witaj w teście programowania!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 60));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 60, 0));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        // Tworzenie panelu przycisków
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2, 5, 10)); // Ustawienie układu siatki dla 8 przycisków
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 160, 60, 160));

        // Dodawanie przycisków do panelu
        buttonPanel.add(startButton);
        buttonPanel.add(showAllQuestionsButton);
        buttonPanel.add(showQuestionCountButton);
        buttonPanel.add(addQuestionButton);
        buttonPanel.add(deleteQuestionButton);
        buttonPanel.add(editQuestionButton);
        buttonPanel.add(showScoresButton);
        buttonPanel.add(deleteScoresButton);


        // Dodawanie panelu przycisków do głównego okna
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Odświeżanie i przerysowywanie okna
        frame.revalidate();
        frame.repaint();
    }

    private void startQuiz(ActionEvent e) {
        // Resetowanie wyniku i indeksu bieżącego pytania
        score = 0;
        currentQuestionIndex = 0;

        // Usuwanie ekranu powitalnego
        frame.getContentPane().removeAll();

        // Pobieranie losowych pytań z menedżera pytań
        questions = questionManager.getRandomQuestions(20);

        // Tworzenie etykiety pytania
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Ustawienie większej czcionki
        questionLabel.setBorder(BorderFactory.createEmptyBorder(150, 0, 20, 0));
        JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        questionPanel.add(questionLabel);
        frame.add(questionPanel, BorderLayout.NORTH);

        // Tworzenie panelu odpowiedzi
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JRadioButton();
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 24)); // Ustawienie większej czcionki
            buttonGroup.add(answerButtons[i]);
            answerPanel.add(answerButtons[i]);
        }

// Utworzenie panelu do wyśrodkowania answerPanel w osi X
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(answerPanel);

        frame.add(centerPanel, BorderLayout.CENTER);


        // Tworzenie przycisku "Następne pytanie"
        nextButton = createButton("Następne pytanie", this::nextQuestion);
        nextButton.setPreferredSize(new Dimension(200, 50));
        // Tworzenie przycisku "Zrezygnuj"
        JButton quitButton = new JButton("Zrezygnuj");
        quitButton.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
        quitButton.setPreferredSize(new Dimension(200, 50));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame,
                        "Czy na pewno chcesz zrezygnować z quizu?",
                        "Potwierdzenie",
                        JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.YES_OPTION) {
                    frame.dispose(); // Zamknij bieżące okno quizu
                    new QuizGUI();   // Wróć do głównego menu
                }
            }
        });

// Tworzenie panelu dla przycisków
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(nextButton);
        buttonPanel.add(quitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

// Wyświetlanie pierwszego pytania
        displayQuestion(currentQuestionIndex);

// Odświeżanie i przerysowywanie okna
        frame.revalidate();
        frame.repaint();
    }

    private void nextQuestion(ActionEvent e) {
        // Sprawdź, czy użytkownik wybrał odpowiedź
        if (buttonGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(frame, "Proszę wybrać odpowiedź przed przejściem do następnego pytania.");
            return;
        }
        // Sprawdź odpowiedź użytkownika
        for (int i = 0; i < 4; i++) {
            if (answerButtons[i].isSelected() && i == questions.get(currentQuestionIndex).getCorrectAnswer() - 1) {
                score++;
                break;
            }
        }
        // Zwiększ indeks bieżącego pytania
        currentQuestionIndex++;

        // Sprawdź, czy jest jeszcze więcej pytań
        if (currentQuestionIndex < questions.size()) {
            // Wyświetl następne pytanie
            displayQuestion(currentQuestionIndex);
        } else {
            // Jeśli to było ostatnie pytanie, zakończ quiz
            endQuiz();
        }
    }

    private void showAllQuestions(ActionEvent e) {
        // Pobieranie wszystkich pytań z menedżera pytań
        List<Question> allQuestions = questionManager.getAllQuestions();

        // Tworzenie tekstu zawierającego wszystkie pytania i ich odpowiedzi
        StringBuilder questionsText = new StringBuilder();
        int questionNumber = 1;
        for (Question question : allQuestions) {
            questionsText.append(questionNumber++).append(") ").append(question.getQuestion()).append("\n");
            questionsText.append("A) ").append(question.getAnswers()[0]).append("\n");
            questionsText.append("B) ").append(question.getAnswers()[1]).append("\n");
            questionsText.append("C) ").append(question.getAnswers()[2]).append("\n");
            questionsText.append("D) ").append(question.getAnswers()[3]).append("\n");
            char correctAnswerLetter = (char) ('A' + question.getCorrectAnswer() - 1);
            questionsText.append("Poprawna odpowiedź: ").append(correctAnswerLetter).append("\n\n\n\n");
        }

        // Wyświetlanie wszystkich pytań w oknie dialogowym
        JTextArea textArea = new JTextArea(questionsText.toString());
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(frame, scrollPane, "Wszystkie pytania", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showQuestionCount(ActionEvent e) {
        int count = questionManager.getQuestionCount();
        JOptionPane.showMessageDialog(frame, "Liczba pytań w bazie danych: " + count, "Liczba pytań", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayQuestion(int index) {

        // Pobierz pytanie na podstawie indeksu
        Question currentQuestion = questions.get(index);

        // Ustaw tekst etykiety pytania
        questionLabel.setText((index + 1) + ") " + currentQuestion.getQuestion());

        // Ustaw tekst przycisków odpowiedzi
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(currentQuestion.getAnswers()[i]);
        }

        // Odznacz wszystkie przyciski odpowiedzi
        buttonGroup.clearSelection();

        // Odświeżanie i przerysowywanie okna
        frame.revalidate();
        frame.repaint();
    }


    private void addQuestion(ActionEvent e) {
        // Okno dialogowe do wprowadzania pytania
        String questionText = JOptionPane.showInputDialog(frame, "Wprowadź pytanie:");

        // Jeśli użytkownik anuluje wprowadzanie, zakończ metodę
        if (questionText == null || questionText.trim().isEmpty()) {
            return;
        }

        // Okna dialogowe do wprowadzania odpowiedzi
        String[] answers = new String[4];
        for (int i = 0; i < 4; i++) {
            answers[i] = JOptionPane.showInputDialog(frame, "Wprowadź odpowiedź " + (i + 1) + ":");
            if (answers[i] == null || answers[i].trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Wprowadzenie pytania anulowane.");
                return;
            }
        }

        // Okno dialogowe do wprowadzania poprawnej odpowiedzi
        String correctAnswerString = JOptionPane.showInputDialog(frame, "Wprowadź numer poprawnej odpowiedzi (1-4):");
        if (correctAnswerString == null || correctAnswerString.trim().isEmpty()) {
            return;
        }

        int correctAnswer;
        try {
            correctAnswer = Integer.parseInt(correctAnswerString);
            if (correctAnswer < 1 || correctAnswer > 4) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Nieprawidłowy numer odpowiedzi. Proszę wprowadzić numer od 1 do 4.");
            return;
        }

        // Tworzenie obiektu pytania i dodawanie go do bazy danych
        Question newQuestion = new Question(-1, questionText, answers, correctAnswer);
        questionManager.addQuestion(newQuestion);

        JOptionPane.showMessageDialog(frame, "Pytanie zostało dodane pomyślnie!");
    }

    private void deleteQuestion(ActionEvent e) {
        // Pobieranie wszystkich pytań z menedżera pytań
        List<Question> allQuestions = questionManager.getAllQuestions();

        // Jeśli nie ma pytań, wyświetl komunikat
        if (allQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Brak pytań w bazie danych.");
            return;
        }

        // Tworzenie listy pytań do wyboru
        String[] questionChoices = new String[allQuestions.size()];
        for (int i = 0; i < allQuestions.size(); i++) {
            questionChoices[i] = allQuestions.get(i).getQuestion();
        }

        // Okno dialogowe do wyboru pytania do usunięcia
        String selectedQuestion = (String) JOptionPane.showInputDialog(
                frame,
                "Wybierz pytanie do usunięcia:",
                "Usuń pytanie",
                JOptionPane.QUESTION_MESSAGE,
                null,
                questionChoices,
                questionChoices[0]
        );

        // Jeśli użytkownik anuluje wybór, zakończ metodę
        if (selectedQuestion == null) {
            return;
        }

        // Wyszukaj wybrane pytanie
        Question questionToDelete = null;
        for (Question question : allQuestions) {
            if (question.getQuestion().equals(selectedQuestion)) {
                questionToDelete = question;
                break;
            }
        }

        // Usuwanie pytania z bazy danych
        if (questionToDelete != null) {
            questionManager.deleteQuestion(questionToDelete.getId());
            JOptionPane.showMessageDialog(frame, "Pytanie zostało usunięte pomyślnie!");
        } else {
            JOptionPane.showMessageDialog(frame, "Nie udało się znaleźć wybranego pytania.");
        }
    }

    private void editQuestion(ActionEvent e) {
        // Pobieranie wszystkich pytań z menedżera pytań
        List<Question> allQuestions = questionManager.getAllQuestions();

        // Jeśli nie ma pytań, wyświetl komunikat
        if (allQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Brak pytań w bazie danych.");
            return;
        }

        // Tworzenie listy pytań do wyboru
        String[] questionChoices = new String[allQuestions.size()];
        for (int i = 0; i < allQuestions.size(); i++) {
            questionChoices[i] = allQuestions.get(i).getQuestion();
        }

        // Okno dialogowe do wyboru pytania do edycji
        String selectedQuestionText = (String) JOptionPane.showInputDialog(
                frame,
                "Wybierz pytanie do edycji:",
                "Edytuj pytanie",
                JOptionPane.QUESTION_MESSAGE,
                null,
                questionChoices,
                questionChoices[0]
        );

        // Jeśli użytkownik anuluje wybór, zakończ metodę
        if (selectedQuestionText == null) {
            return;
        }

        // Wyszukaj wybrane pytanie
        Question selectedQuestion = null;
        for (Question question : allQuestions) {
            if (question.getQuestion().equals(selectedQuestionText)) {
                selectedQuestion = question;
                break;
            }
        }

        // Jeśli pytanie zostało znalezione, otwórz okno edycji
        if (selectedQuestion != null) {
            // Tworzenie okna dialogowego do edycji pytania
            JPanel editPanel = new JPanel();
            editPanel.setLayout(new GridLayout(6, 2)); // 6 wierszy (dla pytania, 4 odpowiedzi i poprawnej odpowiedzi) oraz 2 kolumny

            // Pole tekstowe dla pytania
            JLabel questionLabel = new JLabel("Pytanie:");
            JTextField questionField = new JTextField(selectedQuestion.getQuestion());
            editPanel.add(questionLabel);
            editPanel.add(questionField);

            // Pola tekstowe dla odpowiedzi
            JTextField[] answerFields = new JTextField[4];
            for (int i = 0; i < 4; i++) {
                JLabel answerLabel = new JLabel("Odpowiedź " + (char) ('A' + i) + ":");
                answerFields[i] = new JTextField(selectedQuestion.getAnswers()[i]);
                editPanel.add(answerLabel);
                editPanel.add(answerFields[i]);
            }

            // Pole wyboru dla poprawnej odpowiedzi
            JLabel correctAnswerLabel = new JLabel("Poprawna odpowiedź:");
            JComboBox<String> correctAnswerComboBox = new JComboBox<>(new String[]{"A", "B", "C", "D"});
            correctAnswerComboBox.setSelectedIndex(selectedQuestion.getCorrectAnswer() - 1); // Zakładając, że correctAnswer to wartość od 1 do 4
            editPanel.add(correctAnswerLabel);
            editPanel.add(correctAnswerComboBox);

            int result = JOptionPane.showConfirmDialog(frame, editPanel, "Edytuj pytanie", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Aktualizacja pytania i odpowiedzi na podstawie wprowadzonych danych
                selectedQuestion.setQuestion(questionField.getText());
                for (int i = 0; i < 4; i++) {
                    selectedQuestion.getAnswers()[i] = answerFields[i].getText();
                }
                selectedQuestion.setCorrectAnswer(correctAnswerComboBox.getSelectedIndex() + 1);

                // Aktualizacja pytania w bazie danych
                questionManager.editQuestion(selectedQuestion);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Nie udało się znaleźć wybranego pytania.");
        }
    }

    private void showScores(ActionEvent e) {
        // Pobieranie wyników z bazy danych
        List<String> scoresList = new ArrayList<>();
        String query = "SELECT * FROM scores ORDER BY score DESC"; // Pobieranie wyników w kolejności malejącej
        try {
            PreparedStatement statement = questionManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                scoresList.add(username + ": " + score);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Błąd podczas pobierania wyników z bazy danych.");
            return;
        }

        // Wyświetlanie wyników w oknie dialogowym
        if (!scoresList.isEmpty()) {
            StringBuilder scoresText = new StringBuilder("Wyniki użytkowników:\n\n");
            for (String score : scoresList) {
                scoresText.append(score).append("\n");
            }
            JTextArea textArea = new JTextArea(scoresText.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(300, 200));
            JOptionPane.showMessageDialog(frame, scrollPane, "Wyniki", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Brak wyników w bazie danych.");
        }
    }

    private void deleteScores(ActionEvent e) {
        try {
            // Uzyskiwanie połączenia z bazą danych
            Connection connection = questionManager.getConnection();

            // Tworzenie zapytania SQL do usunięcia wszystkich wyników
            String sql = "DELETE FROM scores";

            // Przygotowanie i wykonanie zapytania
            PreparedStatement stmt = connection.prepareStatement(sql);
            int deletedRows = stmt.executeUpdate();

            // Informowanie użytkownika o pomyślnym usunięciu wyników
            if (deletedRows > 0) {
                JOptionPane.showMessageDialog(frame, "Wszystkie wyniki zostały usunięte.");
            } else {
                JOptionPane.showMessageDialog(frame, "Brak wyników do usunięcia.");
            }
        } catch (SQLException ex) {
            // Obsługa błędów
            JOptionPane.showMessageDialog(frame, "Wystąpił błąd podczas usuwania wyników: " + ex.getMessage());
        }
    }

    private void endQuiz() {
        // Wyświetl wynik użytkownika
        JOptionPane.showMessageDialog(frame, "Twój wynik to: " + score);

        // Pobierz pseudonim od użytkownika
        String username = JOptionPane.showInputDialog(frame, "Wprowadź swój pseudonim:");

        // Jeśli użytkownik wprowadził pseudonim, zapisz wynik w bazie danych
        if (username != null && !username.trim().isEmpty()) {
            saveScore(username, score);
        }
        frame.dispose();

         new QuizGUI();
    }

    private void saveScore(String username, int score) {
        String query = "INSERT INTO scores (username, score) VALUES (?, ?)";
        try {
            PreparedStatement statement = questionManager.getConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setInt(2, score);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Błąd podczas zapisywania wyniku w bazie danych.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizGUI());
    }
}
