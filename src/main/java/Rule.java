import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule{
	public String template;
	public HashMap< String, ArrayList<String> > slots;
	public HashMap< String, ArrayList<String> > actions;


	public Rule(String template, HashMap<String, ArrayList<String>> slots , HashMap<String, ArrayList<String>> actions){
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

	public HashMap<String, ArrayList<String>> getActions() {
		return actions;
	}

	public void setActions(HashMap<String, ArrayList<String>> actions) {
		this.actions = actions;
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
