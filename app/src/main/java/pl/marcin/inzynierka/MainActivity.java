package pl.marcin.inzynierka;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.Layout;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import java.util.ArrayList;

        import static pl.marcin.inzynierka.R.layout.activity_main;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyPresenter presenter;
    private LinearLayout mainLayout;
    private ArrayList<EditText> beaconNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);

        findViewById(R.id.start_button).setOnClickListener(this);
        findViewById(R.id.stop_button).setOnClickListener(this);

        if (presenter == null)
            presenter = new MyPresenter();
        presenter.onTakeView(this);

    }

    @Override
    public void onStart(){
        super.onStart();
        getBeacons();
    }

    protected void getBeacons(){

        beaconNames = new ArrayList<>();
        for (int i = 0; i < mainLayout.getChildCount(); i++) {
            if (mainLayout.getChildAt(i) instanceof EditText) {
                beaconNames.add((EditText) mainLayout.getChildAt(i));
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onTakeView(null);
        if (!isChangingConfigurations())
            presenter = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_button:
                presenter.startScanning();
                break;
            case R.id.stop_button:
                presenter.stopScanning();
                break;
        }


    }

    public void setBeaconName(int counter, String name){
        beaconNames.get(counter).setText(name);
    }

}
