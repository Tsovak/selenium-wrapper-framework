package selenium.browsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tsovak_Sahakyan.
 */
public class NetworkLog{


    private final List<LogEntry> sourceLogs;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public NetworkLog(List<LogEntry> sourceLogs) {

        this.sourceLogs = sourceLogs;
    }

    public List<JSONObject> getApiMessages() {
        final JSONParser parser = new JSONParser();
        return sourceLogs.stream().
            filter(log ->
                       (log.getMessage().contains("\"method\":\"Network.requestWillBeSent\"") || log.getMessage().contains("\"method\":\"Network.responseReceived\""))
                       && log.getMessage().contains("\"type\":\"XHR\"")).
            map(log -> {
                try {
                    return getTargetMessage((JSONObject) parser.parse(log.getMessage()));
                } catch (ParseException ignored) {
                }
                return null;
            }).collect(Collectors.toList());
    }

    public List<String> getPrettyApiMessages() {

        return getApiMessages().stream().map(gson::toJson).collect(Collectors.toList());
    }

    private JSONObject getTargetMessage(JSONObject json) {

        JSONObject msg = ((JSONObject) ((JSONObject) json.get("message")).get("params"));
        msg.remove("initiator");
        return msg;
    }
}
