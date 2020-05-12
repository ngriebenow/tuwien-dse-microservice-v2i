package dse.grp20.actorsimulator.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDateTime;

public class VehicleStatus implements Serializable {

    @Id
    private Geo location;

    private Geo velocity;

    private long time;

    private String vehicleId;

    @Override
    public String toString() {
        return "VehicleStatus{" +
                "location=" + location +
                ", velocity=" + velocity +
                ", time=" + time +
                ", vehicleId='" + vehicleId + '\'' +
                '}';
    }

    public Geo getLocation() {
        return location;
    }

    public void setLocation(Geo location) {
        this.location = location;
    }

    public Geo getVelocity() {
        return velocity;
    }

    public void setVelocity(Geo velocity) {
        this.velocity = velocity;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
