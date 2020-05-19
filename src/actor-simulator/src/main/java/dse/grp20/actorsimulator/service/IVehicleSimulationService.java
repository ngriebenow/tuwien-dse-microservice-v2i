package dse.grp20.actorsimulator.service;

import dse.grp20.common.dto.VehicleControlDTO;

public interface IVehicleSimulationService {

    void controlVehicle(VehicleControlDTO vehicleControlDTO);

    void restartSimulation();
}
