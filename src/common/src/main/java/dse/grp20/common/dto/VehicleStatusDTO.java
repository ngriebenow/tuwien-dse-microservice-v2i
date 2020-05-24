package dse.grp20.common.dto;

import java.io.Serializable;

public class VehicleStatusDTO implements Serializable {

    private static final long serialVersionUID = 7928866262771344000L;
    private GeoDTO location;
    private GeoDTO direction;

    private long time;

    private double speed;

    private String vin;

    public GeoDTO getLocation() {
        return this.location;
    }

    public void setLocation(GeoDTO location) {
        this.location = location;
    }

    public GeoDTO getDirection() {
        return this.direction;
    }

    public void setDirection(GeoDTO direction) {
        this.direction = direction;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
