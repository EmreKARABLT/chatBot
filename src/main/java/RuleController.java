import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleController {
	ArrayList<Rule> rules = new ArrayList<>();
	public RuleController(){}


	public String categorizeWord(String word){
		for (Rule rule : rules) {
			HashMap<String, ArrayList<String>> slots = rule.getSlots();
			for (String key : slots.keySet()) {
				for (String value :	slots.get(key)) {
					Pattern pattern = Pattern.compile(value ,Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(word);

					if(matcher.matches())
						return matcher.replaceAll(key);
				}
			}
		}
		return null;
	}
	public String parseQuestion(String question){
		question = question.toLowerCase(Locale.ROOT);
		for (Rule rule : rules) {
			HashMap<String, ArrayList<String>> slots = rule.getSlots();
			for (String key : slots.keySet()) {
				for (String value : slots.get(key)) {
					Pattern pattern = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(question);
					question = matcher.replaceAll("<" + key + ">");
				}
			}
		}
		return question;
	}
	public ArrayList<String> getKeywordsAndPlaceholders(String question) {
		ArrayList<String> split = TextProcessor.splitSentence(question);
		for (int i = 0 ; i < split.size() ; i++) {
			String word = split.get(i);
			if (categorizeWord( word ) != null ) {
				split.set(i,categorizeWord(word));
			}
		}
		return split;
	}
	public void addRule(Rule rule){
		rules.add(rule);
	}
	public void removeRule(Rule rule){
		rules.remove(rule);
	}
	public void modifyRule(){} // TODO discuss what will be in this method with group


		public static void main(String[] args) {
		RuleController controller = new RuleController();

		Rule template1 = new Rule("Which lectures are there on <DAY> at <TIME>?",
						new HashMap<>(){{
							put("day" , new ArrayList<String>(List.of(new String[]{"monday","saturday"})));
							put("time" , new ArrayList<String>(List.of(new String[]{"13","15"})));
						}},
				null);

		Rule template2 = new Rule("Which lectures are there in <CLASSROOM> at <TIME>?",
						new HashMap<>(){{
							put("classroom" , new ArrayList<String>(List.of(new String[]{"C.016","C.020"})));
							put("time" , new ArrayList<String>(List.of(new String[]{"9","11"})));
						}},
				null);
		controller.addRule(template1);
		controller.addRule(template2);
		System.out.println(controller.categorizeWord("11"));
		System.out.println(controller.categorizeWord("monday"));
		System.out.println(controller.categorizeWord("sunday"));
		System.out.println(controller.getKeywordsAndPlaceholders("which lectures are there on saturday"));;
		System.out.println(controller.getKeywordsAndPlaceholders("Which lectures are there on <DAY> at <TIME>?"));;
		System.out.println(controller.getKeywordsAndPlaceholders("Which lectures are there in <CLASSROOM> at <TIME>?"));;

	}
}
