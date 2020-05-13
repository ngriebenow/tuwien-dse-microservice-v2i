package dse.grp20.actorsimulator.service;

import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class TimeService {

    private static double SIM_SPEED = 1;

    public long getTime() {
        return (long)(Calendar.getInstance().getTimeInMillis() * SIM_SPEED);
    }

    public long getRefreshRateInMs() {
        return (long)(500 / SIM_SPEED);
    }

}
