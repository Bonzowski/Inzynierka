package pl.marcin.inzynierka.Database;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.marcin.inzynierka.R;

/**
 * Created by Marcin on 15.11.2016.
 */

public class TicketDatabaseActivity extends ListActivity {
    private TicketDataSource datasource;
    private Button add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obtained_tickets_list);

        datasource = new TicketDataSource(this);
        datasource.open();

        List<Ticket> values = datasource.getAllTickets();

        ArrayAdapter<Ticket> adapter = new ArrayAdapter<Ticket>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<Ticket> adapter = (ArrayAdapter<Ticket>) getListAdapter();
                Ticket comment = null;
                String[] comments = new String[]{"Cool", "Very nice", "Hate it"};
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                comment = datasource.createTicket(nextInt,nextInt,comments[nextInt],nextInt);
                adapter.add(comment);
            }
        });

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
