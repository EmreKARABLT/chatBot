import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule{
	public String template;
	public HashMap< String, ArrayList<String> > slots;
	public ArrayList < HashMap< String, ArrayList<String> >  > actions;


	public Rule(String template, HashMap<String, ArrayList<String>> slots , ArrayList< HashMap<String, ArrayList<String> > > actions){
		this.template = template.toLowerCase(Locale.ROOT);
		this.slots = slots;
		this.actions = actions;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public HashMap<String, ArrayList<String>> getSlots() {
		return slots;
	}

	public void setSlots(HashMap<String, ArrayList<String>> slots) {
		this.slots = slots;
	}

	/**
	 * It categorizes the given words by iterating over the slots of this rule
	 * @param word the string which will be categorized
	 * @return it returns to the category if it is found, otherwise returns NULL
	 */
	public  ArrayList<String> categorizeWord(String word){
			for (String key : slots.keySet()) {
				for (String value :	slots.get(key)) {
					Pattern pattern = Pattern.compile(value ,Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(word);
					if(matcher.matches())
						return new ArrayList<>(Arrays.asList(value, matcher.replaceAll(key)));
				}
			}
		return null;
	}

	/**
	 * This method compares given rule with "this" and checks if they are the same rules by comparing their template question
	 * @param o given Rule
	 * @return if templates of given rules are the same it returns TRUE , otherwise FALSE
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Rule rule = (Rule) o;
		return template.equals(rule.template);
	}

	@Override
	public int hashCode() {
		return Objects.hash(template);
	}

	@Override
	public String toString() {
		return "Rule{" +
				"template='" + template + '\'' +
				'}';
	}
}
