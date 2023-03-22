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
        System.out.println(voc.toString());
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

			ArrayList<String> ruleKeywords = vocabulary.makeVocabulary(oneTemplate);

			if(compareIfMatch(questionKeywords,ruleKeywords)){
				return template;
			}
		}
		return null;

	}

	public boolean compareIfMatch(ArrayList<String> questionKeywords , ArrayList<String> ruleKeywords){

		boolean[] matchArray = new boolean[questionKeywords.size()];
		// System.out.println("Keywords Question" + questionKeywords);
		// System.out.println("Keywords Rule" + ruleKeywords);
		// System.out.println("--------");
		for (int i = 0; i < questionKeywords.size(); i++) {
			if (ruleKeywords.contains(questionKeywords.get(i))) {
				matchArray[i] = true;
			}
		}
		for (int i = 0; i < matchArray.length; i++) {
			if(!matchArray[i])
				return false;
		}
		return true;
	}
	public ArrayList<String> getQuestionAsList(String question){
		return TextProcessor.removeNoVocabulary(question, voc);
	}
	
}
