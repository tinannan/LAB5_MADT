package com.example.lab5_madt;

import android.os.AsyncTask;

import com.example.lab5_madt.MainActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataLoader extends AsyncTask<String, Void, List<String>> {

    private final MainActivity mainActivity;

    // Constructor to link DataLoader with MainActivity
    public DataLoader(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    protected List<String> doInBackground(String... urls) {
        List<String> data = null;
        try {
            // Fetch XML data from the URL
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();

            // Parse XML using the Parser class
            data = Parser.parseXML(inputStream);

            // Close the input stream
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        // Update the MainActivity with parsed data
        if (result != null && !result.isEmpty()) {
            mainActivity.updateCurrencyList(result);
        }
    }
}
