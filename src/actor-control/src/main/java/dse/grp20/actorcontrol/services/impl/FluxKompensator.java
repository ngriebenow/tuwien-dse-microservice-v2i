package dse.grp20.actorcontrol.services.impl;

import dse.grp20.actorcontrol.services.ITimeService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of the FluxKompensator by ZT Dr. DI Johannes Weidl-Rektenwald
 * (see http://coopxarch.blogspot.com/2011/05/der-fluxkompensator-und-seine-bedeutung.html)
 * This service manages time.
 */
@ApplicationScope
@Component("fluxkompensator")
public class FluxKompensator implements ITimeService {
    private final Calendar cal;
    private long initialTimestamp;
    private static final long DEFAULT_SAMPLE_INCREMENT = 0;
    private long sampleIncrement = DEFAULT_SAMPLE_INCREMENT;
    private long dateHolder = 0;

    // simulation speed factor
    private double simSpeed = 1;

    // zero time
    private long zeroTime;

    public FluxKompensator() {
        cal = Calendar.getInstance();
        cal.clear();
        cal.set(2012, 1, 1, 13, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        initialTimestamp = cal.getTimeInMillis();
    }

    // call this method instead of Calendar.getInstance().getTimeInMillis()
    @Override
    public long getTime() {
        final long previous = dateHolder;
        final long now = (previous != 0) ? (previous + sampleIncrement) : initialTimestamp;
        dateHolder = now;
        return now;
    }
    @Override
    public void setSampleIncrement(long value) {
        sampleIncrement = value;
    }
    @Override
    public void setSampleValue(long sampleValue) {
        dateHolder = sampleValue;
    }

    @Override
    public synchronized void reset() {
        cal.clear();
        cal.set(2012, 1, 1, 13, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        initialTimestamp = cal.getTimeInMillis();
        sampleIncrement = DEFAULT_SAMPLE_INCREMENT;
        dateHolder = 0;
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
