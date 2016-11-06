package pl.marcin.inzynierka.MainMenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pl.marcin.inzynierka.R;

/**
 * Created by Marcin on 06.11.2016.
 */

public class QueueListAdapter extends BaseAdapter {

    ArrayList<String> queueName;
    ArrayList<String> queueDesc;
    ArrayList<Integer> currentTicket;
    ArrayList<Integer> lastTicket;

    Context context;
    private static LayoutInflater inflater = null;

    final QueuesPresenter presenter;

    public QueueListAdapter(QueuesActivity activity, ArrayList<String> qName, ArrayList<String> qDesc,
                            ArrayList<Integer> cTicket,  ArrayList<Integer> lTicket, QueuesPresenter presenter) {

        queueName = qName;
        queueDesc = qDesc;
        currentTicket = cTicket;
        lastTicket = lTicket;
        this.presenter = presenter;


        context = activity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class Holder
    {
        TextView hqName;
        TextView hqueueDesc;
        TextView hcurrentTicket;
        TextView hlastTicket;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        final int ps = position;

        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.queue_details, null);

        holder.hqName = (TextView) rowView.findViewById(R.id.rowQueueName);
        holder.hqueueDesc = (TextView) rowView.findViewById(R.id.rowQueueDescription);
        holder.hcurrentTicket = (TextView) rowView.findViewById(R.id.rowQueueLastTicket);
        holder.hlastTicket = (TextView) rowView.findViewById(R.id.rowQueueCurrentTicket);

        holder.hqName.setText("Queue name: " + queueName.get(position));
        holder.hqueueDesc.setText("Queue description: " + queueDesc.get(position));
        holder.hcurrentTicket.setText("Current ticket: " +currentTicket.get(position));
        holder.hlastTicket.setText("Last ticket: " + lastTicket.get(position));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.LTGRAY);
                try {
                    presenter.getTicket(ps);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return rowView;
    }

    @Override
    public int getCount() {
        return queueName.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }


}
