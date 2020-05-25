package dse.grp20.statustracking.entities;

import dse.grp20.common.dto.GeoDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "VehicleStatus")
public class VehicleStatus implements Serializable {

    private static final long serialVersionUID = -1620525001941958403L;
    private Geo location;
    private Geo direction;
    private double speed;

    private long time;

    private String vin;

    @Id
    private String id;

    public VehicleStatus() {}

    public VehicleStatus(GeoDTO location, GeoDTO direction, String vin, long time, double speed) {
        this.location = new Geo("Point", location.getLongitude(), location.getLatitude());
        this.direction = new Geo("Point", direction.getLongitude(), direction.getLatitude());
        this.vin = vin;
        this.time = time;
        this.speed = speed;
    }

    public VehicleStatus(VehicleStatusDTO dto) {
        this(dto.getLocation(), dto.getDirection(), dto.getVin(), dto.getTime(), dto.getSpeed());
    }

    public String getId() {
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public Geo getLocation() {
        return this.location;
    }

    public void setLocation(Geo location) {
        this.location = location;
    }

    public Geo getDirection() {
        return this.direction;
    }

    public void setDirection(Geo direction) {
        this.direction = direction;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getVin() {
        return this.vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
