import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkillTemplate {
    private static HashMap<String,SkillTemplate> slotsToTemplate;
    private String question;
    private ArrayList<String> keywords;
    private HashMap< String, ArrayList<String> > slots;
    private ArrayList<Action> actions;
    private ArrayList< ArrayList<String> > pairedValues = new ArrayList<>();
    private ArrayList<String> parsedRules = new ArrayList<>();

    public SkillTemplate(String question, ArrayList<String> keywords, HashMap<String, ArrayList<String>> slots, ArrayList<Action> actions) {
        this.question = question;
        this.keywords = keywords;
        this.slots = slots;
        this.actions = actions;
        this.pairedValues = new ArrayList<>();
        this.parsedRules = new ArrayList<>();
        pairValues(this.parsedRules,0);
        parseRule();
    }

    public void addAllSlots(){

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public HashMap<String, ArrayList<String>> getSlots() {
        return slots;
    }

    public void setSlots(HashMap<String, ArrayList<String>> slots) {
        this.slots = slots;
    }

    public Action getAction(ArrayList<String>keyword){
        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            if(action.ifMatched(createHashAction(keyword)))
                return action;
        }
        return null;
    }

    public HashMap<String,String> createHashAction(ArrayList<String> question){
        
        HashMap<String,String> questionKeywords = new HashMap<>();
        for (String key: keywords) {
                ArrayList<String> values = slots.get(key);
                for(String value : values){
                    if(question.contains(value)){
                        questionKeywords.put(key,value);
                        break;
                    }
                }
        }
        
        return questionKeywords;

    }
    public String replaceKeyWithValue(String template , String key, String value){
        Pattern pattern = Pattern.compile(String.format("<%s>", key), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(template);
        return matcher.replaceAll(value).toLowerCase(Locale.ROOT);
    }
    public ArrayList<String> parseRule(){
        parsedRules = new ArrayList<>();
        String[] keys = slots.keySet().toArray(new String[0]);
        for (int i = 0; i < pairedValues.size(); i++) {
            String text = question;
            for (int j = 0 ; j < keys.length ; j++ ) {
                text = replaceKeyWithValue(text , keys[j] , pairedValues.get(i).get(j));
            }
            parsedRules.add(text);
        }
        return parsedRules;
    }
    public void pairValues(ArrayList<String> currentPair, int currentKeyIndex) {
        if (currentPair.size() == slots.size()) {
            pairedValues.add(currentPair);
            return;
        }
        ArrayList<String> keys = new ArrayList<>(slots.keySet());
        for (int i = currentKeyIndex; i < keys.size(); i++) {
            String key = keys.get(i);
            for (String value : slots.get(key)) {
                ArrayList<String> newPair = new ArrayList<>(currentPair);
                newPair.add(value);
                pairValues(newPair, i + 1);
            }
        }
    }

    public ArrayList<String> getParsedRules() {
        return parsedRules;
    }

    @Override
    public String toString() {
        return "SkillTemplate{" +
                "question='" + question +
//                 "\n keywords=" + keywords +
//                "\n slots=" + slots +
//                "\n actions=" + actions +
                '}';
    }




}
