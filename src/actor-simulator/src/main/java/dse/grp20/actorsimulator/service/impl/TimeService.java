package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.service.ITimeService;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Implementation of the FluxKompensator by ZT Dr. DI Johannes Weidl-Rektenwald
 * (see http://coopxarch.blogspot.com/2011/05/der-fluxkompensator-und-seine-bedeutung.html)
 * This service manages time.
 */
@Component
public class TimeService implements ITimeService {

    // simulation speed factor
    private double simSpeed = 1;

    // zero time
    private long zeroTime;

    // call this method instead of Calendar.getInstance().getTimeInMillis()
    @Override
    public long getTime() {
        return (long)((Calendar.getInstance().getTimeInMillis() - zeroTime) * simSpeed);
    }

    @Override
    public void setSimulationSpeed(double speed) {
        simSpeed = speed;
    }

    // reset zero time
    @Override
    public void restartSimulation() {
        zeroTime = Calendar.getInstance().getTimeInMillis();
    }

    // call this method instead of Thread.sleep(). If we have a simulation factor != 1, we also need to adapt the thread sleep time in this wrapper.
    public void sleep(long milliseconds) throws InterruptedException {
        Thread.sleep((long)(milliseconds / simSpeed));
    }

    // block for the specified refresh interval for vehicles
    @Override
    public void sleepRefreshInterval() throws InterruptedException {
        Thread.sleep((long)(500 /simSpeed));
    }
}
