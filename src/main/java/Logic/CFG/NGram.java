package Logic.CFG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NGram {

    static Map<String, List<String>> wordMapping = new HashMap<>();

    public static void addMapping(Map<String, List<String>> wordMapping, List<String> wordsList, String v) {

        wordsList.forEach(k -> {
            if (wordMapping.containsKey(k) && !wordMapping.get(k).contains(v)) wordMapping.get(k).add(v);
            else {
                List<String> valueList = new ArrayList<>();
                valueList.add(v);
                wordMapping.put(k, valueList);
            }
        });
    }

    public static List<String> getMapping(Map<String, List<String>> rules, String symbol,
            Map<String, List<String>> wordMapping,
            List<String> prevWords) {
        List<String> questions = rules.get(symbol);
        List<String> lastWords = new ArrayList<>();

        questions.forEach(sentence -> {
            List<String> previousWords = new ArrayList<>(prevWords);
            List<String> tokens = Arrays.asList(sentence.split(" "));

            for (String word : tokens) {
                if (rules.containsKey(word)) previousWords = getMapping(rules, word, wordMapping, previousWords);
                else {
                    addMapping(wordMapping, previousWords, word);
                    previousWords = new ArrayList<>();
                    previousWords.add(word);
                }
            }
            
            lastWords.addAll(previousWords);

        });
        return lastWords;
    }

    public static List<String> predictWords(String input, Map<String, List<String>> rules) throws IOException {
        getMapping(rules, "<S>", wordMapping, new ArrayList<>());
        return wordMapping.get(input.split(" ")[0]);
    }
}
