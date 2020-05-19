package dse.grp20.actorsimulator.entity;

import dse.grp20.common.dto.LightDTO;

import java.io.Serializable;

public class TrafficLightControl implements Serializable {

    private Long trafficLightId;
    private Light light;
    private long from;

    public Long getTrafficLightId() {
        return trafficLightId;
    }

    public void setTrafficLightId(Long trafficLightId) {
        this.trafficLightId = trafficLightId;
    }

    public Light getLight() {
        return light;
    }

    public void setLightDTO(Light light) {
        this.light = light;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "TrafficLightControl{" +
                "trafficLightId=" + trafficLightId +
                ", light=" + light +
                ", from=" + from +
                '}';
    }
}
