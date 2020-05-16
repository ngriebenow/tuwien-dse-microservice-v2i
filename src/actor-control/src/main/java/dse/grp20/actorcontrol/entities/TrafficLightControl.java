package dse.grp20.actorcontrol.entities;

import dse.grp20.common.dto.LightDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

public class TrafficLightControl {

    @Id
    @MongoId
    private String id;
    private LightDTO lightDTO;
    private long from;
    private boolean valid;
    private TrafficLightDTO trafficLight;
    private long timestamp;

    public TrafficLightControl() {
        timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
