package dse.grp20.common.dto;

import java.io.Serializable;

public class TrafficLightDTO implements Serializable {


    private Long id;

    private Double scanRadius;

    private GeoDTO location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScanRadius() {
        return scanRadius;
    }

    public void setScanRadius(Double scanRadius) {
        this.scanRadius = scanRadius;
    }

    public GeoDTO getLocation() {
        return location;
    }

    public void setLocation(GeoDTO location) {
        this.location = location;
    }
}
