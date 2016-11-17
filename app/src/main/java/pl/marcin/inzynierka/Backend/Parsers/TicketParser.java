package pl.marcin.inzynierka.Backend.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
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
        Date dateFromJSON = new Date(jObject.getLong("date"));

        Calendar fullDate = toCalendar(dateFromJSON);
        ticketDate = HourBuilder(fullDate);

    }

    private Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private String HourBuilder(Calendar cal){

        String hours = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String minutes = Integer.toString(cal.get(Calendar.MINUTE));
        String seconds = Integer.toString(cal.get(Calendar.SECOND));

        return hours + ":" + minutes + ":" + seconds;

    }

}
