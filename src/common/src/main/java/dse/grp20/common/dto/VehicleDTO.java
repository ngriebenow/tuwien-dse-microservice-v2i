package dse.grp20.common.dto;

import java.io.Serializable;

public class VehicleDTO implements Serializable {

    private String vin;

    private String modelType;

    private String oem;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }
}
