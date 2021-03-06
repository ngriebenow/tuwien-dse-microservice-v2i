package dse.grp20.actorregistry.entity;

import java.io.Serializable;

public class Geo implements Serializable {

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
}
