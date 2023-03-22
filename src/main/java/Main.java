import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        TemplateController controller = new TemplateController();
        Scanner scan  = new Scanner(System.in);
        System.out.println("What is your question?");
        String question = scan.nextLine();
        SkillTemplate template = controller.getMatchedRule(question);
        System.out.println(template);
        if(template!=null){
            System.out.println(template.getAction(controller.getQuestionKeywords()));
            System.out.println(controller.getQuestionKeywords());
        }
    }
}
