package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    /**
     * Tag for log messages (Ctrl + J -> Tag)
     */
    private static final String TAG = "EarthquakeLoader";

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /**
     * required to trigger the loadInBackground()
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread
     *
     * @return
     */
    @Override
    public List<Earthquake> loadInBackground() {
        // don't perform the request if there are no URLs.
        if (mUrl == null) {
            return null;
        }
        // perform the HTTP request for earthquake data and process the response.
        // Create a list of earthquake locations.
        return QueryUtils.fetchEarthquakeData(mUrl);
    }
}
