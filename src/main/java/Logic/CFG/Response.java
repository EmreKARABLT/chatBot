package Logic.CFG;


import java.util.ArrayList;
import java.util.List;

public class Response {
    String condition;
    String response;

    static List<Response> actionsList = new ArrayList<>();

    public String getCondition(){
        return condition;
    }
    public String getResponse(){
        return response;
    }
}
