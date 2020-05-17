package dse.grp20.actorcontrol.entities;

import dse.grp20.common.dto.LightDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

public class TrafficLightControl {

    @Id
    @MongoId
    private Long trafficLightId;
    private LightDTO lightDTO;
    private long from;
    private long timestamp;

    public TrafficLightControl() {
        timestamp = System.currentTimeMillis();
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
