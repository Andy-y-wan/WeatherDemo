package com.lmjssjj.weatherwidget;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lmjssjj.weatherwidget.model.CityModel;
import com.lmjssjj.weatherwidget.model.WeatherModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author ya.wan
 * @date 2017/11/2
 */

public class WeatherRequest {

    public interface IWeatherCallBack{
        void onTransferSuccess(Weather type, String city, String temp, String weatherData, String mobileLink);
        void onTransferFailure();
    }

    private IWeatherCallBack mCallBack;

    public void setCallBack(IWeatherCallBack callBack) {
        this.mCallBack = callBack;
    }

    private static RequestQueue mRequestQueue;

    public WeatherRequest(RequestQueue rq){
        mRequestQueue=rq;
    }

    public void getWeatherData(final String key, final String city,final boolean location){
        if(mCallBack == null){
            return;
        }
        if(TextUtils.isEmpty(city)|| TextUtils.isEmpty(key)){
            mCallBack.onTransferFailure();
            return;
        }
        String s = WeatherConfig.WEATHER_URL;
        String url = String.format(s, key,WeatherConfig.API_KEY, Locale.getDefault().getLanguage());
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String result) {
                if(!TextUtils.isEmpty(result)){
                    Gson gson=new Gson();
                    List<WeatherModel> weathers=new ArrayList<WeatherModel>();
                    try {
                        weathers = gson.fromJson(result, new TypeToken<List<WeatherModel>>() {
                        }.getType());
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    if(weathers != null && weathers.size() > 0){
//						StringBuffer sb=new StringBuffer();
                        Weather type = null;
                        WeatherModel model = weathers.get(0);
//						sb.append(model.getTemperature().getMetric().getValue());
//						sb.append("° ");
//						sb.append(model.getWeatherText());
                        /**rounding off temperature */
                        String tmp = model.getTemperature().getMetric().getValue();
                        if(tmp!=null){
                            Log.v("lmjssjj", "tmp:"+tmp);
                            tmp = String.valueOf(Math.round(Float.parseFloat(tmp)));
                            Log.v("lmjssjj", "tmp:"+tmp);
                        }
                        /**rounding off temperature */
                        String temp=/*model.getTemperature().getMetric().getValue()*/tmp+"°";
                        String weather=model.getWeatherText();
                        type = Weather.getWeather(model.getWeatherIcon(), model.getIsDayTime());


                        mCallBack.onTransferSuccess(type, city, temp,weather, model.getMobileLink());
                    }else{
                        mCallBack.onTransferFailure();
                    }
                }else{
                    mCallBack.onTransferFailure();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                if(mCallBack != null){
                    mCallBack.onTransferFailure();
                }
            }
        });
        mRequestQueue.add(request);
    }
    /**
     *
     * @Title: getCityAccordingLL
     * @Description: TODO(通过经纬度查询城市数据)
     * @author: lmjssjj
     * @date:  2017年6月26日 下午1:47:50
     * @param: @param longitude
     * @param: @param latitude
     * @return: void
     * @throws
     */
    public void getCityAccordingLL(double longitude,double latitude){

        String s = WeatherConfig.LL_URL;
        String url = String.format(s, String.valueOf(longitude)+","+String.valueOf(latitude),WeatherConfig.API_KEY,Locale.getDefault().getLanguage());
        if(Locale.getDefault().getLanguage().equals("my"))
        {
            url = String.format(s, String.valueOf(longitude)+","+String.valueOf(latitude),WeatherConfig.API_KEY,"en");
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public void onResponse(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            Gson gson = new Gson();
                            CityModel city = null;
                            try {
                                city = gson.fromJson(result, new TypeToken<CityModel>() {
                                }.getType());
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                            if (city!=null ) {
                                Log.v("lmjssjj", "WeatherRequest city key:"+city.getKey());
                                Log.v("lmjssjj", "WeatherRequest city LocalizedName:"+city.getLocalizedName());
                                getWeatherData(city.getKey(),city.getLocalizedName(),true);
                            } else {

                            }
                        } else {

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });
        mRequestQueue.add(stringRequest);
    }

}

