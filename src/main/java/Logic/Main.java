package Logic;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        TemplateController controller = new TemplateController();
        Scanner scan  = new Scanner(System.in);
        System.out.println("What is your question?");
        String question = "which lectures are there on monday at 11";

        SkillTemplate template = controller.getMatchedRule(question);
        System.out.println("template KW " + template.getKeywords());

        System.out.println(template.getAction(controller.getQuestionKeywords()));
    }
}
