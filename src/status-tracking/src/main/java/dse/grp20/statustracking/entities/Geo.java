package dse.grp20.statustracking.entities;

import java.io.Serializable;

public class Geo implements Serializable {

    private static final long serialVersionUID = -6885435710143140474L;


    public Geo() {
    }

    public Geo(String type, Double longitude, Double latitude) {
        this.type = type;
        this.coordinates = new Double[]{longitude, latitude};
    }

    private String type;

    private Double[] coordinates;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double[] getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }
}
