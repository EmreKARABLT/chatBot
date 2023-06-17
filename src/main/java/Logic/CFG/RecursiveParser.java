package Logic.CFG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecursiveParser {

    /**
     *
     * @param input       This is the input string given by the user
     * @param startSymbol Represents the start symbol of the grammar for the
     *                    recursive search which in our case the start symbol is
     *                    "<S>"
     * @param grammar     The grammar to be used for the recursive search
     * @param rulesHashmap Hashmap for the input string when it is being parsed where the keys represent the non-terminals
     *                     and values are the contents of the input string.
     * @return A boolean value, where true represents the input being part of the
     *         grammar, otherwise it isn't
     */

    public static int hasMatch(List<String> input, String startSymbol, Map<String, List<String>> grammar,
                               Map<String, String> rulesHashmap) {

        List<String> rules = grammar.get(startSymbol);

        if (rules == null) {
            return 0;
        }

        for (String rule : rules) {
            boolean match = false;
            List<String> ruleTokens = Arrays.stream(rule.split("\\s+")).toList();
            int ruleIndex = 0, inputIndex = 0;
            while (ruleIndex < ruleTokens.size() && inputIndex < input.size()) {
                if (grammar.containsKey(ruleTokens.get(ruleIndex))) {
                    int m = hasMatch(input.subList(inputIndex, input.size()), ruleTokens.get(ruleIndex), grammar,
                            rulesHashmap);
                    if (m != 0) {
                        inputIndex += m;
                        ruleIndex++;
                        match = true;
                        continue;
                    }
                } else if (ruleTokens.get(ruleIndex).equals(input.get(inputIndex))) {
                    ruleIndex++;
                    inputIndex++;
                    match = true;
                    continue;
                }
                match = false;
                break;

            }

            if (match && ruleIndex == ruleTokens.size()) {
                rulesHashmap.put(startSymbol, rule);
                return inputIndex;
            }

        }

        return 0;
    }

    /**
     * @param filename is the path of where the grammar text file is located
     * @return Hashmap where the rules of the grammar text file are stored, where
     *         the keys are the non-terminals which are represented by "<>" and the
     *         values are the
     *         strings/words not contained in the "<>" brackets
     * @throws IOException
     */
    static Map<String, List<String>> parseGrammarFromFile(String filename) throws IOException {
        Map<String, List<String>> grammar = new HashMap<>();
        Pattern pattern = Pattern.compile("^Rule (<[a-zA-Z]*>) (.*)$");

        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().replaceAll("\\.", " .").replaceAll("\\?", " ?");
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
     * @param filename is the path of where the grammar text file is located
     * @return A list, where it stores the conditions and according responses for
     *         the action in the grammar text file
     *         The conditions stores the string before the "->" symbol and the
     *         response stores the string after the "->" symbol
     * @throws FileNotFoundException
     */

    public static List<Response> parseActionsFromFile(String filename) throws FileNotFoundException {
        Pattern responsePattern = Pattern.compile("Action.*->\\s+(.*)");
        Pattern kvPattern = Pattern.compile("(<\\w*>)\\s+([\\w\\s\\*]*)\\s+");
        Scanner scanner = new Scanner(new File(filename));
        List<Response> actionList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher responseMatcher = responsePattern.matcher(line);
            if (responseMatcher.find()) {
                Matcher kvMatcher = kvPattern.matcher(line);
                Response response = new Response();
                response.response = responseMatcher.group(1);
                while (kvMatcher.find())
                    response.condition.put(kvMatcher.group(1), kvMatcher.group(2));
                actionList.add(response);
            }
        }
        return actionList;
    }

    /**
     *
     * @param input This is the input string given by the user
     * @param rulesHashmap Hashmap for the input string when it is being parsed where the keys represent the non-terminals
     *                     and values are the contents of the input string.
     * @param filename is the path of where the grammar text file is located
     * @return A response to the provided user input
     * @throws IOException
     */
    public static String matchInputWithAction(List<String> input, Map<String, String> rulesHashmap, String filename,
                                              Map<String, List<String>> grammar)
            throws IOException {
        List<Response> actionsList = parseActionsFromFile(filename);
        int match = 0;
        int valueCounter = 0;
        int highestValueCounter = 0;
        int highestMatch = -1;
        String response = "";

        for (int i = 0; i < actionsList.size(); i++) {
            for (Map.Entry<String, String> entry : actionsList.get(i).getCondition().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (rulesHashmap.containsKey(key) && rulesHashmap.get(key).equals(value) || rulesHashmap.containsKey(key) && value.equals("*")) {
                    match++;
                }
                if(value != null){
                    valueCounter++;
                }
            }
            if (match > highestMatch) {
                highestMatch = match;
                highestValueCounter = valueCounter;
                response = actionsList.get(i).getResponse();
            }
            match = 0;
            valueCounter = 0;

        }
        return highestMatch == highestValueCounter ? response : "I have no idea.";
    }

    /**
     *
     * @param input is the input string given by the user
     * @param filename is the path of where the grammar text file is located
     * @return This combines the parser for rules and actions to make the CFG and
     *         gives the response
     * @throws IOException
     */
    public static String respond(String input, String filename) throws IOException {
        Map<String, List<String>> grammar = parseGrammarFromFile(filename);
        Map<String, String> rulesHashmap = new HashMap<>();
        input = input.replaceAll("\\.", " .").replaceAll("\\?", " ?");
        boolean match = hasMatch(Arrays.stream(input.split("\\s+")).toList(), "<S>", grammar, rulesHashmap) != 0;
        System.out.println(match);
        System.out.println(rulesHashmap);
        if (match) {
            return matchInputWithAction(Arrays.stream(input.split("\\s+")).toList(), rulesHashmap, filename, grammar);
        }
        return "I have no Idea";
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            System.out.println(respond("my mother is in New York tomorrow. What is the weather?",
                    "src/main/java/Logic/CFG/grammar.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
