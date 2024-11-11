package com.example.lab5_madt;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CurrencyAdapter currencyAdapter;
    private final List<String> currencyList = new ArrayList<>();
    private final List<String> fullCurrencyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize the views
        ListView currencyListView = findViewById(R.id.currencyListView);
        EditText searchField = findViewById(R.id.searchField);

        // initialize the custom adapter with an empty list
        currencyAdapter = new CurrencyAdapter(this, currencyList);
        currencyListView.setAdapter(currencyAdapter);

        // loading the currency data when the activity is created
        loadCurrencyData();

        // search
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Limit the input to a maximum of 3 characters
                if (charSequence.length() > 3) {
                    // Truncate the input to the first 3 characters
                    searchField.setText(charSequence.subSequence(0, 3));
                    searchField.setSelection(3); // Move the cursor to the end
                    //Log.d("filter debug", "no filter"+ charSequence.subSequence(0, 3).toString());
                } else {
                    // Perform the filtering only if the input is 3 characters or fewer
                    filterCurrencyList(charSequence.toString());
                    //Log.d("filter debug", charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    // start the DataLoader and fetch the currency data
    private void loadCurrencyData() {
        String url = "https://www.floatrates.com/daily/usd.xml"; // Replace with your XML data URL
        new DataLoader(this).execute(url);
    }

    // update the ListView with the fetched data
    public void updateCurrencyList(List<String> data) {
        if (data != null) {
            currencyList.clear();
            currencyList.addAll(data);
            currencyAdapter.notifyDataSetChanged();
            fullCurrencyList.addAll(data);
        }
    }

    // filter the ListView based on user input
    private void filterCurrencyList(String query) {
        List<String> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            // Show all currencies if the query is empty
            filteredList.addAll(fullCurrencyList);
            //Log.d("filterCurrencyList", "empty filter");
        } else {
            //Log.d("filterCurrencyList", query);
            // Filter the list based on the query
            for (String currency : fullCurrencyList) {
                if (currency.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(currency);
                    //Log.d("filterCurrencyList1", filteredList.toString());
                }
            }
        }

        // Update the adapter with the filtered list
        currencyList.clear();
        currencyList.addAll(filteredList);
        currencyAdapter.notifyDataSetChanged();
    }

}
