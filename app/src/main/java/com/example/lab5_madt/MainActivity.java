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

        // implement the search functionality
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filterCurrencyList(charSequence.toString());
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
        }
    }

    // filter the ListView based on user input
    private void filterCurrencyList(String query) {
        List<String> filteredList = new ArrayList<>();
        for (String currency : currencyList) {
            if (currency.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(currency);
            }
        }
        currencyAdapter.clear();
        currencyAdapter.addAll(filteredList);
        currencyAdapter.notifyDataSetChanged();
    }
}
