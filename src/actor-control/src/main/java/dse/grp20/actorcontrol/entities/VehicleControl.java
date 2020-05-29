package dse.grp20.actorcontrol.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;


public class VehicleControl implements Serializable {

    @Id
    @MongoId
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
}
