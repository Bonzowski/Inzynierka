package pl.marcin.inzynierka.FirstScreen;

import android.content.Context;
import java.util.Observable;
import java.util.Observer;
import pl.marcin.inzynierka.Connection.ConnectionAvailability;


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
    }

    // searching for beacons section
    public void startScanning(Context c) {
        if(ConnectionAvailability.IsBlueToothEnabled()) {
            searchBeaconsModel = new BeaconSearchModel(c);
            searchBeaconsModel.startScanning();
            searchBeaconsModel.addObserver(this);
        }
        else
            view.progressHandler.sendEmptyMessage(1);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (searchBeaconsModel.getIBeaconDevices().size() == 1 || searchBeaconsModel.getIEddyStoneDevices().size() == 1)
           view.navigateToQueues();
    }

    public void stopScanning(){
        searchBeaconsModel.onStop();
    }

    public void destroyModel() {
        searchBeaconsModel.onDestroy();
    }

}

