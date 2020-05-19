package dse.grp20.statustracking.entities;

import dse.grp20.common.dto.GeoDTO;
import dse.grp20.common.dto.VehicleDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

@Document(collection = "VehicleStatus")
public class VehicleStatus implements Serializable {

    private static final long serialVersionUID = -1620525001941958403L;
    private GeoDTO location;
    private GeoDTO direction;
    private double speed;

    private long time;

    private String vin;

    @Id
    @MongoId
    private String id;

    public VehicleStatus() {}

    public VehicleStatus(GeoDTO location, GeoDTO direction, String vin, long time, double speed) {
        this.location = location;
        this.direction = direction;
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

    public GeoDTO getLocation() {
        return this.location;
    }

    public void setLocation(GeoDTO location) {
        this.location = location;
    }

    public GeoDTO getDirection() {
        return this.direction;
    }

    public void setDirection(GeoDTO direction) {
        this.direction = direction;
    }

    public long getTime() {
        return this.time;
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
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
