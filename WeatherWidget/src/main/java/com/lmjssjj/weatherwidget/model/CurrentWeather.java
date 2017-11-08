package com.lmjssjj.weatherwidget.model;

/**
 * Created by ya.wan on 2017/11/8.
 */

public class CurrentWeather {

    public String city;
    public String temperature;
    public String weatherData;
    public String mobileLink;

    public CurrentWeather(String city, String temperature, String weatherData, String mobileLink) {
        this.city = city;
        this.temperature = temperature;
        this.weatherData = weatherData;
        this.mobileLink = mobileLink;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(String weatherData) {
        this.weatherData = weatherData;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }
}
