package pl.marcin.inzynierka.Connection;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import pl.marcin.inzynierka.R;

/**
 * Created by Marcin on 20.10.2016.
 */

public class ConnectionAvailability {

    public static boolean IsNetworkEnabled(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        else
            return true;
    }

    public static boolean IsBlueToothEnabled() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled())
            return false;
        else
            return true;
    }
}
