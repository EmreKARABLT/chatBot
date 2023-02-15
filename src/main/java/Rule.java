import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
	public String template;
	public HashMap< String, ArrayList<String> > slots;
	public ArrayList<String> actions;
	public ArrayList< ArrayList<String> > pairedValues = new ArrayList<>();
	public ArrayList<String> parsedRules = new ArrayList<>();


	public Rule(String template, HashMap<String, ArrayList<String>> slots , ArrayList<String> actions){
		this.template = template.toLowerCase(Locale.ROOT);
		this.slots = slots;
		this.actions = actions;
		pairValues(this.parsedRules,0);
		parseRule();
	}

	public String replaceKeyWithValue(String rule , String key, String value){
		Pattern pattern = Pattern.compile(String.format("<%s>", key), Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(rule);
		return matcher.replaceAll(value).toLowerCase(Locale.ROOT);
	}
	public ArrayList<String> parseRule(){
		parsedRules = new ArrayList<>();
		String[] keys = slots.keySet().toArray(new String[0]);
		for (int i = 0; i < pairedValues.size(); i++) {
			String text = template;
			for (int j = 0 ; j < keys.length ; j++ ) {
				text = replaceKeyWithValue(text , keys[j] , pairedValues.get(i).get(j));
			}
			parsedRules.add(text);
		}
		return parsedRules;
	}
	private void pairValues(ArrayList<String> currentPair, int currentKeyIndex) {
		if (currentPair.size() == slots.size()) {
			pairedValues.add(currentPair);
			return;
		}
		ArrayList<String> keys = new ArrayList<>(slots.keySet());
		for (int i = currentKeyIndex; i < keys.size(); i++) {
			String key = keys.get(i);
			for (String value : slots.get(key)) {
				ArrayList<String> newPair = new ArrayList<>(currentPair);
				newPair.add(value);
				pairValues(newPair, i + 1);
			}
		}
	}
	public boolean isMatched(String question){
		for (int i = 0; i < parsedRules.size(); i++) {
			if(parsedRules.get(i).contains(question.toLowerCase(Locale.ROOT))){
				return true;
			}
		}
		return false;
	}

	public boolean isMatchedUsingRegex(String question){
//		System.out.println(TextProcessor.regExForSearching(template));
		Pattern pattern = Pattern.compile(TextProcessor.regExForSearching(question),Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(question);
//		System.out.println(matcher);
		return true;
	}

	public String findAction(String question){
		return " ";
	}
	public void addSlot(String key, ArrayList<String> values){
		slots.put(key , values);
		parseRule();
	}
	public void removeSlot(String key, ArrayList<String> value){
		slots.remove(key,value);
		parseRule();
	}

	public static void main(String[] args) {
		HashMap<String , ArrayList<String>> slots = new HashMap<>();
		slots.put("day",new ArrayList<String>(Arrays.asList("Monday","Tuesday")));
		slots.put("time",new ArrayList<String>(Arrays.asList("1","2")));

		Rule rule = new Rule("Which lectures are there on <DAY> at <TIME>", slots , null );
		System.out.println(rule.isMatchedUsingRegex("Which lectures are there on Monday"));
	}
}

