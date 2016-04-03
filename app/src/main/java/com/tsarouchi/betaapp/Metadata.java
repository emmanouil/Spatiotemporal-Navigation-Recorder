package com.tsarouchi.betaapp;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Emmanouil on 25-Mar-16.
 */
public class Metadata {
    private LocationManager locationManager;
    private coordLVL coordsType = coordLVL.BOTH;

    public enum coordLVL {GPS, NET, BOTH}

    public Metadata(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                UtilsClass.logDEBUG("call");
                recordLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
        try {

            switch (coordsType) {
                case GPS:
                    UtilsClass.logINFO("Getting coordinates from GPS");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    break;
                case NET:
                    UtilsClass.logINFO("Getting coordinates from Network");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    break;
                case BOTH:
                    UtilsClass.logINFO("Getting coordinates from Network and GPS");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    break;
                default:
                    UtilsClass.logERROR("No Coordinate source specified");
                    break;
            }
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            UtilsClass.logERROR("We do not have permission to access Location Services " + e);
            e.printStackTrace();
        }

    }

    private void recordLocation(Location location) {
        UtilsClass.writeLocation(location.toString());
    }


}
