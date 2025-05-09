import model.GameResult;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StatsWriter {

    private final String filePath;

    public StatsWriter(String filePath) {
        this.filePath = filePath;
    }

    public void writeStats(GameResult result) {
        try (FileWriter fw = new FileWriter(filePath, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(String.format("%s,%s,%d,%s", result.getUsername(), result.getSecretWord(),
                    result.getAttempts(), result.getResult()));
        } catch (IOException e) {
            System.err.println("Error writing to " + filePath + ": " + e.getMessage());
        }
    }
}
