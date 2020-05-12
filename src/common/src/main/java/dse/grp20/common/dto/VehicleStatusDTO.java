package dse.grp20.common.dto;

import java.io.Serializable;

public class VehicleStatusDTO implements Serializable {

    private static final long serialVersionUID = 7928866262771344000L;
    private GeoDTO location;
    private GeoDTO velocity;

    private long timeStamp;

    private VehicleDTO vehicle;

    public GeoDTO getLocation() {
        return this.location;
    }

    public void setLocation(GeoDTO location) {
        this.location = location;
    }

    public GeoDTO getVelocity() {
        return this.velocity;
    }

    public void setVelocity(GeoDTO velocity) {
        this.velocity = velocity;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public VehicleDTO getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }
}
