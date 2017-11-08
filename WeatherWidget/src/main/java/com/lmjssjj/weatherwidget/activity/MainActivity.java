package com.lmjssjj.weatherwidget.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lmjssjj.weatherwidget.R;
import com.lmjssjj.weatherwidget.Weather;
import com.lmjssjj.weatherwidget.WeatherAppState;
import com.lmjssjj.weatherwidget.WeatherConfig;
import com.lmjssjj.weatherwidget.WeatherRequest;
import com.lmjssjj.weatherwidget.common.GLRenderer;
import com.lmjssjj.weatherwidget.common.GLView;
import com.lmjssjj.weatherwidget.model.CurrentWeather;
import com.lmjssjj.weatherwidget.weather.cloudydayweather.CloudyDayWeatherView;
import com.lmjssjj.weatherwidget.weather.defaultweather.DefaultWeatherView;
import com.lmjssjj.weatherwidget.weather.fogdayweather.FogDayWeatherView;
import com.lmjssjj.weatherwidget.weather.overcastdayweather.OverDayWeatherView;
import com.lmjssjj.weatherwidget.weather.rainydayweather.RainyDayWeatherView;
import com.lmjssjj.weatherwidget.weather.snowdayweather.SnowDayWeatherView;
import com.lmjssjj.weatherwidget.weather.sunnydayweather.SunyDayWeatherView;

import static com.lmjssjj.weatherwidget.activity.SelectCityActivity.REQUEST_PERMISSION_LOCATION;

public class MainActivity extends AppCompatActivity implements WeatherRequest.IWeatherCallBack {

    private static final String TAG = "MainActivity";

    private FrameLayout mContent;
    private TextView textView;
    private static int i = 0;

    private LocationManager mLocationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        mContent = findViewById(R.id.content);
        textView = findViewById(R.id.textView);
        mContent.removeAllViews();
        mContent.addView(getWeatherView(null, "","","",""));
        location();
        WeatherAppState.getInstance().getRequestApi().setCallBack(this);
    }

    public void change(View v) {
        /*mContent.removeAllViews();
        mContent.addView(getWeatherView());*/
        Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String key = data.getStringExtra(WeatherConfig.KEY);
            String city = data.getStringExtra(WeatherConfig.CITY);
            boolean location = data.getBooleanExtra(WeatherConfig.LOCATION, false);
            WeatherAppState.getInstance().getRequestApi().getWeatherData(key, city, location);
        }
    }

    @Override
    public void onTransferSuccess(Weather type, String city, String temp, String weatherData, String mobileLink) {
        textView.setText(city + temp + weatherData);
        mContent.removeAllViews();
        mContent.addView(getWeatherView(type, city, temp, weatherData, mobileLink));
    }

    @Override
    public void onTransferFailure() {
        mContent.removeAllViews();
        mContent.addView(new DefaultWeatherView(this));
    }

    public View getWeatherView(Weather weather, String city, String temp, String weatherData, String mobileLink) {
        View v = null;
        if (weather == null) {
            v = new DefaultWeatherView(this);
            return v;
        }
        Log.v("lmjssjj", i + "---");
        switch (weather) {

            case DEFAULT:
                v = new DefaultWeatherView(this);
                break;
            case SUNNY_NIGHT:
                v = new SunyDayWeatherView(this);
                break;
            case SUNNY_DAY:
                v = new SunyDayWeatherView(this);
                break;
            case RAIN_DAY:
                v = new RainyDayWeatherView(this);
                break;
            case RAIN_NIGHT:
                v = new RainyDayWeatherView(this);
                break;
            case CLOUDY_NIGHT:
                v = new CloudyDayWeatherView(this);
                break;
            case CLOUDY_DAY:
                v = new CloudyDayWeatherView(this);
                break;
            case FOG_DAY:
                v = new FogDayWeatherView(this);
                break;
            case FOG_NIGHT:
                v = new FogDayWeatherView(this);
                break;
            case SNOW_DAY:
                v = new SnowDayWeatherView(this);
                break;
            case SNOW_NIGHT:
                v = new SnowDayWeatherView(this);
                break;
            /*case TSTORMS_DAY:
                baseWeather = (BaseWeather) mLayoutInflater.inflate(R.layout.weather_storm, null);
                break;
            case TSTORMS_NIGHT:
                baseWeather = (BaseWeather) mLayoutInflater.inflate(R.layout.weather_storm, null);
                break;
            case ICE_DAY:
                baseWeather = (BaseWeather) mLayoutInflater.inflate(R.layout.weather_ice_day, null);
                break;
            case ICE_NIGHT:
                baseWeather = (BaseWeather) mLayoutInflater.inflate(R.layout.weather_ice_night, null);
                break;*/
            case OVERCAST_DAY:
                v = new OverDayWeatherView(this);
                break;
            case OVERCAST_NIGHT:
                v = new OverDayWeatherView(this);
                break;
            default:
                v = new DefaultWeatherView(this);
                break;
        }

        ((GLView)v).mRenderer.setCurrentWeather(new CurrentWeather(city, temp,getDescription(weather), mobileLink));
        return v;
    }

    public String getDescription(Weather weather){
        if(weather == null || weather.getDescriptionResId() == 0){
            return "N/A";
        }else{
            return getResources().getString(weather.getDescriptionResId());
        }
    }

    public void location() {
        Log.v("lmjssjj", "Launcher location start");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestLocationPermission();
        } else {
            // checkLocationEnabled();
            startLocation();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestLocationPermission() {
        String permission1 = Manifest.permission.ACCESS_FINE_LOCATION;
        //String permission2 = Manifest.permission.ACCESS_COARSE_LOCATION;
        int hasPermission = checkSelfPermission(permission1);
        String permissions[] = new String[]{permission1/*, permission2*/};
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, REQUEST_PERMISSION_LOCATION);
        } else {
            // checkLocationEnabled();
            startLocation();
        }
    }

    public void startLocation() {
        try {
            mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && mLocationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
                //			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, getMainLooper());
            }
        }catch (Exception e) {
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location==null){
                return;
            }
            Log.e(TAG, "onLocationChanged:" + location.getLatitude() + ":" + location.getLongitude());
                WeatherAppState.getInstance().getRequestApi().getCityAccordingLL(location.getLatitude(),
                        location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged:" + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled:" + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled:" + provider);
        }
    };

}
