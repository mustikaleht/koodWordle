import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StatsReader {

    private final String filePath;

    public StatsReader(String filePath) {
        this.filePath = filePath;
    }

    public void displayStats() {
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            System.out.println("\n--- Game Statistics ---");
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
            System.out.println("-----------------------");
        } catch (FileNotFoundException e) {
            System.out.println("No stats recorded yet.");
        }
    }
}
