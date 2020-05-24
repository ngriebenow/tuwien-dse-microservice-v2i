package dse.grp20.common.dto;

import java.io.Serializable;
import java.util.Objects;

public class GeoDTO implements Serializable {

    public GeoDTO() {
    }

    public GeoDTO(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private Double latitude;

    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoDTO geoDTO = (GeoDTO) o;
        return Objects.equals(latitude, geoDTO.latitude) &&
                Objects.equals(longitude, geoDTO.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "GeoDTO{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
