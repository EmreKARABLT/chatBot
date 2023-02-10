import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
	public static Boolean isQuestion(String question) {
		question = question.toLowerCase(Locale.ROOT);
		String[] questionPronouns = new String[]{"when", "where", "who", "whom", "which", "why", "what"};

		for (String questionPronoun : questionPronouns) {
			if (question.contains(questionPronoun)) {
				return true;
			}
		}
		return false;
	}
	public static ArrayList<String> splitSentence(String question){
		Pattern pattern = Pattern.compile("[^A-z0-9\\s]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(question);
		question = matcher.replaceAll(" ").toLowerCase(Locale.ROOT);
		System.out.println(question);
		ArrayList<String> split = new ArrayList<>();
		String[] q = question.split("[*\s]");
		for (String word : q) {
			if (!word.isEmpty()
//					&& !word.equalsIgnoreCase("for")
//					&& !word.equalsIgnoreCase("and")
//					&& !word.equalsIgnoreCase("that")
//					&& !word.equalsIgnoreCase("of")
//					&& !word.equalsIgnoreCase("in")
//					&& !word.equalsIgnoreCase("the")
//					&& !word.equalsIgnoreCase("a")
//					&& !word.equalsIgnoreCase("is")
//					&& !word.equalsIgnoreCase("are")
			) {
				split.add(word);
			}
		}

		return split;
	}

	public static void main(String[] args) {
		String q = "Sure! In regular expression, the special characters for punctuation and,space 10! are and p{P} respectively. Here is a regular expression that will match all punctuation and space characters:";
		System.out.println(splitSentence(q));
	}
}
