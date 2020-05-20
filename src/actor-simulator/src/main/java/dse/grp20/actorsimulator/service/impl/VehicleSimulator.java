package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.entity.Geo;
import dse.grp20.actorsimulator.entity.NearCrashEvent;
import dse.grp20.actorsimulator.entity.VehicleControl;
import dse.grp20.actorsimulator.entity.VehicleStatus;
import dse.grp20.actorsimulator.external.IStatusTrackingService;
import dse.grp20.actorsimulator.service.Constants;
import dse.grp20.actorsimulator.service.ITimeService;
import dse.grp20.common.dto.NearCrashEventDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VehicleSimulator {

    private ITimeService timeService;
    private IStatusTrackingService statusTrackingService;

    private static final double CURVE_ADAPTATION_FACTOR = 1000;

    private static final double MAX_ACCELERATION = 7.5;

    private static Logger LOGGER = LoggerFactory.getLogger(VehicleSimulator.class);

    private ModelMapper modelMapper = new ModelMapper();
    private VehicleControl latestControl;
    private List<Geo> route;
    private VehicleStatus currentStatus;

    private Thread workingThread;

    private boolean quit = false;

    public VehicleSimulator(VehicleControl latestControl, List<Geo> route, VehicleStatus currentStatus, ITimeService timeService, IStatusTrackingService statusTrackingService) {
        this.latestControl = latestControl;
        this.route = route;
        this.currentStatus = currentStatus;
        this.timeService = timeService;
        this.statusTrackingService = statusTrackingService;
    }

    public void stop() throws InterruptedException {
        quit = true;
        workingThread.interrupt();
        workingThread.join();
    }

    public void simulate(){
        try {
            LOGGER.info("start simulating");
            workingThread = Thread.currentThread();
            currentStatus.setTime(timeService.getTime());

            while (!quit && route.size() > 0) {

                timeService.sleepRefreshInterval();

                // refresh current position in currentStatus after some time has passed
                long now = timeService.getTime();
                long deltaTime = now - currentStatus.getTime();
                Geo direction = currentStatus.getDirection();
                double directionDistance = Geo.distance(currentStatus.getLocation(), currentStatus.getLocation().plus(direction));
                Geo deltaLocation = direction.scale(currentStatus.getSpeed() * deltaTime / directionDistance / 1000);
                Geo nowLocation = currentStatus.getLocation().plus(deltaLocation);

                LOGGER.info("currentStatus: " + currentStatus);

                currentStatus.setLocation(nowLocation);
                currentStatus.setTime(now);


                if (currentStatus.getLocation().inProximity(Constants.VEHICLE1_NCE_POSITION)) {
                    simulateNearCrashEvent();
                }

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
                    long deltaControlTime = timeService.getTime() - latestControl.getTimestamp();

                    double deltaTargetSpeed = latestControl.getSpeed() - currentStatus.getSpeed();

                    double deltaSpeed = deltaTime / 1000. * MAX_ACCELERATION;

                    if (currentStatus.getSpeed() > latestControl.getSpeed()) {
                        currentStatus.setSpeed(Math.max(currentStatus.getSpeed() - deltaSpeed, latestControl.getSpeed()));
                    } else if (currentStatus.getSpeed() < latestControl.getSpeed()) {
                        currentStatus.setSpeed(Math.min(currentStatus.getSpeed() + deltaSpeed, latestControl.getSpeed()));
                    }

                    if (currentStatus.getSpeed() == latestControl.getSpeed()) {
                        latestControl = null;
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

        } catch (InterruptedException ex) {
            // got interrupted
        }
    }

    private void simulateNearCrashEvent() throws InterruptedException {
        LOGGER.warn("pedestrian detected, emergency break!");
        // simulate break
        long sleepTime = (long)(currentStatus.getSpeed() * 1000. / MAX_ACCELERATION);
        timeService.sleep(sleepTime);

        currentStatus.setSpeed(0);
        LOGGER.info("emitting NCE and waiting 20 s");

        NearCrashEvent nearCrashEvent = new NearCrashEvent();
        nearCrashEvent.setLocation(currentStatus.getLocation());
        nearCrashEvent.setVin(currentStatus.getVin());
        nearCrashEvent.setTime(timeService.getTime());

        statusTrackingService.emitNearCrashEvent(modelMapper.map(nearCrashEvent, NearCrashEventDTO.class));

        timeService.sleep(20000);


        double targetSpeedAfterNCE = 13.8888;
        LOGGER.info("re-accelerating to " + targetSpeedAfterNCE);
        // simulate break
        sleepTime = (long)(targetSpeedAfterNCE * 1000. / MAX_ACCELERATION);
        timeService.sleep(sleepTime);
        currentStatus.setSpeed(targetSpeedAfterNCE);
        LOGGER.info("resuming route with " + targetSpeedAfterNCE);

    }

    public VehicleStatus getCurrentStatus() {
        return currentStatus;
    }

    public void receiveControlStatus(VehicleControl control) {
        latestControl = control;
        // record incoming timestamp
        latestControl.setTimestamp(timeService.getTime());
    }
}
