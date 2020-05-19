/*package dse.grp20.actorsimulator.localtest;

import dse.grp20.actorsimulator.entity.Geo;
import dse.grp20.actorsimulator.entity.VehicleStatus;
import dse.grp20.actorsimulator.service.impl.VehicleSimulationService;
import dse.grp20.actorsimulator.service.impl.TimeService;
import edu.princeton.cs.introcs.StdDraw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class GuiTester {

    private static TimeService timeService = new TimeService();
    private static Logger LOGGER = LoggerFactory.getLogger(GuiTester.class);
    private static VehicleSimulationService vehicleSimulationService = new VehicleSimulationService();

    public static void main(String[] args) throws InterruptedException{

        StdDraw.setCanvasSize(512,512);
        StdDraw.setXscale(15.605,15.615);
        StdDraw.setYscale(48.125,48.135);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.008);

        vehicleSimulationService.runSimulation();

        int counter = 0;

        while (true) {
            Thread.sleep(timeService.getRefreshRateInMs());

            if (vehicleSimulationService.getVehicleStati().size() > 0) {
                VehicleStatus s = vehicleSimulationService.getVehicleStati().get(0);
                StdDraw.point(s.getLocation().getLongitude(), s.getLocation().getLatitude());

                LOGGER.info(s.toString());


                // simulate some controls from ActorControlService
                counter++;
                if (counter == 10) {
                    VehicleStatus vs = new VehicleStatus();
                    vs.setSpeed(60);
                    vs.setLocation(new Geo(-1.,-1.));
                    vehicleSimulationService.setControlStatus("WVWZZZ1JZ3W386752",vs);
                }
                if (counter == 20) {
                    VehicleStatus vs = new VehicleStatus();
                    vs.setSpeed(15);
                    vs.setLocation(new Geo(-1.,-1.));
                    vehicleSimulationService.setControlStatus("WVWZZZ1JZ3W386752",vs);
                }
                if (counter == 30) {
                    VehicleStatus vs = new VehicleStatus();
                    vs.setSpeed(35);
                    vs.setLocation(new Geo(-1.,-1.));
                    vehicleSimulationService.setControlStatus("WVWZZZ1JZ3W386752",vs);
                }
            }
        }
    }
}
*/