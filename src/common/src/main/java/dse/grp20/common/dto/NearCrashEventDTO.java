package dse.grp20.common.dto;

import java.io.Serializable;

public class NearCrashEventDTO implements Serializable {

    private static final long serialVersionUID = -7245482283242621646L;
    private GeoDTO location;
    private long time;
    private String vin;

    public NearCrashEventDTO () {};

    public NearCrashEventDTO (GeoDTO location, long time, String vin) {
        this.location = location;
        this.time = time;
        this.vin = vin;
    }

    public GeoDTO getLocation() {
        return this.location;
    }

    public void setLocation(GeoDTO location) {
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
