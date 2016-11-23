package pl.marcin.inzynierka.MVPs.TicketMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pl.marcin.inzynierka.Backend.Database.TicketDataSource;
import pl.marcin.inzynierka.MVPs.FirstScreen.BeaconSearchPresenter;
import pl.marcin.inzynierka.R;

import static pl.marcin.inzynierka.R.layout.ticket;

/**
 * Created by Marcin on 05.11.2016.
 */

public class CurrentTicketActivity extends AppCompatActivity {

    private CurrentTicketPresenter ticketPresenter;
    private TextView queue;
    private TextView tickett;
    private TextView date;

    protected Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ticket);

        queue = (TextView) findViewById(R.id.queue_id);
        tickett = (TextView) findViewById(R.id.ticket_id);
        date = (TextView) findViewById(R.id.date);

        intent = getIntent();

        queue.setText("" + intent.getIntExtra("queue_id", 0));
        tickett.setText(""+ intent.getIntExtra("ticket_id", 0));
        date.setText("" + intent.getStringExtra("date"));

        if (ticketPresenter == null)
            ticketPresenter = new CurrentTicketPresenter();
        ticketPresenter.onTakeView(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ticketPresenter.startScanning();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ticketPresenter.stopScanning();
    }

    public void ticketDeactivated(){
        Toast.makeText(getBaseContext(), R.string.ticket_deactivated, Toast.LENGTH_LONG).show();
    }

    public void deactivate(View view) {
        switch (view.getId()) {
            case R.id.deactivate:
               ticketPresenter.deactivateTicket();
               ticketDeactivated();
        }
    }

}
