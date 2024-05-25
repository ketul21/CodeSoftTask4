package org.example;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz {
    private Question[] questions;
    private int score;
    private Scanner scanner;
    private Timer timer;
    private boolean timeUp;

    public Quiz(Question[] questions) {
        this.questions = questions;
        this.score = 0;
        this.scanner = new Scanner(System.in);
        this.timer = new Timer();
    }

    public void start() {
        for (Question question : questions) {
            timeUp = false;
            displayQuestion(question);
            int userAnswer = getUserAnswer();
            if (!timeUp) {
                checkAnswer(question, userAnswer);
            } else {
                System.out.println("Time is up! Moving to the next question.");
            }
        }
        System.out.printf("Quiz finished! Your total score is: %d/%d\n", score, questions.length);
    }

    private void displayQuestion(Question question) {
        System.out.println(question.getQuestionText());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s\n", i + 1, options[i]);
        }
        startTimer();
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp = true;
            }
        }, 10000); // 10 seconds for each question
    }

    private int getUserAnswer() {
        int userAnswer = -1;
        try {
            userAnswer = scanner.nextInt() - 1; // Adjusting for 0-based index
            timer.cancel();
            timer = new Timer(); // Reset timer for the next question
        } catch (Exception e) {
            // Handling invalid input
        }
        return userAnswer;
    }

    private void checkAnswer(Question question, int userAnswer) {
        if (userAnswer == question.getCorrectAnswerIndex()) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer is " + (question.getCorrectAnswerIndex() + 1));
        }
    }

    public static void main(String[] args) {
        Question[] questions = {
                new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 2),
                new Question("What is 5 + 3?", new String[]{"5", "8", "10", "15"}, 1),
                new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 1)
        };

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}
