package Logic.CFG;
import java.util.HashMap;
import java.util.Map;

public class Response {
    Map<String, String> condition;
    String response;

    public Response(){
        condition = new HashMap<>();
    }

    public Map<String, String> getCondition(){
        return condition;
    }
    public String getResponse(){
        return response;
    }
}
