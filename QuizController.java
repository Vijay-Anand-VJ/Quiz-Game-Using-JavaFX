
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class QuizController extends VBox {
    private int score = 0;
    private int currentQuestionIndex = 0;
    private Label questionLabel;
    private Button[] answerButtons;
    private Timeline timer;

    public QuizController() {
        questionLabel = new Label();
        answerButtons = new Button[4];

        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new Button();
            answerButtons[i].setOnAction(e -> checkAnswer(i));
        }

        this.getChildren().add(questionLabel);
        for (Button btn : answerButtons) {
            this.getChildren().add(btn);
        }

        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex >= QuestionData.questions.length) {
            // Game over, show results
            showResults();
            return;
        }
        Question currentQuestion = QuestionData.questions[currentQuestionIndex];
        questionLabel.setText(currentQuestion.getText());
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(currentQuestion.getOptions()[i]);
        }
        startTimer();
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(30), e -> {
            // Time expired
            nextQuestion();
        }));
        timer.setCycleCount(1);
        timer.play();
    }

    private void checkAnswer(int selectedOptionIndex) {
        Question currentQuestion = QuestionData.questions[currentQuestionIndex];
        if (currentQuestion.getCorrectAnswerIndex() == selectedOptionIndex) {
            score++;
        }
        nextQuestion();
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        loadNextQuestion();
    }

    private void showResults() {
        System.out.println("Game Over! Final Score: " + score);
    }
}
