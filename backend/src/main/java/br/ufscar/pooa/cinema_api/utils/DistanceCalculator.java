package br.ufscar.pooa.cinema_api.utils;

public class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.01;

    private DistanceCalculator() {
    }

    /**
     * Calculates the approximate distance between two geographical points using the equirectangular
     * approximation formula. This method is computationally less intensive than the Haversine
     * formula but less accurate, especially for longer distances.
     *
     * @param lat1 latitude of the first point in decimal degrees
     * @param lon1 longitude of the first point in decimal degrees
     * @param lat2 latitude of the second point in decimal degrees
     * @param lon2 longitude of the second point in decimal degrees
     * @return the approximate distance between the two points in kilometers
     */
    public static double equirectangularDistance(
        double lat1,
        double lon1,
        double lat2,
        double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double x = (lon2 - lon1) * Math.cos((lat1 + lat2) / 2);
        double y = lat2 - lat1;

        return Math.sqrt(x * x + y * y) * EARTH_RADIUS_KM;
    }
}

