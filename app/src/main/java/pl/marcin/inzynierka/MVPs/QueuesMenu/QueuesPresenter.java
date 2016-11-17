package pl.marcin.inzynierka.MVPs.QueuesMenu;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import pl.marcin.inzynierka.Backend.Connection.ConnectionAvailability;
import pl.marcin.inzynierka.Backend.Connection.ServerConnectionModel;
import pl.marcin.inzynierka.Backend.Database.TicketDataSource;
import pl.marcin.inzynierka.Backend.Parsers.QueuesParser;
import pl.marcin.inzynierka.Backend.Parsers.TicketParser;
import pl.marcin.inzynierka.R;

/**
 * Created by Marcin on 14.10.2016.
 */

public class QueuesPresenter {

    private QueuesActivity view;
    private Context context;
    private TicketParser parsedTicket;
    public QueuesParser parsedQueues;

    private TicketDataSource datasource;

    public QueuesPresenter() {
    }

    public void onTakeView(QueuesActivity view) {
        this.view = view;
        if (view != null)
            this.context = view.getBaseContext();
    }

    public void getQueues() throws ExecutionException, InterruptedException, JSONException {
        if (ConnectionAvailability.IsNetworkEnabled(view.getBaseContext())) {

            String query = ServerConnectionModel.prefix + ServerConnectionModel.ipAddress + context.getString(R.string.get_Queues);

            JSONObject answer = new ServerConnectionModel().execute(query).get();
            parsedQueues = new QueuesParser(answer);
        }
        else
            view.noNetwork();
    }

    public void getTicket(int queue) throws ExecutionException, InterruptedException, JSONException {
        if (ConnectionAvailability.IsNetworkEnabled(view.getBaseContext())){

            String query = ServerConnectionModel.prefix + ServerConnectionModel.ipAddress + context.getString(R.string.get_Ticket) + Integer.toString(queue);

            JSONObject answer = new ServerConnectionModel().execute(query).get();
            parsedTicket = new TicketParser(answer);
            ticketToDatabase(parsedTicket);
            view.navigateToTicket(parsedTicket.queue_id, parsedTicket.ticket_id, parsedTicket.ticketDate);
        }

        else
            view.noNetwork();

    }

    public void ticketToDatabase (TicketParser ticketParser){

        datasource = new TicketDataSource(context);
        datasource.open();
        datasource.createTicket(ticketParser.queue_id,ticketParser.ticket_id,ticketParser.ticketDate,1);
        datasource.close();
    }

}
