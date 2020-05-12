package dse.grp20.actorsimulator.entity;

import java.io.Serializable;

public class Geo implements Serializable {

    private static final double proximity = 0.0001;

    public Geo() {
    }

    public Geo(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
        return new Geo(this.latitude - geo.latitude, this.longitude - geo.latitude);
    }

    public Geo plus(Geo geo) {
        return new Geo(this.latitude + geo.latitude, this.longitude + geo.latitude);
    }

    public boolean inProximity(Geo geo) {
        return (this.latitude - geo.latitude) < proximity && (this.longitude - geo.longitude) < proximity;
    }

    public Double getLength() {
        return Math.sqrt(this.latitude * this.latitude + this.longitude * this.longitude);
    }

    public Geo normalize() {
        double l = getLength();
        return new Geo(this.latitude / l, this.longitude / l);
    }

    public Geo scale(double l) {
        return new Geo(this.latitude * l, this.longitude * l);
    }
}
