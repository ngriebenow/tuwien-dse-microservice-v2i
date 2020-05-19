package dse.grp20.actorsimulator.service;

public interface ITimeService {

    void setSimulationSpeed(double speed);

    long getTime();

    void sleepRefreshInterval() throws InterruptedException;

    void sleep(long milliseconds) throws InterruptedException;
}
