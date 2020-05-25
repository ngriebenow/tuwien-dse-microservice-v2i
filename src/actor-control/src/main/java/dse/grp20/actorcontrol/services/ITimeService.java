package dse.grp20.actorcontrol.services;

public interface ITimeService {

    void setSimulationSpeed(double speed);

    long getTime();

    void setSampleIncrement(long value);

    void setSampleValue(long sampleValue);

    void reset();

    void sleepRefreshInterval() throws InterruptedException;

    void sleep(long milliseconds) throws InterruptedException;

    void restartSimulation();
}
