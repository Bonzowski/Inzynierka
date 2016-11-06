package pl.marcin.inzynierka.MainMenu;

        import android.app.Activity;
        import android.app.ListActivity;
        import android.app.LoaderManager;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.content.Loader;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Menu;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import org.json.JSONException;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.concurrent.ExecutionException;

        import pl.marcin.inzynierka.R;
        import pl.marcin.inzynierka.TicketMenu.TicketActivity;

        import static pl.marcin.inzynierka.R.layout.queues_list;

public class QueuesActivity extends Activity {

    private QueuesPresenter presenter;
    private ProgressDialog progressDialog;
    private ListView queuesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queues_list);

        queuesList = (ListView) findViewById(R.id.list);

        if (presenter == null)
            presenter = new QueuesPresenter();
        presenter.onTakeView(this);

        try {
            presenter.getQueues();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        queuesList.setAdapter(new QueueListAdapter(this,
                presenter.queuesAnswer.queueName,
                presenter.queuesAnswer.queueDesc,
                presenter.queuesAnswer.currentTicket,
                presenter.queuesAnswer.lastTicket, presenter ));
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        try {
            presenter.getQueues();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        queuesList.setAdapter(new QueueListAdapter(this,
                presenter.queuesAnswer.queueName,
                presenter.queuesAnswer.queueDesc,
                presenter.queuesAnswer.currentTicket,
                presenter.queuesAnswer.lastTicket, presenter ));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onTakeView(null);
        //presenter.destroyModel();
        if (!isChangingConfigurations())
            presenter = null;
    }

    public void noNetwork() {
        Toast.makeText(this, getText(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    //opening ticket activity
    public void navigateToTicket(Integer queue, Integer id, String date) {

        Intent ticketIntent = new Intent(this, TicketActivity.class);
        ticketIntent.putExtra("queue_id", queue);
        ticketIntent.putExtra("ticket_id", id);
        ticketIntent.putExtra("date", date);

        startActivity(ticketIntent);
    }

}



