package dse.grp20.common.dto;

import java.io.Serializable;

public class TrafficLightControlDTO implements Serializable {

    private Long trafficLightId;
    private LightDTO lightDTO;
    private long from;

    public TrafficLightControlDTO() {
    }

    public TrafficLightControlDTO(Long trafficLightId, LightDTO lightDTO, long from) {
        this.trafficLightId = trafficLightId;
        this.lightDTO = lightDTO;
        this.from = from;
    }

    public Long getTrafficLightId() {
        return trafficLightId;
    }

    public void setTrafficLightId(Long trafficLightId) {
        this.trafficLightId = trafficLightId;
    }

    public LightDTO getLightDTO() {
        return lightDTO;
    }

    public void setLightDTO(LightDTO lightDTO) {
        this.lightDTO = lightDTO;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }
}
