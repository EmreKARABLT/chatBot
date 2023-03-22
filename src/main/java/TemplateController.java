import java.io.FileNotFoundException;
import java.util.*;

public class TemplateController {
	ArrayList<SkillTemplate> templates = new ArrayList<>();
	ArrayList<String> voc;
	Vocabulary vocabulary;
	ArrayList<String> questionKeywords;

	public TemplateController() throws FileNotFoundException{
		templates = SkillParser.getAllSkills();
		vocabulary = new Vocabulary();
        voc = vocabulary.makeVocabulary(templates);

	}
	/**
	 * This method iterates over all the rules and check if the given question matches with any rule's template
	 * @param question given question
	 * @return if there is a match it returns the matched rules , return NULL otherwise.
	 */
	public SkillTemplate getMatchedRule(String question){
		for (SkillTemplate template : this.templates) {
			questionKeywords = getQuestionAsList(question);

			if(questionKeywords.isEmpty()){
				return null;
			}
			ArrayList<SkillTemplate> oneTemplate = new ArrayList<>();
			oneTemplate.add(template);
			System.out.println();
			ArrayList<String> ruleKeywords = vocabulary.makeVocabulary(oneTemplate);

			if(compareIfMatch(ruleKeywords)){

				return template;
			}
		}
		return null;

	}

	public boolean compareIfMatch(ArrayList<String> ruleKeywords){

		boolean[] matchArray = new boolean[questionKeywords.size()];
		ArrayList<String> toRemove = new ArrayList<>();
		for (int i = 0; i < questionKeywords.size(); i++) {
			if (ruleKeywords.contains(questionKeywords.get(i))) {
				matchArray[i] = true;
			}
			else if(ruleKeywords.contains(questionKeywords.get(i).replace(" ",""))){
				matchArray[i] = true;
			}
			else{
				for (int j = 0; j < ruleKeywords.size(); j++) {
					if(ruleKeywords.get(j).contains(questionKeywords.get(i))){
						matchArray[i] = true;
						toRemove.add(questionKeywords.get(i));
						break;
					}
				}
			}
		}
		questionKeywords.removeAll(toRemove);
		System.out.println(questionKeywords);
		for (int i = 0; i < matchArray.length; i++) {
			if(!matchArray[i])
				return false;
		}
		return true;
	}
	public ArrayList<String> getQuestionAsList(String question){
		return TextProcessor.removeNoVocabulary(question, voc);
	}

	public ArrayList<String> getQuestionKeywords(){
		return questionKeywords;
	}
	public ArrayList<String> parseAllTemplatesToStrings(){
		ArrayList<String> allParsedTemplates = new ArrayList<>();
		for (SkillTemplate template: templates) {

			allParsedTemplates.addAll(template.getParsedRules());
		}
		return allParsedTemplates;
	}
	public String didYouMean(String question){
		ArrayList<String> allParsedTemplates = parseAllTemplatesToStrings();
		String bestMatch = "";
		int minDistance = 100;
		for (String parsedRule: allParsedTemplates) {
			int distance = LevenshteinDistance.computeLDistance(parsedRule,question);
			System.out.println(parsedRule + "--- " +  distance +" ---" + question );
			if(distance<minDistance){
				bestMatch = parsedRule;
				minDistance = distance;
			}
		}
		if(minDistance > 0 && minDistance < 10){
			Scanner scan  = new Scanner(System.in);
			System.out.println("did you mean ?(Y/N)\n" + bestMatch );
			String answer = scan.nextLine();
			if(answer.equalsIgnoreCase("yes" ) ||answer.equalsIgnoreCase("y")){
				return bestMatch;
			}
		}
		return question;
	}

}
