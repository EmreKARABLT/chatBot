package com.Logic;

import java.util.*;

public class RuleController {
	ArrayList<Rule> rules = new ArrayList<>();
	//TODO find a matching action
	public RuleController(){}



	/**
	 * This method iterates over all the rules and check if the given question matches with any rule's template
	 * @param question given question
	 * @return if there is a match it returns the matched rules , return NULL otherwise.
	 */
	public Rule getMatchedRule(String question){
		for (Rule rule : this.rules) {
			ArrayList<String> questionKeywords = getKeywords(rule,question);
			ArrayList<String> ruleKeywords = getKeywords(rule,rule.template);

			if(compareIfMatch(questionKeywords,ruleKeywords)){
				return rule;
			}
		}
		return null;

	}

	public boolean compareIfMatch(ArrayList<String> questionKeywords , ArrayList<String> ruleKeywords){

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

//	public String parseQuestion(String question){
//		question = question.toLowerCase(Locale.ROOT);
//		for (Rule rule : rules) {
//			HashMap<String, ArrayList<String>> slots = rule.getSlots();
//			for (String key : slots.keySet()) {
//				for (String value : slots.get(key)) {
//					Pattern pattern = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
//					Matcher matcher = pattern.matcher(question);
//					question = matcher.replaceAll("<" + key + ">");
//				}
//			}
//		}
//		return question;
//	}

	public ArrayList<String> getKeywords(Rule rule , String question) {

		ArrayList<String> split = TextProcessor.splitSentence(question);
		for (int i = 0 ; i < split.size() ; i++) {
			String word = split.get(i);
			if ( rule.categorizeWord( word ) != null ) {
				split.set(i,rule.categorizeWord(word).get(1));
				System.out.println(rule.categorizeWord(word).get(1) + " " + rule.categorizeWord(word).get(0));
			}
		}
		return split;
	}
	public HashMap<String , String > getHashMapForActions(Rule rule , String question){
		ArrayList<String> split = TextProcessor.splitSentence(question);
		HashMap<String , String> placeholdersAndCorrespondingKeywords = new HashMap<>();
		for (int i = 0 ; i < split.size() ; i++) {
			String word = split.get(i);
			if ( rule.categorizeWord( word ) != null ) {
				placeholdersAndCorrespondingKeywords.put(rule.categorizeWord(word).get(1) , rule.categorizeWord(word).get(0));
			}
		}
		return placeholdersAndCorrespondingKeywords;
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

		Rule template2 = new Rule("Turn the <ROOM> light to <COLOR>",
						new HashMap<>(){{
							put("room" , new ArrayList<String>(List.of(new String[]{"livingroom","bedroom","hall"})));
							put("color" , new ArrayList<String>(List.of(new String[]{"red","blue","green","white","saturday"})));
						}},
				null);
		controller.addRule(template1);
		controller.addRule(template2);

		String question = "what lectures are there on monday at 13";
		Rule rule = controller.getMatchedRule(question);
		System.out.println(rule);
		HashMap<String , String > hm= (rule!= null ) ? controller.getHashMapForActions(rule,question) : null;
		System.out.println(hm);

	}
}
