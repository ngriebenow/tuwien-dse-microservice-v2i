package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.service.ITimeService;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class TimeService implements ITimeService {

    private static double SIM_SPEED = 1;

    private long zeroTime;

    public TimeService() {
        zeroTime = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public long getTime() {
        return (long)((Calendar.getInstance().getTimeInMillis() - zeroTime) * SIM_SPEED);
    }

    @Override
    public long getRefreshRate() {
        return (long)(500 / SIM_SPEED);
    }

    @Override
    public void setSimulationSpeed(double speed) {
        SIM_SPEED = speed;
    }

    public static double getSimSpeed() {
        return SIM_SPEED;
    }
}
