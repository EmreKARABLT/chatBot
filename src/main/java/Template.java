import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	public String categorizeWord(String word){
		for (String key : slots.keySet()) {
			for (String value :	slots.get(key)) {
				Pattern pattern = Pattern.compile(value ,Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(word);
				if(matcher.matches())
				 return matcher.replaceAll("<"+key+">" );
			}
		}
		return null;
	}
	public String parseQuestion(String question){
		question = question.toLowerCase(Locale.ROOT);
		for (String key : slots.keySet()) {
			for (String value :	slots.get(key)) {
				Pattern pattern = Pattern.compile(value ,Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(question);
				question = matcher.replaceAll("<"+key+">" );
			}

		}
		return question;
	}

	public static void main(String[] args) {
		String temp = "Which lectures are there on <DAY> at <TIME>?";
		HashMap<String , ArrayList<String> > slots = new HashMap<>(){{
			put("day" , new ArrayList<String>(List.of(new String[]{"monday","saturday"})));
			put("time" , new ArrayList<String>(List.of(new String[]{"13","15"})));
		}};
		Template template = new Template(temp, slots, null);

		System.out.println(template.parseQuestion("which lectures are there on Monday at 13"));
		System.out.println(template.categorizeWord("13"));
		System.out.println(template.categorizeWord("monday"));
		System.out.println(template.categorizeWord("sunday"));
	}


}
