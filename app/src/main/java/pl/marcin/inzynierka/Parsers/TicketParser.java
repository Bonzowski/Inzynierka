package pl.marcin.inzynierka.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcin on 20.10.2016.
 */

public class TicketParser {

    public int queue_id;
    public int ticket_id;
    public String ticketDate;


    public TicketParser(JSONObject jObject) throws JSONException {

        queue_id = jObject.getInt("queue_id");
        ticket_id = jObject.getInt("ticket_id");
        ticketDate = jObject.getString("date");
    }
}
