package dse.grp20.statustracking.entities;

import java.io.Serializable;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = -3430637426277794475L;
    private String id;

    private String name;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
