package dse.grp20.statustracking.entities;

import java.io.Serializable;

public class Geo implements Serializable {

    private static final long serialVersionUID = -6885435710143140474L;

    public Geo() {
    }

    public Geo(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private Double latitude;

    private Double longitude;

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
