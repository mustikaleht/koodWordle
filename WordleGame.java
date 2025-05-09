import game.WordleEngine;
import io.WordFileReader;
import java.util.Scanner;

public class WordleGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WordFileReader fileReader = new WordFileReader("wordle-words.txt");
        String[] wordList = fileReader.loadWords();

        if (wordList == null || wordList.length == 0) {
            System.out.println("Could not load word list. Exiting.");
            System.exit(1);
        }

        int wordIndex = getWordIndex(args, wordList.length);
        String secretWord = wordList[wordIndex];

        WordleEngine game = new WordleEngine(secretWord, scanner);
        game.play();

        scanner.close();
    }

    private static int getWordIndex(String[] args, int wordListSize) {
        if (args.length > 0) {
            try {
                int index = Integer.parseInt(args[0]);
                if (index >= 0 && index < wordListSize) {
                    return index;
                } else {
                    System.out.println("Warning: Invalid word index provided. Using a random word.");
                    return new java.util.Random().nextInt(wordListSize);
                }
            } catch (NumberFormatException e) {
                System.out.println("Warning: Invalid command-line argument. Using a random word.");
                return new java.util.Random().nextInt(wordListSize);
            }
        } else {
            System.out.println("Warning: Missing command-line argument. Using a random word.");
            return new java.util.Random().nextInt(wordListSize);
        }
    }
}
