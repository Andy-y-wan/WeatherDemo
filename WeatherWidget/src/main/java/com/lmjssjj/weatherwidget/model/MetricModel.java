package com.lmjssjj.weatherwidget.model;

/**
 * Created by ya.wan on 2017/11/2.
 */

public class MetricModel {

    private String Value;
    private String Unit;
    private String UnitType;
    public String getValue() {
        return Value;
    }
    public void setValue(String value) {
        Value = value;
    }
    public String getUnit() {
        return Unit;
    }
    public void setUnit(String unit) {
        Unit = unit;
    }
    public String getUnitType() {
        return UnitType;
    }
    public void setUnitType(String unitType) {
        UnitType = unitType;
    }
}
