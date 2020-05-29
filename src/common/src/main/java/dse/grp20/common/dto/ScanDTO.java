package dse.grp20.common.dto;

import java.io.Serializable;
import java.util.List;

public class ScanDTO implements Serializable {

    private static final long serialVersionUID = 1902323176359433426L;
    private List<TrafficLightStatusDTO> trafficLightStati;
    private List<VehicleStatusDTO> vehicleStati;
    private TrafficLightDTO trafficLight;

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

    public TrafficLightDTO getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(TrafficLightDTO trafficLight) {
        this.trafficLight = trafficLight;
    }
}
