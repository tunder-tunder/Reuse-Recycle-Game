package com.example.myapplication.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class LocationHelper {

    private Context context;
    private Activity activity;

    private boolean granted = false;
    private final int LOCATION_PERMISSION = 1001;

    public LocationHelper(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }


}
