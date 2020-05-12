package dse.grp20.actorsimulator.service;

import dse.grp20.actorsimulator.entity.Geo;
import dse.grp20.actorsimulator.entity.Vehicle;
import dse.grp20.actorsimulator.entity.VehicleStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class Vehicle1Simulator {

    private static final long REFRESH_RATE_MS = 2000;

    private List<VehicleStatus> latestControlStati = new ArrayList<>();

    private List<Geo> route = Constants.VEHICLE1_ROUTE;

    private VehicleStatus currentStatus;

    public VehicleStatus getCurrentStatus() {
        return currentStatus;
    }

    public void simulate() throws InterruptedException {

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId("WVWZZZ1JZ3W386752");
        vehicle1.setName("Volkswagen Golf IV");

        currentStatus = new VehicleStatus();
        currentStatus.setLocation(Constants.VEHICLE1_INITIAL_POSITION);
        //currentStatus.setVelocity(Constants.VEHICLE1_ENTRY_A_POSITION.minus(Constants.VEHICLE1_INITIAL_POSITION));
        currentStatus.setSpeed(20);
        currentStatus.setVehicleId(vehicle1.getId());
        currentStatus.setTime(Calendar.getInstance().getTimeInMillis());

        while (route.size() > 0) {

            Thread.sleep(REFRESH_RATE_MS);

            VehicleStatus lastStatus = currentStatus;

            // TODO: use time as long?
            // refresh current position in currentStatus after some time has passed
            long now = Calendar.getInstance().getTimeInMillis();
            long deltaTime = now - lastStatus.getTime();
            Geo deltaLocation = lastStatus.getVelocity().scale(deltaTime / 1000);
            Geo nowLocation = lastStatus.getLocation().plus(deltaLocation);
            double currentSpeed = Geo.distance(currentStatus.getLocation(), nowLocation) * 1000 / deltaTime;

            System.out.println("SPEED: " + currentSpeed + " m/s");
            currentStatus.setLocation(nowLocation);
            currentStatus.setTime(now);


            // get next target, or break if no more navigation targets available
            Geo nextTarget = null;
            while (route.size() > 0 && nextTarget == null) {
                nextTarget = route.get(0);
                if (currentStatus.getLocation().inProximity(nextTarget)) {
                    System.out.println(">> TARGET REACHED " + nextTarget);
                    nextTarget = null;
                    route.remove(0);
                }
            }
            if (nextTarget == null) break;

            // if available, use next control status for navigation
            if (latestControlStati.size() > 0) {
                VehicleStatus nextControlStatus = latestControlStati.get(0);
                nextTarget = nextControlStatus.getLocation();
                if (currentStatus.getLocation().inProximity(nextTarget)) {
                    // next control status is reached, delete it and overtake control status information
                    latestControlStati.remove(0);
                    currentStatus.setVelocity(nextControlStatus.getVelocity());
                    continue;
                }
            }

            //Geo deltaTarget = nextTarget.minus(currentStatus.getLocation());
            //double distance = Geo.distance(currentStatus.getLocation(), nextTarget);
            //Geo targetVelocity = deltaTarget.scale(currentSpeed / distance);

            // change direction, but use current speed to fit to next control status
            //currentStatus.setVelocity(targetVelocity);

        }
    }

    public void receiveControlStati(List<VehicleStatus> stati) {
        latestControlStati = stati;
    }

}
