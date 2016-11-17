package pl.marcin.inzynierka.MVPs.QueuesMenu;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.widget.ListView;
        import android.widget.Toast;

        import org.json.JSONException;

        import java.util.concurrent.ExecutionException;

        import pl.marcin.inzynierka.R;
        import pl.marcin.inzynierka.MVPs.TicketMenu.CurrentTicketActivity;

public class QueuesActivity extends Activity {

    private QueuesPresenter presenter;
    private ListView queuesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queues_list);

        queuesList = (ListView) findViewById(R.id.listQueues);

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
        setAdapter();
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
        setAdapter();
    }

    private void setAdapter(){
    queuesList.setAdapter(new QueueListAdapter(this,
            presenter.parsedQueues.queueName,
            presenter.parsedQueues.queueDesc,
            presenter.parsedQueues.currentTicket,
            presenter.parsedQueues.lastTicket, presenter ));
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

        Intent ticketIntent = new Intent(this, CurrentTicketActivity.class);
        ticketIntent.putExtra("queue_id", queue);
        ticketIntent.putExtra("ticket_id", id);
        ticketIntent.putExtra("date", date);

        startActivity(ticketIntent);
    }

}



