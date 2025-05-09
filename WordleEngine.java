package game;

import io.StatsWriter;
import model.GameResult;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class WordleEngine {

    private final String secretWord;
    private final Scanner scanner;
    private int attemptsRemaining = 6;
    private Set<Character> remainingLetters = new HashSet<>();
    private String username;
    private char[] correctPositions;
    private final Feedback feedbackGenerator;

    public WordleEngine(String secretWord, Scanner scanner) {
        this.secretWord = secretWord.toUpperCase();
        this.scanner = scanner;
        for (char c = 'A'; c <= 'Z'; c++) {
            remainingLetters.add(c);
        }
        correctPositions = new char[5];
        java.util.Arrays.fill(correctPositions, ' ');
        feedbackGenerator = new Feedback(this.secretWord, this.correctPositions);
    }

    public void play() {
        System.out.print("Enter your username: ");
        username = scanner.nextLine().trim();

        while (attemptsRemaining > 0) {
            System.out.println("\nAttempts remaining: " + attemptsRemaining);
            System.out.println("Remaining letters: " + remainingLetters.stream().sorted().collect(Collectors.toList()));
            System.out.print("Enter your guess (5 letters): ");
            String guess = scanner.nextLine().trim().toUpperCase();

            if (!isValidGuess(guess)) {
                System.out.println("Invalid guess. Please enter a 5-letter word.");
                continue;
            }

            String feedback = feedbackGenerator.provideFeedback(guess);
            System.out.println(feedback);
            updateCorrectPositions(guess, feedback);
            updateRemainingLetters(guess, feedback);

            if (guess.equals(secretWord)) {
                System.out.println("\u001B[32mCongratulations! You guessed the word: " + secretWord + "\u001B[0m");
                recordStats("win");
                break;
            }

            attemptsRemaining--;

            if (attemptsRemaining == 0) {
                System.out.println("You ran out of attempts! The secret word was: \u001B[31m" + secretWord + "\u001B[0m");
                recordStats("loss");
            }

            if (attemptsRemaining > 0) {
                askForStats();
            }
        }
    }

    private boolean isValidGuess(String guess) {
        return guess.length() == 5 && guess.matches("[A-Z]+");
    }

    private void updateCorrectPositions(String guess, String feedback) {
        for (int i = 0; i < guess.length(); i++) {
            if (feedback.substring(i * 6, (i + 1) * 6).contains("\u001B[32m")) {
                correctPositions[i] = guess.charAt(i);
            }
        }
        feedbackGenerator.updateCorrectPositions(this.correctPositions);
    }

    private void updateRemainingLetters(String guess, String feedback) {
        for (int i = 0; i < guess.length(); i++) {
            char guessedLetter = guess.charAt(i);
            boolean isCorrect = false;
            for (int j = 0; j < feedback.length(); j++) {
                if (feedback.substring(j).startsWith("\u001B[32m" + guessedLetter + "\u001B[0m") ||
                    feedback.substring(j).startsWith("\u001B[33m" + guessedLetter + "\u001B[0m")) {
                    isCorrect = true;
                    break;
                }
            }
            if (!isCorrect) {
                remainingLetters.remove(guessedLetter);
            }
        }
    }

    private void recordStats(String result) {
        StatsWriter statsWriter = new StatsWriter("stats.csv");
        GameResult gameResult = new GameResult(username, secretWord, 6 - attemptsRemaining, result);
        statsWriter.writeStats(gameResult);
    }

    private void askForStats() {
        System.out.print("View stats? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            displayStats();
        }
    }

    private void displayStats() {
        io.StatsReader statsReader = new io.StatsReader("stats.csv");
        statsReader.displayStats();
    }
}
