package dse.grp20.actorcontrol.services.impl;

import dse.grp20.actorcontrol.services.IControlService;
import dse.grp20.actorcontrol.services.IPlanService;
import dse.grp20.actorcontrol.services.ITimeService;
import dse.grp20.actorcontrol.utils.Utils;
import dse.grp20.common.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PlanService implements IPlanService {

    Logger logger = LoggerFactory.getLogger("PanService");

    @Autowired
    private IControlService controlService;

    @Autowired
    @Qualifier("fluxkompensator") // TODO remove for production
    private ITimeService timeService;

    @Override
    public void planVehicle(List<TrafficLightStatusDTO> trafficLightStatusDTOList, // all future stati from one trafficlight
                            List<VehicleStatusDTO> vehicleStatusDTOList) // all scanned vehicles from that trafficlight
    {
        List<VehicleControlDTO> vehicleControlDTOList = new ArrayList<>();

        // iterate through list and plan every vehicle
        for (VehicleStatusDTO vehicle : vehicleStatusDTOList) {
            double newSpeed = calculateSpeed(vehicle, trafficLightStatusDTOList);
            vehicleControlDTOList.add(new VehicleControlDTO(vehicle.getVin(), newSpeed));
        }

        controlService.controlVehicles(vehicleControlDTOList);
    }

    public double calculateSpeed(VehicleStatusDTO vehicleStatusDTO, List<TrafficLightStatusDTO> trafficLightStatusDTOList) {
        // extract positions
        GeoDTO trafficLightPos = trafficLightStatusDTOList.get(0).getTrafficLightDTO().getLocation();
        GeoDTO vehiclePos = vehicleStatusDTO.getLocation();

        // calculate distance
        double distance = Utils.distance(vehiclePos, trafficLightPos);
        double speed = vehicleStatusDTO.getSpeed();
        logger.info("distance: {}", distance);
        logger.info("speed: {}", speed);

        // calculate init travel time
        long timeOfArrivalInMilliseconds = Math.round((distance / speed) * 1000);
        logger.info("timeOfArrivalInMilliseconds: {}", timeOfArrivalInMilliseconds);

        // calculate current and arrival time
        long currentTime = timeService.getTime();
        long arrivalTime = currentTime + timeOfArrivalInMilliseconds;
        logger.info("currentTime: {}", currentTime);
        logger.info("arrivalTime: {}", arrivalTime);


        // assert list is sorted
        // get nearest greenphase before and after init-arrivalTime
        TrafficLightStatusDTO minTL = null;
        TrafficLightStatusDTO maxTL = null;
        for (TrafficLightStatusDTO trafficLight : trafficLightStatusDTOList) {
            logger.info("Next greenphase: {}", trafficLight.getFrom());
            if (minTL == null) {
                minTL = trafficLight;
                continue;
            }
            if (trafficLight.getLight() == LightDTO.GREEN && arrivalTime < trafficLight.getFrom()) {
                minTL = trafficLight;
                continue;
            }
            maxTL = trafficLight;
            break;
        }
        assert minTL != null;
        assert maxTL != null;
        logger.info("MinTL: {}", minTL.getFrom());
        logger.info("MaxTL: {}", maxTL.getFrom());

        // take the closest greenphase
        TrafficLightStatusDTO nextTL = (Math.abs(minTL.getFrom()-arrivalTime) < Math.abs(maxTL.getFrom()-arrivalTime)) ? minTL : maxTL;
        long targetArrivalTime = nextTL.getFrom();
        logger.info("targetArrivalTime: {}", targetArrivalTime);

        // calculate new speed
        double newSpeed = (distance / (Math.abs(targetArrivalTime - currentTime))) * 1000;
        logger.info("New speed: {}", newSpeed);

        return newSpeed;
    }

    @Override
    public void planTrafficLight(TrafficLightPlanDTO trafficLightPlanDTO) {
        List<TrafficLightControlDTO> trafficLightControlDTOList = new ArrayList<>();

        long newGreenphase = calculateGreenphase(trafficLightPlanDTO);
        trafficLightControlDTOList.add(new TrafficLightControlDTO(
                trafficLightPlanDTO.getTrafficLightId(),
                LightDTO.GREEN,
                newGreenphase
                )
        );

        controlService.controlTrafficLights(trafficLightControlDTOList);
    }

    public long calculateGreenphase(TrafficLightPlanDTO trafficLightPlanDTO) {
        // extract positions
        GeoDTO trafficLightPos = trafficLightPlanDTO.getTrafficLightLocation();
        GeoDTO vehiclePos = trafficLightPlanDTO.getVehicleLocation();

        // calculate distance
        double distance = Utils.distance(vehiclePos, trafficLightPos);
        double speed = trafficLightPlanDTO.getSpeed();
        logger.info("distance: {}", distance);
        logger.info("speed: {}", speed);

        // calculate init travel time
        long timeOfArrivalInMilliseconds = Math.round((distance / speed) * 1000);
        logger.info("timeOfArrivalInMilliseconds: {}", timeOfArrivalInMilliseconds);

        // calculate current and arrival time
        long currentTime = timeService.getTime();
        long arrivalTime = currentTime + timeOfArrivalInMilliseconds;
        logger.info("currentTime: {}", currentTime);
        logger.info("arrivalTime: {}", arrivalTime);

        // TODO add time buffer of 1/2 seconds?
        return arrivalTime;
    }
}
