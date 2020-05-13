package dse.grp20.actorsimulator.service;

import dse.grp20.actorsimulator.entity.Geo;
import dse.grp20.actorsimulator.entity.VehicleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VehicleSimulator {

    // TODO: remove new TimeService()
    @Autowired
    private TimeService timeService = new TimeService();

    private static final double CURVE_ADAPTATION_FACTOR = 1000;
    private static final double SPEED_ADAPTATION_FACTOR = 1000;

    private static Logger LOGGER = LoggerFactory.getLogger(Simulator.class);

    private VehicleStatus latestControlStatus;
    private List<Geo> route;
    private VehicleStatus currentStatus;

    public VehicleSimulator(VehicleStatus latestControlStatus, List<Geo> route, VehicleStatus currentStatus) {
        this.latestControlStatus = latestControlStatus;
        this.route = route;
        this.currentStatus = currentStatus;
    }

    public void simulate() throws InterruptedException {
        LOGGER.info("start simulating");
        currentStatus.setTime(timeService.getTime());

        while (route.size() > 0) {

            Thread.sleep(timeService.getRefreshRateInMs());

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
            if (latestControlStatus != null) {
                double speedAdaptFactor = Math.min(deltaTime / SPEED_ADAPTATION_FACTOR, 1);
                currentStatus.setSpeed((currentStatus.getSpeed()*(1-speedAdaptFactor) + latestControlStatus.getSpeed()*speedAdaptFactor));
                if (latestControlStatus.getLocation().inProximity(nextTarget)) {
                    latestControlStatus = null;
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

            // TODO: update current status for status-tracking service
        }
    }

    public VehicleStatus getCurrentStatus() {
        return currentStatus;
    }

    // TODO: thread-safety for latestControlStatus
    public void receiveControlStatus(VehicleStatus status) {
        latestControlStatus = status;
    }
}
