package dse.group20.statustracking.entities;

import java.io.Serializable;

public class TrafficLight implements Serializable {

    private static final long serialVersionUID = 2588908906970753690L;
    private Long id;

    private Double scanRadius;

    private Geo location;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScanRadius() {
        return this.scanRadius;
    }

    public void setScanRadius(Double scanRadius) {
        this.scanRadius = scanRadius;
    }

    public Geo getLocation() {
        return this.location;
    }

    public void setLocation(Geo location) {
        this.location = location;
    }
}

