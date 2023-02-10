import java.util.HashMap;

public class Rule {
	public String template;
	public HashMap<String,String> slotsAndActions;
	public Rule(String template, HashMap<String,String> slotsAndActions){
		this.template = template;
		this.slotsAndActions = slotsAndActions;
	}
	public String parseRule(){
		// replace <DAY> with the days in slots , same for <TIME> and <LOCATION>
		// return parsed rule
		return " ";
	}
	public boolean isMatched(String question){
		// Check that if a given input(question) is matched with parsed rule ( ruleParser )
		return false;
	}
	public String findAction(String question){
		// Among the all actions find the one which is suitable and return it
		return " ";
	}
}
