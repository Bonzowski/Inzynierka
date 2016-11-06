package pl.marcin.inzynierka.MainMenu;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import pl.marcin.inzynierka.Connection.ConnectionAvailability;
import pl.marcin.inzynierka.Connection.ServerConnectionModel;
import pl.marcin.inzynierka.Parsers.QueuesParser;
import pl.marcin.inzynierka.Parsers.TicketParser;
import pl.marcin.inzynierka.R;

/**
 * Created by Marcin on 14.10.2016.
 */

public class QueuesPresenter {

    private QueuesActivity view;
    private Context context;
    private TicketParser parsedAnswer;
    public QueuesParser queuesAnswer;

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
            queuesAnswer = new QueuesParser(answer);
        }
        else
            view.noNetwork();
    }

    public void getTicket(int queue) throws ExecutionException, InterruptedException, JSONException {
        if (ConnectionAvailability.IsNetworkEnabled(view.getBaseContext())){

            String query = ServerConnectionModel.prefix + ServerConnectionModel.ipAddress + context.getString(R.string.get_Ticket) + Integer.toString(queue);

            JSONObject answer = new ServerConnectionModel().execute(query).get();
            parsedAnswer = new TicketParser(answer);
            view.navigateToTicket(parsedAnswer.queue_id, parsedAnswer.ticket_id, parsedAnswer.ticketDate);
        }

        else
            view.noNetwork();

    }
}
