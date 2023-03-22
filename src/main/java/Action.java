import java.util.ArrayList;
import java.util.HashMap;

public class Action {
    public HashMap<String, String> slotValues = new HashMap<String, String>();
    public String answer;

    public Action(HashMap<String, String> slotValues, String answer) {
        this.slotValues = slotValues;
        this.answer = answer;
    }

    public Action() {

    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public boolean ifMatched(HashMap<String , String> question){
        if(question.equals(slotValues))
            return true;
        return false;
    }
    @Override
    public String toString() {
        return "\nAction : " +
                "slotValues=" + slotValues +
                "\n answer='" + answer + '\'';

    }

}
