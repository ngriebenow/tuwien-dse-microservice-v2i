package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.entity.Light;
import dse.grp20.actorsimulator.entity.TrafficLight;
import dse.grp20.actorsimulator.entity.TrafficLightControl;
import dse.grp20.actorsimulator.entity.TrafficLightStatus;
import dse.grp20.actorsimulator.external.IStatusTrackingService;
import dse.grp20.actorsimulator.service.ITimeService;
import dse.grp20.common.dto.TrafficLightControlDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class simulates a single traffic light.
 */
public class TrafficLightSimulator {

    // interval between switching from red to gren and vice versa
    private static long SWITCHING_INTERVAL = 20000;

    // how long should the traffic light plan its schedule ahead?
    private static long FORWARD_PLANNING_TIME = 600000;

    // after how many ms should the traffic light trigger a scan in the backend?
    private static long SCAN_INTERVAL = 2000;

    private ITimeService timeService;
    private IStatusTrackingService statusTrackingService;

    // planned (not yet realized) traffic light stati
    private List<TrafficLightStatus> scheduledTrafficLightStati = new LinkedList<>();

    private TrafficLight trafficLight;
    private TrafficLightStatus currentStatus;

    // the latest control received from the ActorControlService
    private TrafficLightControl latestControl = null;

    private ModelMapper modelMapper = new ModelMapper();

    private Thread workerThread;
    private Thread scanThread;

    private static Logger LOGGER = LoggerFactory.getLogger(TrafficLightSimulator.class);

    private boolean quit = false;

    public TrafficLightSimulator(ITimeService timeService, IStatusTrackingService statusTrackingService, TrafficLight trafficLight, TrafficLightStatus currentStatus) {
        this.timeService = timeService;
        this.statusTrackingService = statusTrackingService;
        this.trafficLight = trafficLight;
        this.currentStatus = currentStatus;

        continueSchedule(currentStatus);
    }

    public void stop() throws InterruptedException{
        quit = true;
        workerThread.interrupt();
        scanThread.interrupt();
        workerThread.join();
        scanThread.join();
    }

    public void simulate() {
        scanThread = new Thread(() -> {
            TrafficLightDTO trafficLightDTO = modelMapper.map(trafficLight,TrafficLightDTO.class);
            while (!quit) {
                statusTrackingService.scanTrafficLight(trafficLightDTO);
                try {
                    timeService.sleep(SCAN_INTERVAL);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        scanThread.start();

        workerThread = Thread.currentThread();
        LOGGER.info("start simulation with initial status " + currentStatus);

        while (!quit) {

            // get time planned status in the schedule and sleep as long as necessary. If a new control gets in,
            // the thread gets interrupted to check if it is already to time to react to the control.
            TrafficLightStatus nextTargetStatus = scheduledTrafficLightStati.get(0);
            long deltaNextActionTime = nextTargetStatus.getFrom() - timeService.getTime();
            try {
                LOGGER.info("sleeping for " + deltaNextActionTime + " seconds");
                timeService.sleep(Math.max(0, deltaNextActionTime));
            } catch (InterruptedException e) {
                // urgent control
                LOGGER.info("interrupted due to recent control status");
                if (latestControl == null) {
                    return;
                }
            }

            // check if there is a control from ActorControlService
            if (latestControl != null) {
                LOGGER.info("received control status " + latestControl);

                // delete all stati that become invalid because they are in the future of latestControl
                scheduledTrafficLightStati = scheduledTrafficLightStati.stream().filter(s -> s.getFrom() < latestControl.getFrom()).collect(Collectors.toList());

                TrafficLightStatus controlStatus = new TrafficLightStatus();
                controlStatus.setFrom(latestControl.getFrom());
                controlStatus.setLight(latestControl.getLight());
                controlStatus.setTrafficLightId(trafficLight.getId());

                // add the control status as schedule
                scheduledTrafficLightStati.add(controlStatus);

                // plan next schedule
                continueSchedule(controlStatus);

                latestControl = null;
            }

            // refresh next target status in case the control status is the newest one
            nextTargetStatus = scheduledTrafficLightStati.get(0);
            if (nextTargetStatus.getFrom() < timeService.getTime()) {

                // apply new status
                LOGGER.info("set new status to " + nextTargetStatus);
                scheduledTrafficLightStati.remove(0);
                currentStatus.setLight(nextTargetStatus.getLight());
                currentStatus.setFrom(nextTargetStatus.getFrom());

                // deliver status change to status tracking service
                statusTrackingService.updateTrafficLight(modelMapper.map(currentStatus, TrafficLightStatusDTO.class));

                // schedule
                TrafficLightStatus lastFutureStatus = scheduledTrafficLightStati.get(scheduledTrafficLightStati.size()-1);
                continueSchedule(lastFutureStatus);

            }
        }
    }

    // this method plans the schedule ahead of the provided latestTrafficLightStatus
    private void continueSchedule(TrafficLightStatus lastTrafficLightStatus) {
        // determine following state
        long nextTime = lastTrafficLightStatus.getFrom() + SWITCHING_INTERVAL;
        Light nextLight = (lastTrafficLightStatus.getLight() == Light.GREEN) ? Light.RED : Light.GREEN;

        // calculate max time to which this method schedules
        long maxScheduleTime;
        if (scheduledTrafficLightStati.size() > 0) {
            maxScheduleTime = scheduledTrafficLightStati.get(0).getFrom() + FORWARD_PLANNING_TIME;
        } else {
            maxScheduleTime = lastTrafficLightStatus.getFrom() + FORWARD_PLANNING_TIME;
        }

        // iteratively plan new upcoming schedules
        while (nextTime <= maxScheduleTime) {
            TrafficLightStatus plannedStatus = new TrafficLightStatus();
            plannedStatus.setLight(nextLight);
            plannedStatus.setFrom(nextTime);
            plannedStatus.setTrafficLightId(trafficLight.getId());

            scheduledTrafficLightStati.add(plannedStatus);

            nextLight = (nextLight == Light.GREEN) ? Light.RED : Light.GREEN;
            nextTime = nextTime + SWITCHING_INTERVAL;
        }

        LOGGER.info("delivering new schedule with first element " + scheduledTrafficLightStati.get(0));

        // deliver new planned schedule to the status tracking service
        List<TrafficLightStatusDTO> dtoList = scheduledTrafficLightStati.stream().map(s ->
                modelMapper.map(s, TrafficLightStatusDTO.class))
                .collect(Collectors.toList());
        statusTrackingService.updateTrafficLightSchedule(dtoList);
    }

    // this method is called from another thread, setting only the latest control status. So the traffic light is
    // only interested in the latest control status of the actor control service.
    public void receiveControlStatus(TrafficLightControlDTO control) {
        latestControl = modelMapper.map(control, TrafficLightControl.class);
        LOGGER.info("receiving latest control status " + latestControl);
        workerThread.interrupt();
    }
}
