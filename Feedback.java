public class Feedback {

    private final String secretWord;
    private char[] correctPositions;

    public Feedback(String secretWord, char[] correctPositions) {
        this.secretWord = secretWord;
        this.correctPositions = correctPositions;
    }

    public void updateCorrectPositions(char[] correctPositions) {
        this.correctPositions = correctPositions;
    }

    public String provideFeedback(String guess) {
        StringBuilder feedback = new StringBuilder();
        char[] secretWordChars = secretWord.toCharArray();
        boolean[] secretWordUsed = new boolean[5];
        char[] guessChars = guess.toCharArray();

        // First pass: Check for green letters
        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == secretWordChars[i]) {
                feedback.append("\u001B[32m").append(guessChars[i]).append("\u001B[0m");
                secretWordUsed[i] = true;
            } else if (correctPositions[i] != ' ') {
                feedback.append("\u001B[32m").append(correctPositions[i]).append("\u001B[0m");
            } else {
                feedback.append("\u001B[37m").append(guessChars[i]).append("\u001B[0m");
            }
        }

        String currentFeedback = feedback.toString();
        feedback = new StringBuilder();

        // Second pass: Check for yellow letters
        char[] currentFeedbackChars = new char[5];
        for (int i = 0; i < 5; i++) {
            currentFeedbackChars[i] = currentFeedback.charAt(i * 6 + 2);

            if (currentFeedbackChars[i] != correctPositions[i]) {
                for (int j = 0; j < 5; j++) {
                    if (!secretWordUsed[j] && guessChars[i] == secretWordChars[j]) {
                        feedback.append("\u001B[33m").append(guessChars[i]).append("\u001B[0m");
                        secretWordUsed[j] = true;
                        break;
                    } else if (j == 4) {
                        feedback.append(currentFeedback.substring(i * 6, (i + 1) * 6));
                    }
                }
            } else {
                feedback.append(currentFeedback.substring(i * 6, (i + 1) * 6));
            }
        }

        return feedback.toString();
    }
}
