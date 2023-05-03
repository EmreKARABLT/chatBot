package Logic.CFG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Logic.CFG.Response.actionsList;

public class RecursiveParser {

    public String filename;

    /**
     *
     * @param input       This is the input string given by the user
     * @param startSymbol Represents the start symbol of the grammar for the
     *                    recursive search which in our case the start symbol is
     *                    "<S>"
     * @param grammar    The grammar to be used for the recursive search
     * @return A boolean value, where true represents the input being part of the
     *         grammar, otherwise it isn't
     */
    public boolean hasMatch(List<String> input, String startSymbol, Map<String, List<String>> grammar) {
        // System.out.println("\nInput: " + input);
        List<String> rules = grammar.get(startSymbol);

        if (rules == null) {
            return false;
        }

        for (String rule : rules) {
            List<String> tokens = Arrays.stream(rule.split("\\s+")).toList();
            boolean match = true;

            for (int i = 0; i < tokens.size(); i++) {
                if (grammar.containsKey(tokens.get(i))) {
                    if (i >= input.size() || !hasMatch(input.subList(i, input.size()), tokens.get(i), grammar)) {
                        match = false;
                        break;
                    }
                } else if (i >= input.size() || !(tokens.get(i).equals(input.get(i)))) {
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

    /**
     *
     * @return Hashmap where the rules of the grammar text file are stored, where
     *         the keys are the non-terminals which are represented by "<>" and the
     *         values are the
     *         strings/words not contained in the "<>" brackets
     * @throws IOException
     */
    private Map<String, List<String>> parseGrammarFromFile() throws IOException {
        Map<String, List<String>> grammar = new HashMap<>();
        Pattern pattern = Pattern.compile("^Rule (<[A-Z]*>) (.*)$");

        Scanner scanner = new Scanner(new File("src/main/java/Logic/CFG/" + filename + ".txt"));
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

     /**
     *
     * @return A list, where it stores the conditions and according responses for the action in the grammar text file
     * The conditions stores the string before the "|" symbol and the response stores the string after the "|" symbol
     * @throws FileNotFoundException
     */
    public List<Response> parseActionsFromFile() throws FileNotFoundException {
        Pattern pattern = Pattern.compile("^Action (.*)$");

        Scanner scanner = new Scanner(new File("src/main/java/Logic/CFG/" + filename + ".txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String[] parts = matcher.group(1).split("\\|");
                Response action = new Response();
                action.condition = parts.length >= 2 ? parts[0].trim() : "I have no idea.";
                action.response = parts.length >= 2 ? parts[1].trim() : "I have no idea.";
                actionsList.add(action);
            }
        }
        return actionsList;
    }

    /**
     *
     * @param input This is the input string given by the user
     * @return A response to the provided user input
     * @throws FileNotFoundException
     */
    public String matchInputWithAction(List<String> input) throws FileNotFoundException {
        List<Response> actionsList = parseActionsFromFile();
        int match = 0;
        int highestMatch = 0;
        int highestMatchIndex = -1;
        for (int i = 0; i < actionsList.size(); i++) {
            List<String> tokens = Arrays.stream(actionsList.get(i).getCondition().split("\\s+")).toList();
            for (String token : tokens) {
                for (String s : input) {
                    if (token.equals(s)) {
                        ++match;
                    }
                }
                if (match > highestMatch) {
                    highestMatch = match;
                    highestMatchIndex = i;
                }
            }
            match = 0;
        }
        return highestMatchIndex > -1 ? actionsList.get(highestMatchIndex).getResponse() : "I have no idea";
    }

    /**
     *
     * @param input   is the input string given by the user
     * @return This combines the parser for rules and actions to make the CFG and gives the response
     * @throws IOException
     */
    public String respond(String input) throws IOException {
        Map<String, List<String>> grammar = parseGrammarFromFile();
        boolean match = hasMatch(Arrays.stream(input.split("\\s+")).toList(), "<S>", grammar);
        if(match){
            return matchInputWithAction(Arrays.stream(input.split("\\s+")).toList());
        }
        return "I have no Idea";
    }

    public RecursiveParser(String filename) {
        this.filename = filename;

    }   

    public static void main(String[] args) {
        RecursiveParser parser = new RecursiveParser("grammar");
        try {
            System.out.println(parser.respond("Where is DeepSpace"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
