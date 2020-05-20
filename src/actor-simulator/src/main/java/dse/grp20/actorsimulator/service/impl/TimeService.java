package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.service.ITimeService;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class TimeService implements ITimeService {

    private double simSpeed = 2;
    private long zeroTime;

    @Override
    public long getTime() {
        return (long)((Calendar.getInstance().getTimeInMillis() - zeroTime) * simSpeed);
    }

    @Override
    public void setSimulationSpeed(double speed) {
        simSpeed = speed;
    }

    @Override
    public void restartSimulation() {
        zeroTime = Calendar.getInstance().getTimeInMillis();
    }

    public void sleep(long milliseconds) throws InterruptedException {
        Thread.sleep((long)(milliseconds / simSpeed));
    }

    @Override
    public void sleepRefreshInterval() throws InterruptedException {
        Thread.sleep((long)(500 /simSpeed));
    }
}
