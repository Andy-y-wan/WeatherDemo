package com.lmjssjj.weatherwidget;

/**
 * Created by ya.wan on 2017/11/2.
 */

public class WeatherConfig {
    public static final String DEV_HOST = "http://apidev.accuweather.com";//用于开发环境
    public static final String PRODUCTION_HOST = "http://api.accuweather.com";//用于正式环境
    public static final String CITY_API_PATH = "/locations/v1/cities/autocomplete.json?q=%s&apikey=%s";
    public static final String WEATHER_API_PATH = "/currentconditions/v1/%s.json?apikey=%s&language=%s";

    /**get city with longitude and latitude path*/
    //public static final String CITY_API_WITH_LA_PATH = "/locations/v1/cities/geoposition/search.json?q=%s&apikey=%s&language=%s";
    public static final String CITY_API_WITH_LA_PATH = "/locations/v1/cities/geoposition/search.json?q=%s&apikey=%s";

    public static String CITY_URL;
    public static String WEATHER_URL;
    public static String LL_URL;
    static{
        CITY_URL = PRODUCTION_HOST + CITY_API_PATH;
        WEATHER_URL = PRODUCTION_HOST + WEATHER_API_PATH;
        LL_URL = PRODUCTION_HOST+CITY_API_WITH_LA_PATH;//acording longitude and latitude get city
    }

    public static final String API_KEY="ef73e73a9de44f6d9242f96404cd55e0";

    public static final String CITY = "city";
    public static final String KEY = "key";
    public static final String LOCATION = "location";
    public static final String TEMPERATURE = "temperature";//温度
    public static final String WEATHER_TYPE = "weaterhType";//天气类型
    public static final String LINK = "link";//天气url
    public static final String WEATHER_PACKAGE="com.talpa.weather";
}
