package Logic.CFG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GrammerEditor {
    List<String> rules = new LinkedList<>();
    List<String> actions = new LinkedList<>();
    public GrammerEditor(){
        try {
            Scanner s = new Scanner(new File("src/main/java/Logic/CFG/grammar.txt"));
            while(s.hasNextLine()){
                String line = s.nextLine();
                if(line.split(" ")[0].equals("Action")){
                    actions.add(line);
                }else if (line.split(" ")[0].equals("Rule")){
                    rules.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getRules(){
        return rules;
    }
    public List<String> getActions(){
        return actions;
    }

    public void writeToFile(){
        String write = "";
        for (String s: rules) {
            write += s + "\n";
        }
        for (String s: actions) {
            write += s + "\n";
        }
        try {
            FileWriter w = new FileWriter("src/main/java/Logic/CFG/grammar.txt");
            w.write(write);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
