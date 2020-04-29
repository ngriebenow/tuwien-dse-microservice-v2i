package dse.grp20.actorregistry.dao;

public class TrafficLight {

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
