package com.lmjssjj.weatherwidget;

import android.util.ArrayMap;
import android.util.Log;

/**
 * Created by ya.wan on 2017/11/2.
 */

public enum Weather {

    DEFAULT(0), SUNNY_NIGHT(R.string.weather_sunny), SUNNY_DAY(R.string.weather_sunny),
    RAIN_DAY(R.string.weather_rain), RAIN_NIGHT(R.string.weather_rain),
    CLOUDY_DAY(R.string.weather_cloudy), CLOUDY_NIGHT(R.string.weather_cloudy),
    OVERCAST_DAY(R.string.weather_overcast), OVERCAST_NIGHT(R.string.weather_overcast),
    FOG_DAY(R.string.weather_fog), FOG_NIGHT(R.string.weather_fog),
    SNOW_DAY(R.string.weather_snow), SNOW_NIGHT(R.string.weather_snow),
    ICE_DAY(R.string.weather_ice), ICE_NIGHT(R.string.weather_ice),
    TSTORMS_DAY(R.string.weather_tstorms), TSTORMS_NIGHT(R.string.weather_tstorms);

    private int descriptionResId;

    private Weather(int descriptionResId){
        this.descriptionResId = descriptionResId;
    }

    public int getDescriptionResId(){
        return descriptionResId;
    }

    static ArrayMap<Integer, Weather> weatherMap = new ArrayMap<>();
    static int[] sunny = {1, 2, 3, 4, 5, 30, 32, 33, 34};//晴天
    static int[] rain = {12, 13, 14, 18, 39, 40};//雨天
    static int[] cloudy = {6, 7, 35, 36, 37, 38};//多云
    static int[] dreary = {8, 31};//阴天
    static int[] fog = {11};//雾
    static int[] snow = {19, 20, 21, 22, 23, 25, 26, 29, 43, 44};//雪
    static int[] ice = {24};//冰
    static int[] tStorms = {15, 16, 17, 41, 42};//雷暴
    static int isNight = -1;
    static int isDAY = 1;
    static{
        for(int icon: sunny){
            weatherMap.put(icon * isDAY, SUNNY_DAY);
            weatherMap.put(icon * isNight, SUNNY_NIGHT);
        }
        for(int icon: rain){
            weatherMap.put(icon * isDAY, RAIN_DAY);
            weatherMap.put(icon * isNight, RAIN_NIGHT);
        }
        for(int icon: cloudy){
            weatherMap.put(icon * isDAY, CLOUDY_DAY);
            weatherMap.put(icon * isNight, CLOUDY_NIGHT);
        }
        for(int icon: dreary){
            weatherMap.put(icon * isDAY, OVERCAST_DAY);
            weatherMap.put(icon * isNight, OVERCAST_NIGHT);
        }
        for(int icon: fog){
            weatherMap.put(icon * isDAY, FOG_DAY);
            weatherMap.put(icon * isNight, FOG_NIGHT);
        }
        for(int icon: snow){
            weatherMap.put(icon * isDAY, SNOW_DAY);
            weatherMap.put(icon * isNight, SNOW_NIGHT);
        }
        for(int icon: ice){
            weatherMap.put(icon * isDAY, ICE_DAY);
            weatherMap.put(icon * isNight, ICE_NIGHT);
        }
        for(int icon: tStorms){
            weatherMap.put(icon * isDAY, TSTORMS_DAY);
            weatherMap.put(icon * isNight, TSTORMS_NIGHT);
        }
    }


    public static Weather getWeather(int icon, boolean isDayTime) {
        Log.e("getWeather", icon + "::" + isDayTime);
        Weather type=weatherMap.get(icon * (isDayTime ? isDAY : isNight));
        if(type!=null){
            return type;
        } else {
            return Weather.DEFAULT;
        }
    }

}
