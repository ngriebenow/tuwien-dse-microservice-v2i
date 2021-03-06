package dse.grp20.actorsimulator.entity;


import java.io.Serializable;


public class VehicleControl implements Serializable {

    private String id;

    private String vin;

    private double speed;
    private long timestamp;

    public VehicleControl() {
        timestamp = System.currentTimeMillis();
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
