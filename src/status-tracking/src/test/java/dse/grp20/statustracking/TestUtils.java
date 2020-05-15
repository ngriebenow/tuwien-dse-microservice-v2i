package dse.grp20.statustracking;

import dse.grp20.common.dto.*;

public class TestUtils {



    public static VehicleStatusDTO createVehicleStatus(GeoDTO location, long timestamp, VehicleDTO vehicle, GeoDTO velocity) {
        VehicleStatusDTO vehicleStatusDTO = new VehicleStatusDTO();
        vehicleStatusDTO.setLocation(location);
        vehicleStatusDTO.setTimeStamp(timestamp);
        vehicleStatusDTO.setVehicle(vehicle);
        vehicleStatusDTO.setVelocity(velocity);

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
        vehicleDTO.setId(id);

        return vehicleDTO;
    }

    public static TrafficLightDTO createTrafficLight(long id, GeoDTO location, Double radius) {
        TrafficLightDTO trafficLightDTO = new TrafficLightDTO();
        trafficLightDTO.setId(id);
        trafficLightDTO.setLocation(location);
        trafficLightDTO.setScanRadius(radius);
        return trafficLightDTO;
    }

    public static TrafficLightStatusDTO createTrafficLightStatus(TrafficLightDTO trafficLightDTO, LightDTO lightDTO, long from) {
        TrafficLightStatusDTO trafficLightStatusDTO = new TrafficLightStatusDTO();
        trafficLightStatusDTO.setTrafficLight(trafficLightDTO);
        trafficLightStatusDTO.setLightDTO(lightDTO);
        trafficLightStatusDTO.setFrom(from);
        return trafficLightStatusDTO;
    }
}
