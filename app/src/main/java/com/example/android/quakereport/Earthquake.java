package com.example.android.quakereport;

/**
 * Created by usa19 on 7/20/2017.
 */

public class Earthquake {
    private Double mMagnitude;
    private String mLocation;
    private long mTimeInMs;

    public Earthquake(Double mag, String loc, long t) {
        mMagnitude = mag;
        mLocation = loc;
        mTimeInMs = t;
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
}
