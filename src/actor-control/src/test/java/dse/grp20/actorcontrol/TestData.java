package dse.grp20.actorcontrol;

import dse.grp20.common.dto.*;

import java.util.Calendar;


public class TestData {

    //public Calendar startTime = Calendar.getInstance();
    public long startTime;

    public VehicleStatusDTO vehicle1Status;
    public VehicleStatusDTO vehicle2Status;
    public VehicleStatusDTO vehicle3Status;

    public double vehicleSpeedAfterTrafficLight1;
    public double vehicleSpeedAfterTrafficLight2;
    public double vehicleSpeedAfterTrafficLight3;

    public TrafficLightDTO trafficLight1;
    public TrafficLightDTO trafficLight2;
    public TrafficLightDTO trafficLight3;

    public TrafficLightStatusDTO trafficLight1Status1;
    public TrafficLightStatusDTO trafficLight1Status2;
    public TrafficLightStatusDTO trafficLight1Status3;
    public TrafficLightStatusDTO trafficLight1Status4;
    public TrafficLightStatusDTO trafficLight1Status5;

    public TrafficLightStatusDTO trafficLight2Status1;
    public TrafficLightStatusDTO trafficLight2Status2;
    public TrafficLightStatusDTO trafficLight2Status3;
    public TrafficLightStatusDTO trafficLight2Status4;
    public TrafficLightStatusDTO trafficLight2Status5;

    public TrafficLightStatusDTO trafficLight3Status1;
    public TrafficLightStatusDTO trafficLight3Status2;
    public TrafficLightStatusDTO trafficLight3Status3;
    public TrafficLightStatusDTO trafficLight3Status4;
    public TrafficLightStatusDTO trafficLight3Status5;


    public static final int START_HOUR = 13;
    public static final int START_MINUTE = 0;

    public static final GeoDTO VEHICLE1_INITIAL_POSITION = new GeoDTO(48.129728, 15.612400);
    public static final double VEHICLE1_INITIAL_SPEED = 19.44;

    public static final double VEHICLE1_SPEED_AFTER_TRAFFICLIGHT1 = 16.66;
    public static final double VEHICLE1_SPEED_AFTER_TRAFFICLIGHT2 = 11.66;
    public static final double VEHICLE1_SPEED_AFTER_TRAFFICLIGHT3 = 13.9;

    public static long TRAFFICLIGHT1_ID = 1L;
    public static long TRAFFICLIGHT2_ID = 2L;
    public static long TRAFFICLIGHT3_ID = 3L;
    public static GeoDTO TRAFFICLIGHT1_GEO = new GeoDTO(48.139175, 15.613267);
    public static GeoDTO TRAFFICLIGHT2_GEO = new GeoDTO(48.145468, 15.613710);
    public static GeoDTO TRAFFICLIGHT3_GEO = new GeoDTO(48.157159, 15.614965);

    public static LightDTO TRAFFICLIGHT1_STATUS1_LIGHT = LightDTO.GREEN;
    public static LightDTO TRAFFICLIGHT1_STATUS2_LIGHT = LightDTO.RED;
    public static LightDTO TRAFFICLIGHT1_STATUS3_LIGHT = LightDTO.GREEN;
    public static LightDTO TRAFFICLIGHT1_STATUS4_LIGHT = LightDTO.RED;
    public static LightDTO TRAFFICLIGHT1_STATUS5_LIGHT = LightDTO.GREEN;

    public static LightDTO TRAFFICLIGHT2_STATUS1_LIGHT = LightDTO.GREEN;
    public static LightDTO TRAFFICLIGHT2_STATUS2_LIGHT = LightDTO.RED;
    public static LightDTO TRAFFICLIGHT2_STATUS3_LIGHT = LightDTO.GREEN;
    public static LightDTO TRAFFICLIGHT2_STATUS4_LIGHT = LightDTO.RED;
    public static LightDTO TRAFFICLIGHT2_STATUS5_LIGHT = LightDTO.GREEN;

    public static LightDTO TRAFFICLIGHT3_STATUS1_LIGHT = LightDTO.GREEN;
    public static LightDTO TRAFFICLIGHT3_STATUS2_LIGHT = LightDTO.RED;
    public static LightDTO TRAFFICLIGHT3_STATUS3_LIGHT = LightDTO.GREEN;
    public static LightDTO TRAFFICLIGHT3_STATUS4_LIGHT = LightDTO.RED;
    public static LightDTO TRAFFICLIGHT3_STATUS5_LIGHT = LightDTO.GREEN;

    // delta seconds beginning with START_TIME
    public static long TRAFFICLIGHT1_STATUS1_FROM = 60 * 1000;
    public static long TRAFFICLIGHT1_STATUS2_FROM = 80 * 1000;
    public static long TRAFFICLIGHT1_STATUS3_FROM = 100 * 1000;
    public static long TRAFFICLIGHT1_STATUS4_FROM = 120 * 1000;
    public static long TRAFFICLIGHT1_STATUS5_FROM = 140 * 1000;

    public static long TRAFFICLIGHT2_STATUS1_FROM = 120 * 1000;
    public static long TRAFFICLIGHT2_STATUS2_FROM = 140 * 1000;
    public static long TRAFFICLIGHT2_STATUS3_FROM = 180 * 1000;
    public static long TRAFFICLIGHT2_STATUS4_FROM = 200 * 1000;
    public static long TRAFFICLIGHT2_STATUS5_FROM = 240 * 1000;

    public static long TRAFFICLIGHT3_STATUS1_FROM = 150 * 1000;
    public static long TRAFFICLIGHT3_STATUS2_FROM = 170 * 1000;
    public static long TRAFFICLIGHT3_STATUS3_FROM = 190 * 1000;
    public static long TRAFFICLIGHT3_STATUS4_FROM = 210 * 1000;
    public static long TRAFFICLIGHT3_STATUS5_FROM = 230 * 1000;

    public TestData(long time) {
        startTime = time;
        setup();
    }

    private void setup() {
        vehicle1Status = new VehicleStatusDTO();
        vehicle1Status.setVin("WVWZZZ1JZ3W386752");
        vehicle1Status.setLocation(VEHICLE1_INITIAL_POSITION);
        vehicle1Status.setSpeed(VEHICLE1_INITIAL_SPEED);

        vehicle2Status = new VehicleStatusDTO();
        vehicle2Status.setVin("WVWZZZ1JZ3W386752");
        vehicle2Status.setLocation(TRAFFICLIGHT1_GEO);
        vehicle2Status.setSpeed(VEHICLE1_SPEED_AFTER_TRAFFICLIGHT1);

        vehicle3Status = new VehicleStatusDTO();
        vehicle3Status.setVin("WVWZZZ1JZ3W386752");
        vehicle3Status.setLocation(TRAFFICLIGHT2_GEO);
        vehicle3Status.setSpeed(VEHICLE1_SPEED_AFTER_TRAFFICLIGHT2);

        vehicleSpeedAfterTrafficLight1 = VEHICLE1_SPEED_AFTER_TRAFFICLIGHT1;
        vehicleSpeedAfterTrafficLight2 = VEHICLE1_SPEED_AFTER_TRAFFICLIGHT2;
        vehicleSpeedAfterTrafficLight3 = VEHICLE1_SPEED_AFTER_TRAFFICLIGHT3;

        trafficLight1 = new TrafficLightDTO();
        trafficLight2 = new TrafficLightDTO();
        trafficLight3 = new TrafficLightDTO();

        trafficLight1.setId(TRAFFICLIGHT1_ID);
        trafficLight2.setId(TRAFFICLIGHT2_ID);
        trafficLight3.setId(TRAFFICLIGHT3_ID);

        trafficLight1.setLocation(TRAFFICLIGHT1_GEO);
        trafficLight2.setLocation(TRAFFICLIGHT2_GEO);
        trafficLight3.setLocation(TRAFFICLIGHT3_GEO);

        trafficLight1Status1 = new TrafficLightStatusDTO();
        trafficLight1Status2 = new TrafficLightStatusDTO();
        trafficLight1Status3 = new TrafficLightStatusDTO();
        trafficLight1Status4 = new TrafficLightStatusDTO();
        trafficLight1Status5 = new TrafficLightStatusDTO();

        trafficLight2Status1 = new TrafficLightStatusDTO();
        trafficLight2Status2 = new TrafficLightStatusDTO();
        trafficLight2Status3 = new TrafficLightStatusDTO();
        trafficLight2Status4 = new TrafficLightStatusDTO();
        trafficLight2Status5 = new TrafficLightStatusDTO();

        trafficLight3Status1 = new TrafficLightStatusDTO();
        trafficLight3Status2 = new TrafficLightStatusDTO();
        trafficLight3Status3 = new TrafficLightStatusDTO();
        trafficLight3Status4 = new TrafficLightStatusDTO();
        trafficLight3Status5 = new TrafficLightStatusDTO();

        /* TRAFFICLIGHT 1 */
        trafficLight1Status1.setTrafficLightDTO(trafficLight1);
        trafficLight1Status1.setLight(TRAFFICLIGHT1_STATUS1_LIGHT);
        trafficLight1Status1.setFrom(startTime + TRAFFICLIGHT1_STATUS1_FROM);

        trafficLight1Status2.setTrafficLightDTO(trafficLight1);
        trafficLight1Status2.setLight(TRAFFICLIGHT1_STATUS2_LIGHT);
        trafficLight1Status2.setFrom(startTime + TRAFFICLIGHT1_STATUS2_FROM);

        trafficLight1Status3.setTrafficLightDTO(trafficLight1);
        trafficLight1Status3.setLight(TRAFFICLIGHT1_STATUS3_LIGHT);
        trafficLight1Status3.setFrom(startTime + TRAFFICLIGHT1_STATUS3_FROM);

        trafficLight1Status4.setTrafficLightDTO(trafficLight1);
        trafficLight1Status4.setLight(TRAFFICLIGHT1_STATUS4_LIGHT);
        trafficLight1Status4.setFrom(startTime + TRAFFICLIGHT1_STATUS4_FROM);

        trafficLight1Status5.setTrafficLightDTO(trafficLight1);
        trafficLight1Status5.setLight(TRAFFICLIGHT1_STATUS5_LIGHT);
        trafficLight1Status5.setFrom(startTime + TRAFFICLIGHT1_STATUS5_FROM);

        /* TRAFFICLIGHT 2 */
        trafficLight2Status1.setTrafficLightDTO(trafficLight2);
        trafficLight2Status1.setLight(TRAFFICLIGHT2_STATUS1_LIGHT);
        trafficLight2Status1.setFrom(startTime + TRAFFICLIGHT2_STATUS1_FROM);

        trafficLight2Status2.setTrafficLightDTO(trafficLight2);
        trafficLight2Status2.setLight(TRAFFICLIGHT2_STATUS2_LIGHT);
        trafficLight2Status2.setFrom(startTime + TRAFFICLIGHT2_STATUS2_FROM);

        trafficLight2Status3.setTrafficLightDTO(trafficLight2);
        trafficLight2Status3.setLight(TRAFFICLIGHT2_STATUS3_LIGHT);
        trafficLight2Status3.setFrom(startTime + TRAFFICLIGHT2_STATUS3_FROM);

        trafficLight2Status4.setTrafficLightDTO(trafficLight2);
        trafficLight2Status4.setLight(TRAFFICLIGHT2_STATUS4_LIGHT);
        trafficLight2Status4.setFrom(startTime + TRAFFICLIGHT2_STATUS4_FROM);

        trafficLight2Status5.setTrafficLightDTO(trafficLight2);
        trafficLight2Status5.setLight(TRAFFICLIGHT2_STATUS5_LIGHT);
        trafficLight2Status5.setFrom(startTime + TRAFFICLIGHT2_STATUS5_FROM);

        /* TRAFFICLIGHT 3 */
        trafficLight3Status1.setTrafficLightDTO(trafficLight3);
        trafficLight3Status1.setLight(TRAFFICLIGHT3_STATUS1_LIGHT);
        trafficLight3Status1.setFrom(startTime + TRAFFICLIGHT3_STATUS1_FROM);

        trafficLight3Status2.setTrafficLightDTO(trafficLight3);
        trafficLight3Status2.setLight(TRAFFICLIGHT3_STATUS2_LIGHT);
        trafficLight3Status2.setFrom(startTime + TRAFFICLIGHT3_STATUS2_FROM);

        trafficLight3Status3.setTrafficLightDTO(trafficLight3);
        trafficLight3Status3.setLight(TRAFFICLIGHT3_STATUS3_LIGHT);
        trafficLight3Status3.setFrom(startTime + TRAFFICLIGHT3_STATUS3_FROM);

        trafficLight3Status4.setTrafficLightDTO(trafficLight3);
        trafficLight3Status4.setLight(TRAFFICLIGHT3_STATUS4_LIGHT);
        trafficLight3Status4.setFrom(startTime + TRAFFICLIGHT3_STATUS4_FROM);

        trafficLight3Status5.setTrafficLightDTO(trafficLight3);
        trafficLight3Status5.setLight(TRAFFICLIGHT3_STATUS5_LIGHT);
        trafficLight3Status5.setFrom(startTime + TRAFFICLIGHT3_STATUS5_FROM);
    };
}
