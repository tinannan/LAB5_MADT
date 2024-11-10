package com.example.lab5_madt;

import android.os.AsyncTask;

import com.example.lab5_madt.MainActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import android.util.Log;
public class DataLoader extends AsyncTask<String, Void, List<String>> {

    private final MainActivity mainActivity;

    // link DataLoader with MainActivity
    public DataLoader(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    protected List<String> doInBackground(String... urls) {
        List<String> data = null;
        try {

            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();

            // parse xml
            data = Parser.parseXML(inputStream);

            // close the input stream
            inputStream.close();
        } catch (Exception e) {
            Log.e("DataLoader", "Error fetching currency data", e);
        }
        return data;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        // update the MainActivity with parsed data
        if (result != null && !result.isEmpty()) {
            mainActivity.updateCurrencyList(result);
        }
    }
}
