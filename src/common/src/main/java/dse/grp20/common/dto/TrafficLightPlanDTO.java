package dse.grp20.common.dto;


public class TrafficLightPlanDTO {

    private long trafficLightId;
    private GeoDTO trafficLightLocation;

    private String vin;
    private double speed;
    private GeoDTO vehicleLocation;

    public long getTrafficLightId() {
        return trafficLightId;
    }

    public void setTrafficLightId(long trafficLightId) {
        this.trafficLightId = trafficLightId;
    }

    public GeoDTO getTrafficLightLocation() {
        return trafficLightLocation;
    }

    public void setTrafficLightLocation(GeoDTO trafficLightLocation) {
        this.trafficLightLocation = trafficLightLocation;
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

    public GeoDTO getVehicleLocation() {
        return vehicleLocation;
    }

    public void setVehicleLocation(GeoDTO vehicleLocation) {
        this.vehicleLocation = vehicleLocation;
    }
}
