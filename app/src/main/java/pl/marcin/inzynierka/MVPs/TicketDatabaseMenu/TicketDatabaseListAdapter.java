package pl.marcin.inzynierka.MVPs.TicketDatabaseMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.marcin.inzynierka.Backend.Database.TicketDatabaseObject;
import pl.marcin.inzynierka.R;

/**
 * Created by Marcin on 16.11.2016.
 */

public class TicketDatabaseListAdapter extends BaseAdapter {

    private List<TicketDatabaseObject> ticketsInDatabse;
    private Integer images[] = {R.drawable.common_signin_btn_icon_focus_dark, R.drawable.common_signin_btn_icon_focus_light};

    Context context;
    private static LayoutInflater inflater = null;
    final TicketDatabasePresenter presenter;

    public TicketDatabaseListAdapter(TicketDatabaseActivity activity, List<TicketDatabaseObject> ticketsInDatabase, TicketDatabasePresenter presenter){

        this.ticketsInDatabse = ticketsInDatabase;
        this.presenter = presenter;

        context = activity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class Holder
    {
        ImageView hImage;
        TextView hdatabaseId;
        TextView hqueueId;
        TextView hticketId;
        TextView hDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int ps = position;

        TicketDatabaseListAdapter.Holder holder = new TicketDatabaseListAdapter.Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.obtained_ticket_details, null);

        holder.hImage = (ImageView) rowView.findViewById(R.id.icon);

        holder.hdatabaseId = (TextView) rowView.findViewById(R.id.databaseID);
        holder.hqueueId = (TextView) rowView.findViewById(R.id.Q);
        holder.hticketId = (TextView) rowView.findViewById(R.id.T);
        holder.hDate = (TextView) rowView.findViewById(R.id.D);

        holder.hdatabaseId.setText("" + ticketsInDatabse.get(ticketsInDatabse.size()-position-1).getKey() + ".");
        holder.hqueueId.setText("Queue ID: " + ticketsInDatabse.get(ticketsInDatabse.size()-position-1).getQueue_id());
        holder.hticketId.setText("Ticket ID: " + ticketsInDatabse.get(ticketsInDatabse.size()-position-1).getTicket_id());
        holder.hDate.setText("Issued: " + ticketsInDatabse.get(ticketsInDatabse.size()-position-1).getDate());

        if (ticketsInDatabse.get(ticketsInDatabse.size()-position-1).getIsActive() ==1)
            holder.hImage.setImageResource(images[1]);
        else
            holder.hImage.setImageResource(images[0]);

        return rowView;
    }

    @Override
    public int getCount() {
        return ticketsInDatabse.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
