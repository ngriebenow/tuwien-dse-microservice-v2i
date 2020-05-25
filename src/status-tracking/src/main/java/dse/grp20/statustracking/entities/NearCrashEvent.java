package dse.grp20.statustracking.entities;

import dse.grp20.common.dto.GeoDTO;
import dse.grp20.common.dto.NearCrashEventDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "NearCrashEvents")
public class NearCrashEvent implements Serializable {


    private static final long serialVersionUID = -8178702587672279222L;

    private Geo location;
    private long time;
    private String vin;

    @Id
    private String id;

    public NearCrashEvent () {}

    public NearCrashEvent (GeoDTO location, long time, String vin) {
        this.location = new Geo("Point", location.getLongitude(), location.getLatitude());
        this.time = time;
        this.vin = vin;
    }

    public NearCrashEvent (NearCrashEventDTO dto) {
        this(dto.getLocation(), dto.getTime(), dto.getVin());
    }

    public Geo getLocation() {
        return this.location;
    }

    public void setLocation(Geo location) {
        this.location = location;
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
}
