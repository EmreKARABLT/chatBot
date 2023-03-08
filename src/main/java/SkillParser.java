import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class SkillParser {
    // method that reads a txt file line by line in db folder
    public static void main(String[] args) throws FileNotFoundException {
        readSkillsFromFile("skill");
        // System.out.println(readRawTextFromFile("skill"));
        System.out.println(getAllSkills());
        // writeToFile("test", "Question Which lectures are there on <DAY> at <TIME> \n Slot <DAY> Monday");
    }

    public static void writeToFile(String filename, String content) {
        try {
            File file = new File("src/main/java/db/" + filename + ".txt");
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            fw.write(content);

            fw.close();
        } catch (Exception e) {
            System.out.println("Error while writing to file");
        }

    }

    public static ArrayList<String> getAllSkills() {

        File folder = new File("src/main/java/db/");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> skills = new ArrayList<String>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                //remove .txt from fileName
                fileName = fileName.substring(0, fileName.length() - 4);
                skills.add(fileName);
            } 
        }
        return skills;
    }

    public static String readRawTextFromFile(String filename) {
        String content = "";
        try {
            File file = new File("src/main/java/db/" + filename + ".txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                content += myReader.nextLine() + "\n";
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("Error while reading from file");
        }
        return content;
    }

    public static SkillTemplate readSkillsFromFile(String filename) throws FileNotFoundException {
        File myObj = new File("src/main/java/db/" + filename + ".txt");
        Scanner myReader = new Scanner(myObj);

        String question = "";
        ArrayList<String> keywords = new ArrayList<String>();
        ;
        HashMap<String, ArrayList<String>> slots = new HashMap<String, ArrayList<String>>();
        ArrayList<Action> actions = new ArrayList<Action>();
        boolean foundQuestion = false;
        while (myReader.hasNextLine()) {
            String str = myReader.nextLine();
            String firstWord = str.split(" ")[0];

            // Parsing question
            if (firstWord.equals("Question") && !foundQuestion) {
                // remove first word from str
                str = str.substring(str.indexOf(" ") + 1);
                question = str;
                foundQuestion = true;
                System.out.println("question :" + str);

                // find all the words that are between <> in question and add them to keywords

                while (str.contains("<")) {
                    String keyword = str.substring(str.indexOf("<") + 1, str.indexOf(">"));
                    keywords.add(keyword);
                    str = str.substring(str.indexOf(">") + 1);

                    slots.put(keyword, new ArrayList<String>());
                }
            }

            // Parsing Slots
            if (firstWord.equals("Slot")) {
                String slotName = str.split(" ")[1];
                // remove < and > symbols from slotName
                slotName = slotName.substring(1, slotName.length() - 1);
                // System.out.println("slotName :" + slotName);

                String value = str.substring(str.indexOf(">") + 2);
                // System.out.println("value :" + value);
                slots.get(slotName).add(value);
            }

            // Parsing Actions
            if (firstWord.equals("Action")) {
                Action action = new Action();
                str = str.substring(str.indexOf(" ") + 1);
                System.out.println("action :" + str);
                while (str.contains("<")) {
                    String keyword = str.substring(str.indexOf("<") + 1, str.indexOf(">"));
                    // System.out.println(keyword);

                    str = str.substring(str.indexOf(">") + 2);
                    String value = str.split(" ")[0];
                    // System.out.println(value);
                    action.slotValues.put(keyword, value);

                    // remove the value keyword
                    str = str.substring(str.indexOf(" ") + 1);

                }
                action.setAnswer(str);
                actions.add(action);
                // System.out.println("action " + action);

            }

        }

        SkillTemplate skill = new SkillTemplate(question, keywords, slots, actions);
        myReader.close();
        System.out.println(skill);
        return skill;
    }
}