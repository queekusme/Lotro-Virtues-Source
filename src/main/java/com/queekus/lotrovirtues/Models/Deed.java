package com.queekus.lotrovirtues.Models;

import javafx.beans.property.*;

public class Deed {
    private final SimpleStringProperty virtueName = new SimpleStringProperty("");
    private final SimpleStringProperty increase = new SimpleStringProperty("");
    private final SimpleIntegerProperty level = new SimpleIntegerProperty();
    private final SimpleStringProperty virtueType = new SimpleStringProperty("");
    private final SimpleStringProperty region = new SimpleStringProperty("");
    private final SimpleStringProperty deedName = new SimpleStringProperty("");

    private boolean retired;

    public Deed() {
        this("","",0,"","","",false);
    }

    public Deed(String virtueName, String increase, int level, String virtueType, String region, String deedName, boolean retired) {
        this.setVirtueName(virtueName);
        this.setIncrease(increase);
        this.setLevel(level);
        this.setVirtueType(virtueType);
        this.setRegion(region);
        this.setDeedName(deedName);
        this.setRetired(retired);
    }

    public void setVirtueName(String virtueName) {
        this.virtueName.set(virtueName);
    }
    public String getVirtueName() {
        return virtueName.get();
    }
    public void setIncrease(String increase) {
        this.increase.set(increase);
    }
    public String getIncrease() {
        return increase.get();
    }
    public void setLevel(int level) {
        this.level.set(level);
    }
    public int getLevel() {
        return level.get();
    }
    public void setVirtueType(String virtueType) {
        this.virtueType.set(virtueType);
    }
    public String getVirtueType() {
        return virtueType.get();
    }
    public void setRegion(String region) {
        this.region.set(region);
    }
    public String getRegion() {
        return region.get();
    }
    public void setDeedName(String deedName) {
        this.deedName.set(deedName);
    }
    public String getDeedName() {
        return deedName.get();
    }

    public boolean isRetired() { return retired; }
    public void setRetired(boolean retired) { this.retired = retired;  }

    public SimpleStringProperty virtueNameProperty() {
        return virtueName;
    }
    public SimpleStringProperty increaseProperty() {
        return increase;
    }
    public SimpleIntegerProperty levelProperty() {
        return level;
    }
    public SimpleStringProperty virtueTypeProperty() {
        return virtueType;
    }
    public SimpleStringProperty regionProperty() {
        return region;
    }
    public SimpleStringProperty deedNameProperty() {
        return deedName;
    }

}
