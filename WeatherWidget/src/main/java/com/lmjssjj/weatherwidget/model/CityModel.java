package com.lmjssjj.weatherwidget.model;

/**
 * Created by ya.wan on 2017/11/2.
 */

public class CityModel {
    private String Version;
    private String Key;
    private String LocalizedName;
    private CountryModel Country;
    private AreaModel AdministrativeArea;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getLocalizedName() {
        return LocalizedName;
    }

    public void setLocalizedName(String localizedName) {
        LocalizedName = localizedName;
    }

    public CountryModel getCountry() {
        return Country;
    }

    public void setCountry(CountryModel country) {
        Country = country;
    }

    public AreaModel getAdministrativeArea() {
        return AdministrativeArea;
    }

    public void setAdministrativeArea(AreaModel administrativeArea) {
        AdministrativeArea = administrativeArea;
    }

}
