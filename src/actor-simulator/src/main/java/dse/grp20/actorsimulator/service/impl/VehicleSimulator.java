package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.entity.Geo;
import dse.grp20.actorsimulator.entity.VehicleControl;
import dse.grp20.actorsimulator.entity.VehicleStatus;
import dse.grp20.actorsimulator.external.IStatusTrackingService;
import dse.grp20.actorsimulator.service.ITimeService;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VehicleSimulator {

    private ITimeService timeService;
    private IStatusTrackingService statusTrackingService;

    private static final double CURVE_ADAPTATION_FACTOR = 1000;
    private static final double SPEED_ADAPTATION_FACTOR = 1000;
    private static final double DELTA_SPEED = 1;

    private static Logger LOGGER = LoggerFactory.getLogger(VehicleSimulationService.class);

    private ModelMapper modelMapper = new ModelMapper();
    private VehicleControl latestControl;
    private List<Geo> route;
    private VehicleStatus currentStatus;

    public VehicleSimulator(VehicleControl latestControl, List<Geo> route, VehicleStatus currentStatus, ITimeService timeService, IStatusTrackingService statusTrackingService) {
        this.latestControl = latestControl;
        this.route = route;
        this.currentStatus = currentStatus;
        this.timeService = timeService;
        this.statusTrackingService = statusTrackingService;
    }

    public void simulate() throws InterruptedException {
        LOGGER.info("start simulating");
        currentStatus.setTime(timeService.getTime());

        while (route.size() > 0) {

            Thread.sleep(timeService.getRefreshRate());

            // refresh current position in currentStatus after some time has passed
            long now = timeService.getTime();
            long deltaTime = now - currentStatus.getTime();
            Geo direction = currentStatus.getDirection();
            double directionDistance = Geo.distance(currentStatus.getLocation(), currentStatus.getLocation().plus(direction));
            Geo deltaLocation = direction.scale(currentStatus.getSpeed() * deltaTime / directionDistance / 1000);
            Geo nowLocation = currentStatus.getLocation().plus(deltaLocation);

            currentStatus.setLocation(nowLocation);
            currentStatus.setTime(now);

            // get next target, or break if no more navigation targets available
            Geo nextTarget = null;
            while (route.size() > 0 && nextTarget == null) {
                nextTarget = route.get(0);
                // TODO OPTIONAL: find better logic for passing the next target
                if (currentStatus.getLocation().inProximity(nextTarget)) {
                    LOGGER.info("target reached: " + nextTarget);
                    nextTarget = null;
                    route.remove(0);
                }
            }
            if (nextTarget == null) break;

            // adapt speed if there is a speed recommendation
            if (latestControl != null) {
                if (Math.abs(currentStatus.getSpeed() - latestControl.getSpeed()) < DELTA_SPEED) {
                    latestControl = null;
                } else {
                    double speedAdaptFactor = Math.min(deltaTime / SPEED_ADAPTATION_FACTOR, 1);
                    currentStatus.setSpeed((currentStatus.getSpeed()*(1-speedAdaptFactor) + latestControl.getSpeed()*speedAdaptFactor));
                }
            }

            // calculate direction
            Geo targetDirection = nextTarget.minus(currentStatus.getLocation());
            Geo currentLocation = currentStatus.getLocation();
            Geo normCurrentDir = currentStatus.getDirection().scale(1 / Geo.distance(currentLocation, currentLocation.plus(currentStatus.getDirection())));
            Geo normTargetDir = targetDirection.scale(1 / Geo.distance(currentLocation, currentLocation.plus(targetDirection)));
            double directionAdaptFactor = Math.min(deltaTime / CURVE_ADAPTATION_FACTOR, 1);
            Geo finalDirection = normCurrentDir.scale(1-directionAdaptFactor).plus(normTargetDir.scale(directionAdaptFactor)).scale(1/2.);

            currentStatus.setDirection(finalDirection);


            statusTrackingService.updateVehicle(modelMapper.map(currentStatus, VehicleStatusDTO.class));
        }
    }

    public VehicleStatus getCurrentStatus() {
        return currentStatus;
    }

    public void receiveControlStatus(VehicleControl control) {
        latestControl = control;
    }
}
