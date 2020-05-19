package dse.grp20.actorsimulator.service;

public interface ITimeService {

    void setSimulationSpeed(double speed);

    long getTime();

    long getRefreshRate();
}
