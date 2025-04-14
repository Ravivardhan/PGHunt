package com.example.learnui1;

import com.google.android.gms.location.FusedLocationProviderClient;

public class DistanceCalculator {
    public static FusedLocationProviderClient fusedLocationProviderClient;
    public static Double lat,lng;

    // Radius of the Earth in kilometers
    private static final double EARTH_RADIUS = 6371.0;

    public static double getDistanceInKm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    /*
        public static void main(String[] args) {
        // Example: New York (lat/lon) to Los Angeles
        double lat1 = 40.7128, lon1 = -74.0060;
        double lat2 = 34.0522, lon2 = -118.2437;

        double distance = getDistanceInKm(lat1, lon1, lat2, lon2);
        System.out.printf("Distance: %.2f km\n", distance);
    }

     */
}
