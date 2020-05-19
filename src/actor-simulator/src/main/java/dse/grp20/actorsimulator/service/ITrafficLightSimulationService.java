package dse.grp20.actorsimulator.service;

import dse.grp20.common.dto.TrafficLightControlDTO;

public interface ITrafficLightSimulationService {

    void restartSimulation();

    void controlTrafficLight(TrafficLightControlDTO controlDTO);
}
