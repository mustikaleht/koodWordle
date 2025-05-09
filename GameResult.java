public class GameResult {

    private final String username;
    private final String secretWord;
    private final int attempts;
    private final String result; // "win" or "loss"

    public GameResult(String username, String secretWord, int attempts, String result) {
        this.username = username;
        this.secretWord = secretWord;
        this.attempts = attempts;
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getResult() {
        return result;
    }
}