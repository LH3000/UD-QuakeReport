package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by usa19 on 7/20/2017.
 * Set up what each earthquake entry will look like
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        //populate the mag field
        TextView magnitude = (TextView) listItemView.findViewById(R.id.mag);
        magnitude.setText(currentEarthquake.getMagnitude());
        //populate the location field
        TextView location = (TextView) listItemView.findViewById(R.id.loc);
        location.setText(currentEarthquake.getLocation());
        //populate the time field
        TextView time = (TextView) listItemView.findViewById(R.id.time);
        time.setText(currentEarthquake.getTime());

        return listItemView;
    }
}
