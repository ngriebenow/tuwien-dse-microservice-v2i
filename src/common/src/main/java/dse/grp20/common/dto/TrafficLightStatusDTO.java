package dse.grp20.common.dto;

import java.io.Serializable;

public class TrafficLightStatusDTO implements Serializable {

    private static final long serialVersionUID = -5354166941790297232L;
    private LightDTO lightDTO;
    private long from;
    private boolean valid;

    private TrafficLightDTO trafficLight;

    public LightDTO getLightDTO() {
        return this.lightDTO;
    }

    public void setLightDTO(LightDTO lightDTO) {
        this.lightDTO = lightDTO;
    }

    public long getFrom() {
        return this.from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public TrafficLightDTO getTrafficLight() {
        return this.trafficLight;
    }

    public void setTrafficLight(TrafficLightDTO trafficLight) {
        this.trafficLight = trafficLight;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
