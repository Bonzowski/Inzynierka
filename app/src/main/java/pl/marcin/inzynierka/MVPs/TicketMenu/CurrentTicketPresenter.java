package pl.marcin.inzynierka.MVPs.TicketMenu;

import android.widget.Toast;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.util.Observable;
import java.util.Observer;


import pl.marcin.inzynierka.Backend.Connection.ConnectionAvailability;
import pl.marcin.inzynierka.Backend.Database.TicketDataSource;
import pl.marcin.inzynierka.MVPs.FirstScreen.BeaconSearchModel;
import pl.marcin.inzynierka.MVPs.FirstScreen.BeaconSearchPresenter;
import pl.marcin.inzynierka.R;

/**
 * Created by Marcin on 22.11.2016.
 */

public class CurrentTicketPresenter implements Observer {

    private TicketDataSource datasource;
    private CurrentTicketActivity view;
    private BeaconSearchModel searchBeaconsModel;

    public CurrentTicketPresenter() {}

    public void onTakeView(CurrentTicketActivity view) {
        this.view = view;
        searchBeaconsModel = new BeaconSearchModel(view.getBaseContext());
        startScanning();
    }

    public void startScanning(){
        if(ConnectionAvailability.IsBlueToothEnabled()) {
            searchBeaconsModel.startScanning();
            searchBeaconsModel.addObserver(this);
        }
    }


    public void deactivateTicket() {
        datasource = new TicketDataSource(view.getBaseContext());
        datasource.deactivateTicketInDatabase(view.intent.getStringExtra("date"));
    }

    @Override
    public void update(Observable observable, Object data) {
        IBeaconDevice found = (IBeaconDevice) data;
        if( found.getMajor() == 22222) {
            stopScanning();
            deactivateTicket();
            view.ticketDeactivated();
        }
    }

    public void stopScanning(){
        searchBeaconsModel.onStop();
    }
}
