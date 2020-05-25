package dse.grp20.common.dto;

import java.io.Serializable;

public class TrafficLightPlanDTO implements Serializable {

    private static final long serialVersionUID = -8646310830561537270L;
    TrafficLightDTO trafficLight;
    VehicleStatusDTO vehicleStatus;

    public TrafficLightPlanDTO () {}

    public TrafficLightPlanDTO (TrafficLightDTO trafficLight, VehicleStatusDTO vehicleStatus) {
        this.trafficLight = trafficLight;
        this.vehicleStatus = vehicleStatus;
    }
    public TrafficLightDTO getTrafficLightDTO() {
        return this.trafficLight;
    }

    public void setTrafficLightDTO(TrafficLightDTO trafficLight) {
        this.trafficLight = trafficLight;
    }

    public VehicleStatusDTO getVehicleStatusDTO() {
        return this.vehicleStatus;
    }

    public void setVehicleStatusDTO(VehicleStatusDTO vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
