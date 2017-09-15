package com.example.kraputis.meginam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Justineso on 2016.12.06.
 */

public class TopAdapter extends ArrayAdapter<Top> {
    public TopAdapter(Context context, ArrayList<Top> topas) {
        super(context, 0, topas);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Top top = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_top, parent, false);
        }
        // Lookup view for data population
        TextView nickas = (TextView) convertView.findViewById(R.id.vardas_field);
        TextView taskai = (TextView) convertView.findViewById(R.id.taskai_field);
        // Populate the data into the template view using the data object
        nickas.setText(top.getNICKAS());
        taskai.setText(top.getTASKAI());
        // Return the completed view to render on screen
        return convertView;
    }
}
