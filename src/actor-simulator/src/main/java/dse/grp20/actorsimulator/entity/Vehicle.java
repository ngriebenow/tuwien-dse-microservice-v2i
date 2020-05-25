package dse.grp20.actorsimulator.entity;

import java.io.Serializable;

public class Vehicle implements Serializable {


    private String vin;


    public Vehicle(String vin, String modelType, String oem) {
        this.vin = vin;
        this.modelType = modelType;
        this.oem = oem;
    }

    public Vehicle() {
    }

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
