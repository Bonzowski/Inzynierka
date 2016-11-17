package pl.marcin.inzynierka.MVPs.TicketDatabaseMenu;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import pl.marcin.inzynierka.Backend.Connection.ServerConnectionModel;
import pl.marcin.inzynierka.Backend.Database.TicketDataSource;
import pl.marcin.inzynierka.Backend.Database.TicketDatabaseObject;
import pl.marcin.inzynierka.R;

/**
 * Created by Marcin on 15.11.2016.
 */

public class TicketDatabaseActivity extends ListActivity {
    private TicketDataSource datasource;
    private List<TicketDatabaseObject> values;
    private TicketDatabasePresenter presenter;
    private ListView ticketsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obtained_tickets_list);
        ticketsList = (ListView) findViewById(android.R.id.list);

        datasource = new TicketDataSource(this);
        datasource.open();

        values = datasource.getAllTickets();

        if (presenter == null)
            presenter = new TicketDatabasePresenter();
        presenter.onTakeView(this);

        ticketsList.setAdapter(new TicketDatabaseListAdapter(this, values, presenter));
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

    @SuppressWarnings("unchecked")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                alertPopup();
        }
    }

    public void alertPopup(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Warning");
        alert.setMessage("Do you want to delete all tickets?");


        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                presenter.deleteAllComments();
                finish();
                startActivity(new Intent(getBaseContext(), TicketDatabaseActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

}

