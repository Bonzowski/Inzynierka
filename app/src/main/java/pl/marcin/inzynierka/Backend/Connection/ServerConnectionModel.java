package pl.marcin.inzynierka.Backend.Connection;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Marcin on 20.10.2016.
 */

public class ServerConnectionModel extends AsyncTask<String,Void,JSONObject> {

    OkHttpClient client = new OkHttpClient();
    public static JSONObject response = null;

    public static String prefix = "http://";
    public static String ipAddress = "192.168.0.14:8081";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {


        Request request = new Request.Builder()
                .url(params[0])
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonData = null;
        try {
            jsonData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject Jobject = null;
        try {
            Jobject = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Jobject;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }
}
