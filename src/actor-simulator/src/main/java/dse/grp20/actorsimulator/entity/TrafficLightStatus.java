package dse.grp20.actorsimulator.entity;

import java.io.Serializable;

public class TrafficLightStatus implements Serializable {

    private Light light;
    private long from;
    private boolean valid;
    private long trafficLightId;

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public long getTrafficLightId() {
        return trafficLightId;
    }

    public void setTrafficLightId(long trafficLightId) {
        this.trafficLightId = trafficLightId;
    }

    @Override
    public String toString() {
        return "TrafficLightStatus{" +
                "light=" + light +
                ", from=" + from +
                ", valid=" + valid +
                ", trafficLightId=" + trafficLightId +
                '}';
    }
}
