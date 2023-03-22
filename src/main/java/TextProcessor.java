import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
	public static String[] propositions = new String[]{"aboard" ,"about" ,"above" ,"across" ,"after" ,"against" ,"along" ,"amid" ,"among" ,"anti" ,"around" ,"as" ,"at" ,"before" ,"behind" ,"below" ,"beneath" ,"beside" ,"besides" ,"between" ,"beyond" ,"but" ,"by" ,"concerning" ,"considering" ,"despite" ,"down" ,"during" ,"except" ,"excepting" ,"excluding" ,"following" ,"for" ,"from" ,"in" ,"inside" ,"into" ,"like" ,"minus" ,"near" ,"of" ,"off" ,"on" ,"onto" ,"opposite" ,"outside" ,"over" ,"past" ,"per" ,"plus" ,"regarding" ,"round" ,"save" ,"since" ,"than" ,"through" ,"to" ,"toward" ,"towards" ,"under" ,"underneath" ,"unlike" ,"until" ,"up" ,"upon" ,"versus" ,"via" ,"with" ,"within" ,"without"};
	private static String[] questionPronouns = new String[]{"when", "where", "who", "whom", "which", "why", "what"};
	private static ArrayList<String> pro= new ArrayList<String>(List.of(propositions));
	private static ArrayList<String> qp= new ArrayList<String>(List.of(questionPronouns));
//	private static ArrayList<String> verbs = csv("src/main/java/verbs.csv");
	private static ArrayList<String> verbs = new ArrayList<>();
	private static ArrayList<String> stopWords = csv("src/main/java/stopwords.csv");


	public static Boolean isQuestion(String question) {
		question = question.toLowerCase(Locale.ROOT);

		for (String questionPronoun : questionPronouns) {
			if (question.contains(questionPronoun)) {
				return true;
			}
		}
		return false;
	}
	public static ArrayList<String> removePunctuations(String question){
		Pattern pattern = Pattern.compile("[^A-z0-9\\s]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(question);
		question = matcher.replaceAll(" ").toLowerCase(Locale.ROOT);
		String[] q = question.split("[*\s]");
		ArrayList<String> split = new ArrayList<>(Arrays.asList(q));
		return split;
	}


	public static ArrayList<String> splitSentence(String question){
		ArrayList<String> splitNP = removePunctuations(question);
		ArrayList<String> split = new ArrayList<>();
		for (String word : splitNP) {
			if (!word.isEmpty()
					&& !pro.contains(word)
					&& !qp.contains(word)
					//&& !verbs.contains(word)
					&& !stopWords.contains(word)
			) {
				split.add(word);
			}
		}

		return split;
	}
	

	public static ArrayList<String> removeNoVocabulary(String question,ArrayList<String>voc){
		ArrayList<String> splitNP = removePunctuations(question);
		ArrayList<String> split = new ArrayList<>();
		for (String word : splitNP) {
			if (!word.isEmpty() && voc.contains(word)) {
				split.add(word);
			}
		}

		return split;
	}

	public static ArrayList<String> csv(String filename){
		ArrayList<String> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split("[,]");
				records.addAll(Arrays.asList(values));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}
}
