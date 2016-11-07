package pl.marcin.inzynierka.FirstScreen;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.marcin.inzynierka.Connection.ConnectionAvailability;
import pl.marcin.inzynierka.Connection.ServerConnectionModel;
import pl.marcin.inzynierka.MainMenu.QueuesActivity;
import pl.marcin.inzynierka.R;

import static pl.marcin.inzynierka.R.layout.activity_main;

/**
 * Created by Marcin on 06.11.2016.
 */

public class BeaconSearchActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    private BeaconSearchPresenter bPresenter;
    private ProgressDialog progressDialog;
    private Button changeIP;
    private int counter = 0;
    private boolean beaconFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        findViewById(R.id.start_button).setOnClickListener(this);
        changeIP = (Button) findViewById(R.id.change);
        changeIP.setOnClickListener(this);
        changeIP.setVisibility(View.VISIBLE);
        changeIP.setBackgroundColor(Color.TRANSPARENT);

        if (bPresenter == null)
            bPresenter = new BeaconSearchPresenter();
        bPresenter.onTakeView(this);
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public void onResume(){
        super.onResume();
        if (bPresenter == null)
            bPresenter = new BeaconSearchPresenter();
        bPresenter.onTakeView(this);
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
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.start_button:
                beaconFound = false;

                final Thread searchingForBeaconsThread = new Thread(this);
                searchingForBeaconsThread.start();
                progressDialog = ProgressDialog.show(this, "Please wait..", "Searching for beacons", true, false);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            if(beaconFound)
                                progressHandler.sendEmptyMessage(3);
                            else
                                progressHandler.sendEmptyMessage(2);
                                searchingForBeaconsThread.interrupt();
                    }
                }, 4000);
                break;

            case R.id.change:
                counter++;
                        if(counter == 3){
                            alertPopup();
                            counter = 0;
                        }
                break;
        }
    }

    //popup for emergency IP address change
    public void alertPopup(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Setting");
        alert.setMessage("Type server's new IP and port");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               ServerConnectionModel.ipAddress = input.getText().toString(); // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
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

    //toasts section
    private void noBluetooth(){
        Toast.makeText(this,getText(R.string.no_bluetooth),Toast.LENGTH_LONG).show();
    }

    private void noBeacons(){
        Toast.makeText(this,getText(R.string.no_beacons),Toast.LENGTH_LONG).show();
    }

    private void noNetwork() {
        Toast.makeText(this, getText(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    //forth and back navigation
    public void navigateToQueues(){
        progressHandler.sendEmptyMessage(3);
        bPresenter.stopScanning();
        beaconFound = true;
        if (ConnectionAvailability.IsNetworkEnabled(this)){
            bPresenter.destroyModel();
            startActivity(new Intent(this, QueuesActivity.class));
        }
        else
            noNetwork();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
