package dse.grp20.common.dto;

import java.io.Serializable;

public class VehicleControlDTO implements Serializable {

    private String vehicleId;
    private double speed;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}
