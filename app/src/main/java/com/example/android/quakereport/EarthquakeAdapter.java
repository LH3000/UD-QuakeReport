package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by usa19 on 7/20/2017.
 * Set up what each earthquake entry will look like
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCATION_SEPARATOR = " of ";
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

        String originalLocation = currentEarthquake.getLocation();
        String primaryLocation;
        String locationOffset;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        long timeInMs = currentEarthquake.getTimeInMs();
        Date dateObject = new Date(timeInMs);

        //populate the mag field
        TextView magnitude = (TextView) listItemView.findViewById(R.id.mag);
        magnitude.setText(formatMagnitude(currentEarthquake.getMagnitude()));
        //populate the primary location field
        TextView location1 = (TextView) listItemView.findViewById(R.id.primaryLoc);
        location1.setText(primaryLocation);
        //populate the location offset field
        TextView location2 = (TextView) listItemView.findViewById(R.id.locOffset);
        location2.setText(locationOffset);
        //populate the date field
        TextView dateText = (TextView) listItemView.findViewById(R.id.date);
        String date = formatDate(dateObject);
        dateText.setText(date);
        //populate the time field
        TextView timeText = (TextView) listItemView.findViewById(R.id.time);
        String time = formatTime(dateObject);
        timeText.setText(time);

        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL DD, YYYY");
        return dateFormatter.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        return timeFormatter.format(dateObject);
    }

    private String formatMagnitude(double mag) {
        DecimalFormat magFormat = new DecimalFormat("0.0");
        return magFormat.format(mag);
    }
}
