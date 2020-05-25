package dse.grp20.common.dto;

import java.io.Serializable;

public class TrafficLightStatusDTO implements Serializable {

    private static final long serialVersionUID = -5354166941790297232L;
    private LightDTO light;
    private long from;
    private boolean valid;

    private TrafficLightDTO trafficLightDTO;

    public LightDTO getLight() {
        return this.light;
    }

    public void setLight(LightDTO light) {
        this.light = light;
    }

    public long getFrom() {
        return this.from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public TrafficLightDTO getTrafficLightDTO() {
        return trafficLightDTO;
    }

    public void setTrafficLightDTO(TrafficLightDTO trafficLightDTO) {
        this.trafficLightDTO = trafficLightDTO;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "TrafficLightStatusDTO{" +
                "light=" + light +
                ", from=" + from +
                ", valid=" + valid +
                ", trafficLightId=" + trafficLightDTO.getId() +
                '}';
    }
}
