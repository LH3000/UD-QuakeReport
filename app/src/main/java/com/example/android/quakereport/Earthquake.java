package com.example.android.quakereport;

/**
 * Created by usa19 on 7/20/2017.
 */

public class Earthquake {
    private Double mMagnitude;
    private String mLocation;
    private long mTimeInMs;
    private String URL;

    public Earthquake(Double mag, String loc, long t, String url) {
        mMagnitude = mag;
        mLocation = loc;
        mTimeInMs = t;
        URL = url;
    }

    public Double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getTimeInMs() {
        return mTimeInMs;
    }

    public String getURL() {
        return URL;
    }
}
