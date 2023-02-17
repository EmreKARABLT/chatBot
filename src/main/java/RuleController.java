import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleController {
	ArrayList<Rule> rules = new ArrayList<>();
	//TODO find a matching action
	public RuleController(){}

	/**
	 * It categorizes the given words by iterating over the slots of all existing rules
	 * @param word the string which will be categorized
	 * @return it returns to the category if it is found, otherwise returns NULL
	 */
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

	/**
	 * This method iterates over all the rules and check if the given question matches with any rule's template
	 * @param question given question
	 * @return if there is a match it returns the matched rules , return NULL otherwise.
	 */
	public Rule getMatchedRule(String question){
		ArrayList<String> questionKeywords = getKeywordsAndPlaceholders(question);
		ArrayList<String> ruleKeywords = new ArrayList<>();
		for (Rule rule : this.rules) {
			ruleKeywords = getKeywordsAndPlaceholders(rule.template);

			if(compareIfMatch(questionKeywords,ruleKeywords)){
				return rule;
			}
		}
		return null;

	}

	public boolean compareIfMatch(ArrayList<String> questionKeywords , ArrayList<String> ruleKeywords){
		//TODO write the code which checks that if "questionKeywords" and "ruleKeywords" matches
		boolean[] matchArray = new boolean[questionKeywords.size()];
		System.out.println("Keywords Question" + questionKeywords);
		System.out.println("Keywords Rule" + ruleKeywords);
		System.out.println("--------");
		for (int i = 0; i < questionKeywords.size(); i++) {
			if (ruleKeywords.contains(questionKeywords.get(i))) {
				matchArray[i] = true;
			}
		}
		for (int i = 0; i < matchArray.length; i++) {
			if(!matchArray[i])
				return false;
		}
		return true;
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

	/**
	 * This method adds the given rule to the list of rules
	 * @param rule the rule which will be added
	 */
	public void addRule(Rule rule){
		rules.add(rule);
	}

	/**
	 * This method removes the given rule from the list of rules
	 * @param rule the rule which will be removed
	 */
	public void removeRule(Rule rule){
		rules.remove(rule);
	}

	/**
	 * This method modifies the rule
	 */
	public void modifyRule(){} // TODO discuss what will be in this method with group


	public static void main(String[] args) {
		RuleController controller = new RuleController();

		Rule template1 = new Rule("Which lectures are there on <DAY> at <TIME>?",
						new HashMap<>(){{
							put("day" , new ArrayList<String>(List.of(new String[]{"monday","saturday"})));
							put("time" , new ArrayList<String>(List.of(new String[]{"13","15"})));
						}},
				null);

		Rule template2 = new Rule("When <ACTIVITY> is planned for <DAY>",
						new HashMap<>(){{
							put("activity" , new ArrayList<String>(List.of(new String[]{"skiing","running","cycling"})));
							put("Day" , new ArrayList<String>(List.of(new String[]{"Monday","Friday"})));
						}},
				null);
		controller.addRule(template1);
		controller.addRule(template2);
	//		System.out.println(controller.categorizeWord("11"));
	//		System.out.println(controller.categorizeWord("monday"));
	//		System.out.println(controller.categorizeWord("sunday"));
		System.out.println(controller.getMatchedRule("When running is planned"));;
//
	}
}
