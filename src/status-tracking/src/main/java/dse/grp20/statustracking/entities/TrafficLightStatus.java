package dse.grp20.statustracking.entities;

import dse.grp20.common.dto.LightDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

@Document(collection = "TrafficLightStatus")
public class TrafficLightStatus implements Serializable {

    private static final long serialVersionUID = -7705199963504006788L;
    private LightDTO light;
    private long from;
    private boolean valid;

    private long trafficLightId;

    @Id
    @MongoId
    private String id;

    public TrafficLightStatus () {}

    public TrafficLightStatus (LightDTO light, long from, long trafficLight) {
        this.light = light;
        this.from = from;
        this.trafficLightId = trafficLight;
    }

    public TrafficLightStatus (TrafficLightStatusDTO dto) {
        this(dto.getLight(), dto.getFrom(), dto.getTrafficLightId());
    }

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

    public long getTrafficLightId() {
        return trafficLightId;
    }

    public void setTrafficLightId(long trafficLightId) {
        this.trafficLightId = trafficLightId;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Light: ").append(this.light).append(System.lineSeparator())
                .append("from: ").append(this.from).append(System.lineSeparator()).append("trafficlight: ")
                .append(this.trafficLight.getId());
        return super.toString();
    }
}
