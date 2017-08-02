package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.android.quakereport.R.id.mag;

/**
 * Created by usa19 on 7/20/2017.
 * Set up what each earthquake entry will look like
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Activity context, List<Earthquake> earthquakes) {
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
        TextView magnitude = (TextView) listItemView.findViewById(mag);
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

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        return listItemView;
    }

    private int getMagnitudeColor(Double magnitude) {
        int magColorID;
        //floor will find the closest integer less than the decimal value
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magColorID = R.color.magnitude1;
                break;
            case 2:
                magColorID = R.color.magnitude2;
                break;
            case 3:
                magColorID = R.color.magnitude3;
                break;
            case 4:
                magColorID = R.color.magnitude4;
                break;
            case 5:
                magColorID = R.color.magnitude5;
                break;
            case 6:
                magColorID = R.color.magnitude6;
                break;
            case 7:
                magColorID = R.color.magnitude7;
                break;
            case 8:
                magColorID = R.color.magnitude8;
                break;
            case 9:
                magColorID = R.color.magnitude9;
                break;
            default:
                magColorID = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magColorID);
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
