package dse.grp20.actorcontrol.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package: dse.grp20.actorcontrol.entities
 * @Class: VehicleControl
 * @Author: Guenter Windsperger {01302775}
 * @Date: 16.05.2020
 */
public class VehicleControl implements Serializable {

    @Id
    @MongoId
    private String vehicleId;
    private double speed;
    private long timestamp;

    public VehicleControl() {
        timestamp = System.currentTimeMillis();
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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
