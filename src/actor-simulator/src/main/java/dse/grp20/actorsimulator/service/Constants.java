package dse.grp20.actorsimulator.service;

import dse.grp20.actorsimulator.entity.Geo;
import dse.grp20.actorsimulator.entity.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final Vehicle VEHICLE1 = new Vehicle("WVWZZZ1JZ3W386752","Golf IV","Volkswagen");

    public static final Geo VEHICLE1_INITIAL_POSITION = new Geo(48.129728, 15.612400);
    public static final Geo VEHICLE1_ENTRY_A_POSITION = new Geo(48.129901, 15.612411);

    public static final Geo TEST1 = new Geo(48.131145, 15.613510);
    public static final Geo TEST2 = new Geo(48.131851, 15.610420);
    public static final Geo TEST3 = new Geo(48.133229, 15.612509);
    public static final Geo TEST4 = new Geo(48.133155, 15.615050);

    public static final Geo TRAFFICLIGHT_A_POSITION = new Geo(48.139175, 15.613267);
    public static final Geo TRAFFICLIGHT_B_POSITION = new Geo(48.145468, 15.613710);
    public static final Geo TRAFFICLIGHT_C_POSITION = new Geo(48.157159, 15.614965);
    public static final Geo VEHICLE1_TARGET_POSITION = new Geo(48.169729, 15.615382);

    public static final Geo VEHICLE1_NCE_POSITION = new Geo(48.152659, 15.614569);

    public static List<Geo> VEHICLE1_ROUTE_TODO = new ArrayList<>(Arrays.asList(TRAFFICLIGHT_A_POSITION,
            TRAFFICLIGHT_B_POSITION,
            VEHICLE1_NCE_POSITION,
            TRAFFICLIGHT_C_POSITION,
            VEHICLE1_TARGET_POSITION));

    public static List<Geo> VEHICLE1_ROUTE = new ArrayList<>(Arrays.asList(
            TEST1,
            TEST2,
            TEST3,
            TEST4));
}
