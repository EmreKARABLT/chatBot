package CFG;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LL1Parser {
    private static Map<String, List<String>> grammar;

    public LL1Parser(String filename) throws IOException {
        this.grammar = convertGrammarToHashMap(filename);
    }

    public boolean parse(String input, Map<String, List<String>> grammar) {
        // Add $ to the end of input string
        input = input + "$";

        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push("S");

        int index = 0;
        String lookahead = Character.toString(input.charAt(index));

        while (!stack.isEmpty()) {
            String top = stack.pop();

            if (isTerminal(top)) {
                if (top.equals(lookahead)) {
                    index++;
                    lookahead = Character.toString(input.charAt(index));
                } else {
                    return false;
                }
            } else {
                List<String> productions = grammar.get(top);
                String production = selectProduction(productions, lookahead);
                if (production == null) {
                    return false;
                }
                String[] symbols = production.split(" ");
                for (int i = symbols.length - 1; i >= 0; i--) {
                    if (!symbols[i].equals("epsilon")) {
                        stack.push(symbols[i]);
                    }
                }
            }
        }

        return true;
    }

    private boolean isTerminal(String symbol) {
        return !grammar.containsKey(symbol);
    }

    private String selectProduction(List<String> productions, String lookahead) {
        for (String production : productions) {
            String first = production.split(" ")[0];
            if (isTerminal(first) && first.equals(lookahead)) {
                return production;
            }
        }
        return null;
    }

    private static Map<String, List<String>> convertGrammarToHashMap(String filename) throws IOException {
        Map<String, List<String>> grammar = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        while (line != null) {
            String[] parts = line.split(" ");
            String key = parts[0];
            if (key.startsWith("<") && key.endsWith(">")) {
                List<String> value = new ArrayList<>();
                for (int i = 2; i < parts.length; i++) {
                    value.add(parts[i]);
                }
                if (grammar.containsKey(key)) {
                    grammar.get(key).addAll(value);
                } else {
                    grammar.put(key, value);
                }
            }
            line = reader.readLine();
        }
        reader.close();
        return grammar;
    }

    public static void main(String[] args) throws IOException {
        LL1Parser parser = new LL1Parser("src/main/java/CFG/grammar.txt");
        boolean isParsed = parser.parse("Where is DeepSpace?", grammar);
        System.out.println(isParsed);
    }
}