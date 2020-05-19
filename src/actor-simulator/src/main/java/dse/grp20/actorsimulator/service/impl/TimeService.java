package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.service.ITimeService;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class TimeService implements ITimeService {

    private double simSpeed = 10;

    private long zeroTime;

    public TimeService() {
        zeroTime = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public long getTime() {
        return (long)((Calendar.getInstance().getTimeInMillis() - zeroTime) * simSpeed);
    }

    @Override
    public void setSimulationSpeed(double speed) {
        simSpeed = speed;
    }

    public double getSimSpeed() {
        return simSpeed;
    }

    public void sleep(long milliseconds) throws InterruptedException {
        Thread.sleep((long)(milliseconds / simSpeed));
    }

    @Override
    public void sleepRefreshInterval() throws InterruptedException {
        Thread.sleep((long)(500 /simSpeed));
    }
}
