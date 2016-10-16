package pl.marcin.inzynierka;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.support.v17.leanback.widget.Presenter;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Marcin on 14.10.2016.
 */

public class MyPresenter implements Observer {

    private MainActivity view;
    private MyModel model;
    private Context context;
    private int counter = 0;

    public MyPresenter(){}

    public void onTakeView(MainActivity view) {
        this.view = view;
        this.context = view.getBaseContext();
    }

    public void startScanning(){
        model = new MyModel(context);
        model.startScanning();
        model.addObserver(this);
    }

    public void stopScanning(){
        model.onStop();
    }


    @Override
    public void update(Observable observable, Object data) {
        if (counter < model.getValue().size())
            view.setBeaconName(counter, Integer.toString(counter+1) + ". beacon name is: " + model.getValue().get(counter).getUniqueId());
            counter++;
    }
}
