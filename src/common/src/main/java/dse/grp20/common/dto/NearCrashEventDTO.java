package dse.grp20.common.dto;

import java.io.Serializable;
import java.net.ServerSocket;

public class NearCrashEventDTO implements Serializable {

    private GeoDTO location;
    private long time;
    private String vin;

    public GeoDTO getLocation() {
        return location;
    }

    public void setLocation(GeoDTO location) {
        this.location = location;
    }

    public long getTime() {
        return time;
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
}
