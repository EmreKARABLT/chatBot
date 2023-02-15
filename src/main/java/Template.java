import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {
	public String template;
	public HashMap< String, ArrayList<String>> slots;
	public ArrayList<String> actions;


	public Template(String template, HashMap<String, ArrayList<String>> slots , ArrayList<String> actions){
		this.template = template.toLowerCase(Locale.ROOT);
		this.slots = slots;
		this.actions = actions;
	}



}
