import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordLoader {
    private List<String> wordList;

    public WordLoader(String filename) {
        this.wordList = loadWords(filename);
    }

    private List<String> loadWords(String filename) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public List<String> getWordList() {
        return wordList;
    }
}