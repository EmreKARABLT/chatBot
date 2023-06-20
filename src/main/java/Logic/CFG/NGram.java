package Logic.CFG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NGram {
  
    static Map<String, List<String>> rules;
    static Map<String, List<String>> mapping = new HashMap<>();


    public static void addMapping(Map<String, List<String>> mapping, List<String> ks, String v) {
        for (String k : ks) {
            if (mapping.containsKey(k)) {
                if (!mapping.get(k).contains(v)) {
                    mapping.get(k).add(v);
                }
            } else {
                List<String> valueList = new ArrayList<>();
                valueList.add(v);
                mapping.put(k, valueList);
            }
        }
    }

    public static List<String> getMapping(Map<String, List<String>> rules, String k, Map<String, List<String>> mapping,
            List<String> prevWords) {
        List<String> sentences = rules.get(k);
        List<String> lastWords = new ArrayList<>();

        for (String sentence : sentences) {
            List<String> prevWordsLocal = new ArrayList<>(prevWords);

            for (String word : sentence.split(" ")) {
                if (rules.containsKey(word)) {
                    prevWordsLocal = getMapping(rules, word, mapping, prevWordsLocal);
                } else {
                    addMapping(mapping, prevWordsLocal, word);
                    prevWordsLocal = new ArrayList<>();
                    prevWordsLocal.add(word);
                }
            }

            lastWords.addAll(prevWordsLocal);
        }

        return lastWords;
    }

    public static List<String> predictWords(String input, Map<String, List<String>> rules) throws IOException {
        getMapping(rules, "<S>", mapping, new ArrayList<>());
        String[] words = input.split(" ");
        String currentWord = words[0];
        List<String> predictedWords = new ArrayList<>();

        if (mapping.containsKey(currentWord)) {
            predictedWords = mapping.get(currentWord);
        }

        return predictedWords;
    }

    public static void main(String[] args) throws IOException {
        rules = RecursiveParser.parseGrammarFromFile("src/main/java/Logic/CFG/grammar.txt");

        System.out.println(mapping);
        Scanner scanner = new Scanner(System.in);
        String userInput;

        do {
            System.out.println("Enter your input (or 'quit' to exit):");
            userInput = scanner.nextLine();

            // Call your method with the user input
            System.out.println(predictWords(userInput, rules));    

        } while (!userInput.equalsIgnoreCase("quit"));

        scanner.close();
        System.out.println("Program exited.");
    }
}
