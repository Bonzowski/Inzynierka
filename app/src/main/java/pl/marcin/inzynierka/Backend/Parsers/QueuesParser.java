package pl.marcin.inzynierka.Backend.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Marcin on 06.11.2016.
 */

public class QueuesParser {

    public ArrayList<String> queueName = new ArrayList<>();
    public ArrayList<String> queueDesc = new ArrayList<>();
    public ArrayList<Integer> currentTicket = new ArrayList<>();
    public ArrayList<Integer> lastTicket = new ArrayList<>();


    public QueuesParser (JSONObject jsonObject) throws JSONException {

        JSONArray array = jsonObject.getJSONArray("Queues");

        for (int i = 0; i < array.length(); i++) {
            JSONObject childJSONObject = array.getJSONObject(i);
            queueName.add(childJSONObject.getString("name"));
            queueDesc.add(childJSONObject.getString("description"));
            currentTicket.add(childJSONObject.getInt("currentTicket"));
            lastTicket.add(childJSONObject.getInt("lastTicket"));
        }

    }

}

