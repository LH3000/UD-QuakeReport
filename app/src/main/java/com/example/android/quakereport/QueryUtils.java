package com.example.android.quakereport;

/**
 * Created by usa19 on 7/22/2017.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    // tag for the log messages
    private static final String TAG = "QueryUtils";
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {

        //<editor-fold desc="** artificial delay to show the progress bar">
        //try {
        //Thread.sleep(2000);
        //} catch (InterruptedException e) {
        //e.printStackTrace();
        //}
        //</editor-fold>

        // create URL object
        URL url = createUrl(requestUrl);

        // perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }
        // extract relevant fields from the JSON response and create a list of {@link earthquakes}
        List<Earthquake> earthquakes = extractEarthquakesFromJSON(jsonResponse);
        // return the list of {@link earthquakes}s
        return earthquakes;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        //if the url is null, then return early,
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000/* ms */);
            urlConnection.setConnectTimeout(15000 /* ms */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // if the request was successful (response code HttpURLConnection.HTTP_OK == 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the whoe JSON from the server
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing the given JSON response.
     */
    public static List<Earthquake> extractEarthquakesFromJSON(String jsonResponse) {
        // if the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakesList = new ArrayList<>();

        // Try to parse the jsonResponse string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // create a JSONObject from the jsonResponse string, and parse it into earthquake objects
            // store them in
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray earthquakeFeatures = baseJsonResponse.getJSONArray("features");
            for (int i = 0; i < earthquakeFeatures.length(); i++) {
                JSONObject currentEarthquake = earthquakeFeatures.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                Earthquake thisEarthquake = new Earthquake(
                        properties.getDouble("mag"),
                        properties.getString("place"),
                        properties.getLong("time"),
                        properties.getString("url"));
                earthquakesList.add(thisEarthquake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakesList;
    }

}
