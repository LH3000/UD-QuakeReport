/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    //URL for earthquake data from the USGS dataset
    public static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10";
    //adapter for the list of earthquakes
    private EarthquakeAdapter earthquakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // create a new adapter that takes an empty list of earthquakes as input
        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        // set the adaptor on the {@link ListView} so the list can be populated in UI
        earthquakeListView.setAdapter(earthquakeAdapter);
        // set an item click listener on the list view, which sends URL intent to a browser
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //find the current earthquake that was clicked on
                Earthquake currentEarthquake = earthquakeAdapter.getItem(position);

                //convert the string URL into a URI object (to pass into the intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getURL());

                //create new intent to view the URI
                Intent webIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                //send the intent to launch a new activity
                startActivity(webIntent);
            }
        });

        // create an {@link AsyncTask} to perform the Http request to the given URL
        // on a background thread
        FetchEarthquakeAsync fetchEarthquakeAsync = new FetchEarthquakeAsync();
        fetchEarthquakeAsync.execute(USGS_REQUEST_URL);
    }


    private class FetchEarthquakeAsync extends AsyncTask<String, Void, List<Earthquake>> {
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // don't perform the request if there are no URLs, or the first url is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            // perform the HTTP request for earthquake data and process the response.
            // Create a list of earthquake locations.
            List<Earthquake> earthquakesResult = QueryUtils.fetchEarthquakeData(urls[0]);
            return earthquakesResult;
        }

        @Override
        protected void onPostExecute(List<Earthquake> data) {
            // clear the adapter of previous earthquake data
            earthquakeAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                earthquakeAdapter.addAll(data);
            }
        }
    }
}
