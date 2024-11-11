package com.example.lab5_madt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CurrencyAdapter extends ArrayAdapter<String> {

    private final Context context;
    private List<String> values;

    // initialize the adapter
    public CurrencyAdapter(Context context, List<String> values) {
        super(context, R.layout.currency_item, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // reuse the view if possible
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.currency_item, parent, false);
        }

        // get the current currency string
        String currency = values.get(position);

        // find the TextView in the layout and set the data
        TextView currencyTextView = convertView.findViewById(R.id.currencyTextView);
        currencyTextView.setText(currency);

        return convertView;
    }
}
