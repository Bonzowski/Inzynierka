package pl.marcin.inzynierka.TicketMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import pl.marcin.inzynierka.R;

import static pl.marcin.inzynierka.R.layout.ticket;

/**
 * Created by Marcin on 05.11.2016.
 */

public class TicketActivity extends AppCompatActivity {

    private TextView queue;
    private TextView tickett;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ticket);

        queue = (TextView) findViewById(R.id.queue_id);
        tickett = (TextView) findViewById(R.id.ticket_id);
        date = (TextView) findViewById(R.id.date);

        Intent intent = getIntent();

        queue.setText("" + intent.getIntExtra("queue_id", 0));
        tickett.setText(""+ intent.getIntExtra("ticket_id", 0));
        date.setText("" + intent.getStringExtra("date"));
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
    public void onBackPressed() {
        super.onBackPressed();
    }


}
