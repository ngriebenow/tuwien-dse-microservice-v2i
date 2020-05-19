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

public class TrafficLightSimulator {

    private static long SWITCHING_INTERVAL = 20000;
    private static long FORWARD_PLANNING_TIME = 600000;

    private static long SCAN_INTERVAL = 2000;

    private ITimeService timeService;
    private IStatusTrackingService statusTrackingService;

    private List<TrafficLightStatus> scheduledTrafficLightStati = new LinkedList<>();

    private TrafficLight trafficLight;
    private TrafficLightStatus currentStatus;

    private TrafficLightControl latestControl = null;

    private ModelMapper modelMapper = new ModelMapper();

    private Thread workerThread;

    private Thread scanThread;

    private static Logger LOGGER = LoggerFactory.getLogger(TrafficLightSimulator.class);

    public TrafficLightSimulator(ITimeService timeService, IStatusTrackingService statusTrackingService, TrafficLight trafficLight, TrafficLightStatus currentStatus) {
        this.timeService = timeService;
        this.statusTrackingService = statusTrackingService;
        this.trafficLight = trafficLight;
        this.currentStatus = currentStatus;

        scheduledTrafficLightStati.add(currentStatus);
        continueSchedule(currentStatus);
    }

    public void stop() throws InterruptedException{
        workerThread.interrupt();
        scanThread.interrupt();
        workerThread.join();
        scanThread.join();
    }

    public void simulate() {
        scanThread = new Thread(() -> {
            TrafficLightDTO trafficLightDTO = modelMapper.map(trafficLight,TrafficLightDTO.class);
            while (true) {
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

        while (true) {

            TrafficLightStatus nextTargetStatus = scheduledTrafficLightStati.get(0);
            long deltaNextActionTime = nextTargetStatus.getFrom() - timeService.getTime();
            try {
                timeService.sleep(Math.max(0, deltaNextActionTime));
            } catch (InterruptedException e) {
                // urgent control
                LOGGER.info("interrupted due to recent control status");
                if (latestControl == null) {
                    return;
                }
            }


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

                List<TrafficLightStatusDTO> dtoList = scheduledTrafficLightStati.stream().map(s ->
                        modelMapper.map(s, TrafficLightStatusDTO.class))
                        .collect(Collectors.toList());
                statusTrackingService.updateTrafficLightSchedule(dtoList);

                latestControl = null;
            }

            nextTargetStatus = scheduledTrafficLightStati.get(0);
            if (nextTargetStatus.getFrom() < timeService.getTime()) {
                LOGGER.info("set new status to " + nextTargetStatus);
                scheduledTrafficLightStati.remove(0);
                currentStatus.setLight(nextTargetStatus.getLight());
                statusTrackingService.updateTrafficLight(modelMapper.map(currentStatus, TrafficLightStatusDTO.class));

                TrafficLightStatus lastFutureStatus = scheduledTrafficLightStati.get(scheduledTrafficLightStati.size()-1);
                continueSchedule(lastFutureStatus);

                List<TrafficLightStatusDTO> dtoList = scheduledTrafficLightStati.stream().map(s ->
                        modelMapper.map(s, TrafficLightStatusDTO.class))
                        .collect(Collectors.toList());
                statusTrackingService.updateTrafficLightSchedule(dtoList);
            }
        }
    }

    private void continueSchedule(TrafficLightStatus lastTrafficLightStatus) {

        long nextTime = lastTrafficLightStatus.getFrom() + SWITCHING_INTERVAL;
        Light nextLight = (lastTrafficLightStatus.getLight() == Light.GREEN) ? Light.RED : Light.GREEN;

        long maxScheduleTime = scheduledTrafficLightStati.get(0).getFrom() + FORWARD_PLANNING_TIME;

        while (nextTime < maxScheduleTime) {
            TrafficLightStatus plannedStatus = new TrafficLightStatus();
            plannedStatus.setLight(nextLight);
            plannedStatus.setFrom(nextTime);
            plannedStatus.setTrafficLightId(trafficLight.getId());

            scheduledTrafficLightStati.add(plannedStatus);

            nextLight = (nextLight == Light.GREEN) ? Light.RED : Light.GREEN;
            nextTime = nextTime + SWITCHING_INTERVAL;
        }
    }

    public void receiveControlStatus(TrafficLightControlDTO control) {
        latestControl = modelMapper.map(control, TrafficLightControl.class);
        LOGGER.info("receiving latest control status " + latestControl);
        workerThread.interrupt();
    }
}
