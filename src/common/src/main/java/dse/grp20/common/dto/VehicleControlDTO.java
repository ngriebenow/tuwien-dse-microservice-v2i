package dse.grp20.common.dto;

import java.io.Serializable;

public class VehicleControlDTO implements Serializable {

    private String vin;
    private double speed;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}
