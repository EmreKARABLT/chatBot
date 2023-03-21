package com.Logic;
import java.util.ArrayList;
import java.util.HashMap;

public class SkillTemplate {
    private String question;
    private ArrayList<String> keywords;
    private HashMap< String, ArrayList<String> > slots;
    private ArrayList<Action> actions;

    public SkillTemplate(String question, ArrayList<String> keywords, HashMap<String, ArrayList<String>> slots, ArrayList<Action> actions) {
        this.question = question;
        this.keywords = keywords;
        this.slots = slots;
        this.actions = actions;
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

    @Override
    public String toString() {
        return "SkillTemplate{" +
                "question='" + question + '\'' +
                "\n keywords=" + keywords +
                "\n slots=" + slots +
                "\n actions=" + actions +
                '}';
    }


}
