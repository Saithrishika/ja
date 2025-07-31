import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class QuizFrame extends JFrame {
    JLabel questionLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup bg = new ButtonGroup();
    JButton nextBtn;
    javax.swing.Timer timer;
    int timeLeft = 60;
    JLabel timerLabel;

    ArrayList<Question> questions = new ArrayList<>();
    int current = 0, score = 0;

    public QuizFrame(String username) {
        setTitle("Quiz - Welcome " + username);
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question here");
        JPanel centerPanel = new JPanel(new GridLayout(5, 1));
        centerPanel.add(questionLabel);

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            bg.add(options[i]);
            centerPanel.add(options[i]);
        }

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        timerLabel = new JLabel("Time: 60");
        nextBtn = new JButton("Next");
        bottomPanel.add(timerLabel);
        bottomPanel.add(nextBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadQuestions();
        displayQuestion(current);
        startTimer();

        nextBtn.addActionListener(e -> {
            checkAnswer();
            current++;
            if (current < questions.size()) {
                displayQuestion(current);
                timeLeft = 60;
            } else {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Exam Over! Your Score: " + score + "/" + questions.size());
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void startTimer() {
        timer = new javax.swing.Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);
            if (timeLeft == 0) {
                nextBtn.doClick();
            }
        });
        timer.start();
    }

    private void loadQuestions() {
        try (Connection con = DBconnection.connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM questions")) {
            while (rs.next()) {
                questions.add(new Question(
                    rs.getString("question"),
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d"),
                    rs.getString("answer")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading questions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayQuestion(int index) {
        Question q = questions.get(index);
        questionLabel.setText((index + 1) + ". " + q.question);
        options[0].setText("A. " + q.a);
        options[1].setText("B. " + q.b);
        options[2].setText("C. " + q.c);
        options[3].setText("D. " + q.d);
        bg.clearSelection();
    }

    private void checkAnswer() {
        Question q = questions.get(current);
        String selected = "";
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = options[i].getText().substring(0, 1);
            }
        }
        if (selected.equals(q.answer)) {
            score++;
        }
    }

    class Question {
        String question, a, b, c, d, answer;
        public Question(String q, String a, String b, String c, String d, String ans) {
            this.question = q; this.a = a; this.b = b;
            this.c = c; this.d = d; this.answer = ans;
        }
    }
}
