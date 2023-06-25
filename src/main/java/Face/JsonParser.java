package Face;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;

public class JsonParser {
    public HashMap<String , String > labels = new HashMap<>();

    public JsonParser() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ImageProcessing/Model/labels.json"));
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();

            Gson gson = new Gson();
            labels = gson.fromJson(reader, type);
            System.out.println(labels);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getName(int index){
        if(index == -1  || labels.isEmpty()) return "";

        return labels.get(index+"");
    }


}
