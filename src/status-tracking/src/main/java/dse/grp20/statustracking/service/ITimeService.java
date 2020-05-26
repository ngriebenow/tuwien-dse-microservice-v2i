package dse.grp20.statustracking.service;

public interface ITimeService {

        void setSimulationSpeed(double speed);

        void setTime(long zeroTime, double simulationSpeed);

        long getTime();
}

