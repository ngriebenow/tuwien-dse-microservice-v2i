package dse.grp20.actorsimulator.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class VehicleStatus implements Serializable {

    @Id
    private Geo location;

    private Geo direction;

    private long time;

    private String vin;

    private double speed;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "VehicleStatus{" +
                "location=" + location +
                ", direction=" + direction +
                ", time=" + time +
                ", vin='" + vin + '\'' +
                ", speed=" + speed +
                '}';
    }

    public Geo getLocation() {
        return location;
    }

    public void setLocation(Geo location) {
        this.location = location;
    }

    public Geo getDirection() {
        return direction;
    }

    public void setDirection(Geo direction) {
        this.direction = direction;
    }

    public Long getTime() {
        return time;
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
}
