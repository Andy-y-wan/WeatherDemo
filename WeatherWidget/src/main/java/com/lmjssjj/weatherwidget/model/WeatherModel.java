package com.lmjssjj.weatherwidget.model;

/**
 * Created by ya.wan on 2017/11/2.
 */

public class WeatherModel {

    private String WeatherText;
    private int WeatherIcon;
    private boolean IsDayTime;
    private String MobileLink;
    private String Link;
    private TemperatureModel Temperature;

    public String getWeatherText() {
        return WeatherText;
    }

    public void setWeatherText(String weatherText) {
        WeatherText = weatherText;
    }

    public int getWeatherIcon() {
        return WeatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        WeatherIcon = weatherIcon;
    }

    public boolean getIsDayTime() {
        return IsDayTime;
    }

    public void setIsDayTime(boolean isDayTime) {
        IsDayTime = isDayTime;
    }

    public String getMobileLink() {
        return MobileLink;
    }

    public void setMobileLink(String mobileLink) {
        MobileLink = mobileLink;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public TemperatureModel getTemperature() {
        return Temperature;
    }

    public void setTemperature(TemperatureModel temperature) {
        Temperature = temperature;
    }

}

