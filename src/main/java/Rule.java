import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
	public String template;
	public HashMap<String,ArrayList<String>> slotsAndActions;
	public Rule(String template, HashMap<String, ArrayList<String>> slotsAndActions){
		this.template = template.toLowerCase(Locale.ROOT);
		this.slotsAndActions = slotsAndActions;
	}
	public ArrayList<String> parseRule(){
		ArrayList<String> parsedStrings = new ArrayList<>();
		for (String key : slotsAndActions.keySet()) {
			if(!key.equalsIgnoreCase("action")){
				for (String value :
						slotsAndActions.get(key)) {
					Pattern pattern = Pattern.compile(String.format("<%s>",key), Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(template);
					System.out.println(value);
					String parsedString = matcher.replaceFirst(value).toLowerCase(Locale.ROOT);
					parsedStrings.add(parsedString);
				}
			}
		}
		return parsedStrings;
	}
	public boolean isMatched(String question){
		// Check that if a given input(question) is matched with parsed rule ( ruleParser )
		return false;
	}
	public String findAction(String question){
		// Among the all actions find the one which is suitable and return it
		return " ";
	}

	public static void main(String[] args) {
		HashMap<String , ArrayList<String>> slotsAndActions = new HashMap<>();
		slotsAndActions.put("day",new ArrayList<String>(Arrays.asList("Monday","Tuesday","Friday","Saturday")));

		Rule rule = new Rule("today is <DAY>", slotsAndActions );
		System.out.println(rule.parseRule());
	}
}

