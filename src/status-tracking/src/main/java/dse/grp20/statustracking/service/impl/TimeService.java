package dse.grp20.statustracking.service.impl;

import dse.grp20.statustracking.service.ITimeService;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class TimeService implements ITimeService {

    private long zeroTime;
    private double speed;

    @Override
    public void setSimulationSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void setTime(long zeroTime, double simulationSpeed) {
        this.zeroTime = zeroTime;
        this.speed = simulationSpeed;
    }

    @Override
    public long getTime() {
        return (long)((Calendar.getInstance().getTimeInMillis() - this.zeroTime) * this.speed);
    }
}
