package pl.marcin.inzynierka.MVPs.FirstScreen;

import android.content.Context;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.util.Observable;
import java.util.Observer;
import pl.marcin.inzynierka.Backend.Connection.ConnectionAvailability;


/**
 * Created by Marcin on 06.11.2016.
 */

public class BeaconSearchPresenter implements Observer {

    private BeaconSearchActivity view;
    private BeaconSearchModel searchBeaconsModel;

    public BeaconSearchPresenter() {
    }

    public void onTakeView(BeaconSearchActivity view) {
        this.view = view;
        searchBeaconsModel = new BeaconSearchModel(view.getBaseContext());
    }

    // searching for beacons section
    public void startScanning() {
        if(ConnectionAvailability.IsBlueToothEnabled()) {
            searchBeaconsModel.startScanning();
            searchBeaconsModel.addObserver(this);
        }
        else
            view.progressHandler.sendEmptyMessage(1);
    }

    @Override
    public void update(Observable observable, Object data) {
        IBeaconDevice found = (IBeaconDevice) data;
        if( found.getMajor() == 11111) {
            view.navigateToQueues();
            stopScanning();
        }
    }

    public void stopScanning(){
        searchBeaconsModel.onStop();
    }

    public void destroyModel() {
        searchBeaconsModel.onDestroy();
    }

}

