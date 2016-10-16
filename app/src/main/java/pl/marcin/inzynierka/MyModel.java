package pl.marcin.inzynierka;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleEddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Marcin on 14.10.2016.
 */

public class MyModel extends Observable {

    private ProximityManagerContract proximityManager;
    private ArrayList<IBeaconDevice> iBeaconDevices;
    private ArrayList<IEddystoneDevice> iEddyStoneDevices;

        MyModel(Context context){

            KontaktSDK.initialize(context.getString(R.string.kontakt_io_api_key));
            proximityManager = new ProximityManager(context);
            proximityManager.setIBeaconListener(createIBeaconListener());
            proximityManager.setEddystoneListener(createEddystoneListener());

            iBeaconDevices = new ArrayList<IBeaconDevice>();
            iEddyStoneDevices = new ArrayList<IEddystoneDevice>();

        }

        protected void onStop() {
            proximityManager.stopScanning();

        }

        protected void onDestroy() {
            proximityManager.disconnect();
            proximityManager = null;

        }

        public void startScanning() {
            proximityManager.connect(new OnServiceReadyListener() {
                @Override
                public void onServiceReady() {
                    proximityManager.startScanning();
                }
            });
        }

        private IBeaconListener createIBeaconListener() {
            return new SimpleIBeaconListener() {
                @Override
                public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
                    iBeaconDevices.add(ibeacon);
                    setChanged();
                    notifyObservers();
                    Log.i("Sample", "IBeacon discovered: " + ibeacon.getUniqueId());
                }
            };
        }

        private EddystoneListener createEddystoneListener() {
            return new SimpleEddystoneListener() {
                @Override
                public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                    Log.i("Sample", "Eddystone discovered: " + eddystone.getUniqueId());
                }
            };
        }

        public ArrayList<IBeaconDevice> getValue(){
            return iBeaconDevices;
        }
}
