import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Vocabulary {

    public  ArrayList<String> makeVocabulary(ArrayList<SkillTemplate> templates){
        ArrayList<String> vocabulary = new ArrayList<>();
        for (int i = 0; i < templates.size(); i++) {
            SkillTemplate template = templates.get(i);
            String question = template.getQuestion();
            ArrayList<String> keywords = template.getKeywords();
            HashMap<String,ArrayList<String>> slots  =template.getSlots();

            ArrayList<String> questionStrings = TextProcessor.splitSentence(question);
            vocabulary.addAll(questionStrings);
            for(int j = 0;j<keywords.size();j++){
                vocabulary.addAll(slots.get(keywords.get(j)));
            }

        }
        Set<String> set = new HashSet<>(vocabulary);
        vocabulary.clear();
        vocabulary.addAll(set);
        return vocabulary;
        
    }
}
