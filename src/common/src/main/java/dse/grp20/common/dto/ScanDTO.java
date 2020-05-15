package dse.grp20.common.dto;

import java.util.List;

public class ScanDTO {

    private List<TrafficLightStatusDTO> trafficLightStati;
    private List<VehicleStatusDTO> vehicleStati;

    public ScanDTO(List<TrafficLightStatusDTO> trafficLightStati, List<VehicleStatusDTO> vehicleStati) {
        this.trafficLightStati = trafficLightStati;
        this.vehicleStati = vehicleStati;
    }

    public List<TrafficLightStatusDTO> getTrafficLightStati() {
        return this.trafficLightStati;
    }

    public void setTrafficLightStati(List<TrafficLightStatusDTO> trafficLightStati) {
        this.trafficLightStati = trafficLightStati;
    }

    public List<VehicleStatusDTO> getVehicleStati() {
        return this.vehicleStati;
    }

    public void setVehicleStati(List<VehicleStatusDTO> vehicleStati) {
        this.vehicleStati = vehicleStati;
    }
}
