import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
	public static String[] propositions = new String[]{"aboard" ,"about" ,"above" ,"across" ,"after" ,"against" ,"along" ,"amid" ,"among" ,"anti" ,"around" ,"as" ,"at" ,"before" ,"behind" ,"below" ,"beneath" ,"beside" ,"besides" ,"between" ,"beyond" ,"but" ,"by" ,"concerning" ,"considering" ,"despite" ,"down" ,"during" ,"except" ,"excepting" ,"excluding" ,"following" ,"for" ,"from" ,"in" ,"inside" ,"into" ,"like" ,"minus" ,"near" ,"of" ,"off" ,"on" ,"onto" ,"opposite" ,"outside" ,"over" ,"past" ,"per" ,"plus" ,"regarding" ,"round" ,"save" ,"since" ,"than" ,"through" ,"to" ,"toward" ,"towards" ,"under" ,"underneath" ,"unlike" ,"until" ,"up" ,"upon" ,"versus" ,"via" ,"with" ,"within" ,"without"};
	private static String[] questionPronouns = new String[]{"when", "where", "who", "whom", "which", "why", "what"};

	public static String regExForSearching(String template) {

		ArrayList<String> partitionedTemplate = splitSentenceRegexSearch(template);
		String s = splitArrayToString(partitionedTemplate);
		Pattern pattern = Pattern.compile("\\s*<.+?>\\s*");
		Matcher matcher = pattern.matcher(s);

		return "("+matcher.replaceAll("|")+")";
	}

	public static Boolean isQuestion(String question) {
		question = question.toLowerCase(Locale.ROOT);

		for (String questionPronoun : questionPronouns) {
			if (question.contains(questionPronoun)) {
				return true;
			}
		}
		return false;
	}
	public static ArrayList<String> splitSentenceRegexSearch(String sentence){
//		Pattern pattern = Pattern.compile("([^A-z0-9\\s])", Pattern.CASE_INSENSITIVE);
			Pattern pattern = Pattern.compile("[^\\w\\s<>\\d]", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(sentence);
			sentence = matcher.replaceAll(" ").toLowerCase(Locale.ROOT);
			ArrayList<String> split = new ArrayList<>();
			String[] q = sentence.split("\s+");
			Collections.addAll(split, q);
			return split;
	}
	public static ArrayList<String> splitSentence(String question){
		Pattern pattern = Pattern.compile("[^A-z0-9\\s]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(question);
		question = matcher.replaceAll(" ").toLowerCase(Locale.ROOT);
		System.out.println(question);
		ArrayList<String> split = new ArrayList<>();
		String[] q = question.split("[*\s]");
		ArrayList<String> pro= new ArrayList<String>(List.of(propositions));
		ArrayList<String> qp= new ArrayList<String>(List.of(questionPronouns));
		for (String word : q) {
			if (!word.isEmpty()
//					&& !pro.contains(word)&&!qp.contains(word)
			) {
				split.add(word);
			}
		}

		return split;
	}

	public static String splitArrayToString(ArrayList<String> splitSentence){
		StringBuilder combinedString = new StringBuilder();
		for (int i = 0 ; i < splitSentence.size() ; i++){
			combinedString.append(splitSentence.get(i));
			if(i < splitSentence.size()-1)
				combinedString.append(" ");
		}
		return combinedString.toString();
	}

	public static void main(String[] args) {
		String q = "Which lectures are there on Monday at 9?";
//		String temp = "Which lectures are there on <DAY> at <TIME>?";
//		System.out.println(regExForSearching(temp));
		System.out.println(splitSentence(q));
		System.out.println(isQuestion(q));
		System.out.println();

	}
}
