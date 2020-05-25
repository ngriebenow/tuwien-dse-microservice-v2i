package dse.grp20.statustracking;

import dse.grp20.common.dto.*;
import dse.grp20.statustracking.entities.TrafficLightStatus;

public class TestUtils {



    public static VehicleStatusDTO createVehicleStatus(GeoDTO location, long timestamp, String vehicle, GeoDTO velocity) {
        VehicleStatusDTO vehicleStatusDTO = new VehicleStatusDTO();
        vehicleStatusDTO.setLocation(location);
        vehicleStatusDTO.setTime(timestamp);
        vehicleStatusDTO.setVin(vehicle);
        vehicleStatusDTO.setDirection(velocity);

        return vehicleStatusDTO;
    }

    public static GeoDTO createGeoDTO(Double longitude, Double latitude) {
        GeoDTO geoDTO = new GeoDTO();
        geoDTO.setLongitude(longitude);
        geoDTO.setLatitude(latitude);

        return geoDTO;
    }

    public static VehicleDTO createVehicleDTO(String id) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setVin(id);

        return vehicleDTO;
    }

    public static TrafficLightDTO createTrafficLight(long id, GeoDTO location, Double radius) {
        TrafficLightDTO trafficLightDTO = new TrafficLightDTO();
        trafficLightDTO.setId(id);
        trafficLightDTO.setLocation(location);
        trafficLightDTO.setScanRadius(radius);
        return trafficLightDTO;
    }

    public static TrafficLightStatusDTO createTrafficLightStatus(Long trafficLightDTO, LightDTO lightDTO, long from) {
        TrafficLightStatusDTO trafficLightStatusDTO = new TrafficLightStatusDTO();
        trafficLightStatusDTO.setTrafficLightId(trafficLightDTO);
        trafficLightStatusDTO.setLight(lightDTO);
        trafficLightStatusDTO.setFrom(from);
        return trafficLightStatusDTO;
    }

    public static TrafficLightStatus convertDTOtoEntity(TrafficLightStatusDTO dto) {
        return new TrafficLightStatus(dto);
    }
}
