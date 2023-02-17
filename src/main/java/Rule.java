import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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


}
