package CFG;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFGParser {
    private static Map<String, List<String>> grammar;
    private static final String TT_IDENTIFIER = "IDENTIFIER";
    private static final String TT_EOF = "EOF";
    private int pos;
    private String[] tokens;

    private String inputStr;

    public CFGParser(Map<String, List<String>> grammar, String filename) throws IOException {
        this.grammar = grammar;

    }

    public static boolean parse(List<String> input, String startSymbol) {
        System.out.println("\nInput: " + input);
        List<String> rules = grammar.get(startSymbol);

        if (rules == null) {
            return false;
        }

        for (String rule : rules) {
            List<String> tokens = Arrays.stream(rule.split("\\s+")).toList();
            boolean match = true;

            for (int i = 0; i < tokens.size(); i++) {
                if (grammar.containsKey(tokens.get(i))) {
                    if (!parse(input.subList(i, input.size()), tokens.get(i))) {
                        match = false;
                        break;
                    }
                } else if (!(tokens.get(i).equals(input.get(i)))) {
                    match = false;
                    break;
                }

            }

            if (match) {
                return true;
            }

        }

        return false;
    }


    private static Map<String, List<String>> convertGrammarToHashMap(String filename) throws IOException {
        Map<String, List<String>> grammar = new HashMap<>();
        Pattern pattern = Pattern.compile("^Rule (<[A-Z]*>) (.*)$");

        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String key = matcher.group(1);
                String[] values = matcher.group(2).split(" \\| ");
                List<String> valueList = new ArrayList<>();
                for (String value : values) {
                    valueList.add(value.trim());
                }
                grammar.put(key, valueList);
            }
        }
        scanner.close();

        return grammar;

    }


    public static void main(String[] args) throws IOException {
        grammar = convertGrammarToHashMap("src/main/java/CFG/grammar.txt");
        CFGParser parser = new CFGParser(grammar, "src/main/java/CFG/grammar.txt");

        boolean match = parser.parse(Arrays.stream("Which lectures are there at 9 on Saturday".split("\\s+")).toList(), "<S>");
        System.out.println(match);


    }
}