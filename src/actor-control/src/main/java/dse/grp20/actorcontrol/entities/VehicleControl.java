package dse.grp20.actorcontrol.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

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
    private String name;
    private double speed;
    private String timestamp;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
