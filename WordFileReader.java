import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordFileReader {

    private final String filePath;

    public WordFileReader(String filePath) {
        this.filePath = filePath;
    }

    public String[] loadWords() {
        List<String> words = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String word = fileScanner.nextLine().trim().toUpperCase();
                if (word.length() == 5 && word.matches("[A-Z]+")) {
                    words.add(word);
                }
            }
            return words.toArray(new String[0]);
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + filePath + " not found.");
            return null;
        }
    }
}

