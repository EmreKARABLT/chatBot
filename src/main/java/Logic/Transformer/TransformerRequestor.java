package Logic.Transformer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TransformerRequestor {
    public String getResponse(String userinput){
        String outp = "error";
        try {
            // Create the URL object with the endpoint
            URL url = new URL("http://localhost:5000/data");

            // Create the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");

            // Enable input and output streams
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Set the request headers
            connection.setRequestProperty("Content-Type", "application/json");

            // Define the request payload
            String payload = "{\"key\": \""+userinput+"\"}";

            // Send the request body
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            // Read the response
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String responseLine;
                StringBuilder response = new StringBuilder();
                while ((responseLine = bufferedReader.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                 outp = response.toString();
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outp;
    }
}
