package dse.grp20.actorsimulator.entity;

import java.io.Serializable;

public class TrafficLight implements Serializable {

    public TrafficLight(Long id, Double scanRadius, Geo location) {
        this.id = id;
        this.scanRadius = scanRadius;
        this.location = location;
    }

    public TrafficLight() {
    }

    private Long id;

    private Double scanRadius;

    private Geo location;

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

    public Geo getLocation() {
        return location;
    }

    public void setLocation(Geo location) {
        this.location = location;
    }
}
