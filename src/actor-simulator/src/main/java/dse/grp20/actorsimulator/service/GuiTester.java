package dse.grp20.actorsimulator.service;

import dse.grp20.actorsimulator.entity.VehicleStatus;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class GuiTester {

    private static Vehicle1Simulator simulator = new Vehicle1Simulator();

    public static void main(String[] args) throws InterruptedException{
        Thread t = new Thread(() -> {
            try {
                simulator.simulate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.start();

        StdDraw.setCanvasSize(512,512);
        StdDraw.setXscale(15.605,15.615);
        StdDraw.setYscale(48.125,48.135);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.008);

        StdDraw.point(15.6,48.14);

        while (true) {

            //StdDraw.clear();
            VehicleStatus s = simulator.getCurrentStatus();
            StdDraw.point(s.getLocation().getLongitude(), s.getLocation().getLatitude());




            System.out.println(simulator.getCurrentStatus());
            Thread.sleep(1000);
        }
    }
}
