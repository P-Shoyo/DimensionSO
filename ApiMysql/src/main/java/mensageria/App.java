package mensageria;

import java.io.IOException;
import org.json.JSONObject;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        
        json.put("text", "F�cil n�? :shrug:");
        
        Slack.sendMessage(json);
    }
}
