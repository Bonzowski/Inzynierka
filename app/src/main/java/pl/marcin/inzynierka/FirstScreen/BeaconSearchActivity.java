package pl.marcin.inzynierka.FirstScreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import pl.marcin.inzynierka.MainMenu.QueuesActivity;
import pl.marcin.inzynierka.R;

import static pl.marcin.inzynierka.R.layout.activity_main;

/**
 * Created by Marcin on 06.11.2016.
 */

public class BeaconSearchActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    private BeaconSearchPresenter bPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        findViewById(R.id.start_button).setOnClickListener(this);

        if (bPresenter == null)
            bPresenter = new BeaconSearchPresenter();
        bPresenter.onTakeView(this);
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    //handling the UI changes
    public Handler progressHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            switch (msg.what) {
                case 1:
                    noBluetooth();
                    break;
                case 2:
                    noBeacons();
                    break;
                case 3:
                    progressHandler = null;
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_button:
                Thread thread = new Thread(this);
                thread.start();
                progressDialog = ProgressDialog.show(this, "Please wait..", "Searching for beacons", true, false);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(progressHandler != null)
                            progressHandler.sendEmptyMessage(2);
                    }
                }, 5000);
                break;
        }
    }

    @Override
    public void run() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bPresenter.startScanning(getBaseContext());
            }
        });
    }

    private void noBluetooth(){
        Toast.makeText(this,getText(R.string.no_bluetooth),Toast.LENGTH_LONG).show();
    }

    private void noBeacons(){
        Toast.makeText(this,getText(R.string.no_beacons),Toast.LENGTH_LONG).show();
    }

    public void navigateToQueues(){

        progressHandler.sendEmptyMessage(3);

        bPresenter.stopScanning();
        bPresenter.destroyModel();
        startActivity(new Intent(this, QueuesActivity.class));
        finish();
    }

}
