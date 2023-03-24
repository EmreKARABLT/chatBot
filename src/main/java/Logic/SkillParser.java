package Logic;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class SkillParser {


    public static void main(String[] args) throws FileNotFoundException  {
        System.out.println(readSkillsFromFile("skill"));
    }
    public static ArrayList<SkillTemplate> getAllSkills() throws FileNotFoundException {
        ArrayList<SkillTemplate> templates = new ArrayList<>();
        ArrayList<String> paths = getAllSkillPaths();
        for (int i = 0; i < paths.size(); i++) {
            templates.add(readSkillsFromFile(paths.get(i)));
        }
        return templates;
    }


   
    /**
	 * It writes everything that is in content to a txt file called filename
	 * @param filename the string which will be used for the filename, dont put .txt at the end

     * @param content the content that will be in the .txt file
	 */
    public static void writeToFile(String filename, String content) {
        try {
            File file = new File("src/main/java/Logic/db/" + filename + ".txt");
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            fw.write(content);

            fw.close();
        } catch (Exception e) {
            System.out.println("Error while writing to file");
        }

    }


    /**
	 * It reads all the filenames in the db folder and returns them in an ArrayList
	 * @return returns the filenames without the .txt extension
	 */
    public static ArrayList<String> getAllSkillPaths() {

        File folder = new File("src/main/java/Logic/db/");
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


    /**
	
	 * @return it returns the raw text from a file in the db folder
	 */
    public static String readRawTextFromFile(String filename) {
        String content = "";
        try {
            File file = new File("src/main/java/Logic/db/" + filename + ".txt");
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


    /**
	 * It reads a skill from a txt file in the db folder, and parses all the information in it, 
     * to get an easy to use SkillTemplate object
	 * @param filename the name of the skill file you want to parse
	 * @return it returns to the SkillTemplate object with all the information about the Skill
	 */
    public static SkillTemplate readSkillsFromFile(String filename) throws FileNotFoundException {
        File myObj = new File("src/main/java/Logic/db/" + filename + ".txt");
        Scanner myReader = new Scanner(myObj);

        String question = "";
        ArrayList<String> keywords = new ArrayList<String>();
        ;
        HashMap<String, ArrayList<String>> slots = new HashMap<String, ArrayList<String>>();
        ArrayList<Action> actions = new ArrayList<Action>();
        boolean foundQuestion = false;
        while (myReader.hasNextLine()) {
            String str = myReader.nextLine().toLowerCase();
            String firstWord = str.split(" ")[0];

            // Parsing question
            if (firstWord.equals("question") && !foundQuestion) {
                // remove first word from str
                str = str.substring(str.indexOf(" ") + 1);
                question = str;
                foundQuestion = true;

                // find all the words that are between <> in question and add them to keywords

                while (str.contains("<")) {
                    String keyword = str.substring(str.indexOf("<") + 1, str.indexOf(">"));
                    keywords.add(keyword);
                    str = str.substring(str.indexOf(">") + 1);

                    slots.put(keyword, new ArrayList<String>());
                }
            }

            // Parsing Slots
            if (firstWord.equals("slot")) {
                String slotName = str.split(" ")[1];
                // remove < and > symbols from slotName
                slotName = slotName.substring(1, slotName.length() - 1).replace(" ","");
                // System.out.println("slotName :" + slotName);

                String value = str.substring(str.indexOf(">") + 2);
                // System.out.println("value :" + value);
                slots.get(slotName).add(value.replace(" ",""));
            }

            // Parsing Actions
            if (firstWord.equals("action")) {
                Action action = new Action();
                str = str.substring(str.indexOf(" ") + 1);
                while (str.contains("<")) {
                    String keyword = str.substring(str.indexOf("<") + 1, str.indexOf(">"));
                    // System.out.println(keyword);

                    str = str.substring(str.indexOf(">") + 2);
                    
                    if(slots.get(keyword) == null) {
                        System.out.println("Error in file " + filename + " slot " + keyword + " is not defined");
                        System.exit(0);
                    }
                    for (int i = 0; i < slots.get(keyword).size(); i++) {
                        String slotValue = slots.get(keyword).get(i);
                        // System.out.println("Slot value " + slotValue);

                        if (str.replace(" ","").startsWith(slotValue)) {
//                            System.out.println("Slot value " + slotValue);
                            action.slotValues.put(keyword, slotValue);
                            // // remove the value keyword
                            str = str.substring(str.indexOf(slotValue) + slotValue.length() );
                            break;
                        }
                    }

                }
                action.setAnswer(str);
                actions.add(action);
                // System.out.println("action " + action);

            }

        }

        SkillTemplate skill = new SkillTemplate(question, keywords, slots, actions);
        myReader.close();
        return skill;
    }
    public static void deleteFile(String filename){
        File myFile = new File("src/main/java/Logic/db/" + filename + ".txt");
        myFile.delete();
    }
}
