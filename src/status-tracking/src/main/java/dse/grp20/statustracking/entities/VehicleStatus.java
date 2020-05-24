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
    private Geo location;
    private Geo velocity;

    private long timeStamp;

    private VehicleDTO vehicle;

    @Id
    @MongoId
    private String id;

    public VehicleStatus() {}

    public VehicleStatus(GeoDTO location, GeoDTO velocity, VehicleDTO vehicle, long timeStamp) {
        this.location = new Geo("Point", location.getLongitude(), location.getLatitude());
        this.velocity = new Geo("Point", velocity.getLongitude(), velocity.getLatitude());
        this.vehicle = vehicle;
        this.timeStamp = timeStamp;
    }

    public VehicleStatus(VehicleStatusDTO dto) {
        this(dto.getLocation(), dto.getVelocity(), dto.getVehicle(), dto.getTimeStamp());
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

    public Geo getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Geo velocity) {
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
