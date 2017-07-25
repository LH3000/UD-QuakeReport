package com.example.android.quakereport;

/**
 * Created by usa19 on 7/20/2017.
 */

public class Earthquake {
    private String mMagnitude;
    private String mLocation;
    private String mTime;

    public Earthquake(String mag, String loc, String t) {
        mMagnitude = mag;
        mLocation = loc;
        mTime = t;
    }

    public String getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getTime() {
        return mTime;
    }
}
