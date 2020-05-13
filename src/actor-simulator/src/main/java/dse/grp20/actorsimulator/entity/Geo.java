package dse.grp20.actorsimulator.entity;

import java.io.Serializable;

public class Geo implements Serializable {

    private static final double proximity = 0.0002;

    public Geo() {
    }

    public Geo(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    private Double latitude;

    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Geo minus(Geo geo) {
        return new Geo(this.latitude - geo.latitude, this.longitude - geo.longitude);
    }

    public Geo plus(Geo geo) {
        return new Geo(this.latitude + geo.latitude, this.longitude + geo.longitude);
    }

    public boolean inProximity(Geo geo) {
        return Math.abs(this.latitude - geo.latitude) < proximity && Math.abs(this.longitude - geo.longitude) < proximity;
    }

    /*public Double getLength() {
        return Math.sqrt(this.latitude * this.latitude + this.longitude * this.longitude);
    }*/

    /*public Geo normalize() {
        double l = getLength();
        return new Geo(this.latitude / l, this.longitude / l);
    }*/

    public Geo scale(double l) {
        return new Geo(this.latitude * l, this.longitude * l);
    }



    public static double distance(Geo start, Geo end) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(end.getLatitude() - start.getLatitude());
        double lonDistance = Math.toRadians(end.getLongitude() - start.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(start.getLatitude())) * Math.cos(Math.toRadians(end.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // convert to meters

    }


    /*
    public static double speed(Geo start, Geo end, long timeMs) {
        return distance * 1000 / timeMs;
    }*/


}
