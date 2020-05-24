package dse.grp20.statustracking.entities;

import java.io.Serializable;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = -3430637426277794475L;
    private String vin;

    private String modelType;

    private String oem;

    public String getVin() {
        return this.vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getModelType() {
        return this.modelType;
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
