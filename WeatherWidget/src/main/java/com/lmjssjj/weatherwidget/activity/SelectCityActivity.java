package com.lmjssjj.weatherwidget.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lmjssjj.weatherwidget.R;
import com.lmjssjj.weatherwidget.WeatherAppState;
import com.lmjssjj.weatherwidget.WeatherConfig;
import com.lmjssjj.weatherwidget.model.CityModel;
import com.lmjssjj.weatherwidget.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ya.wan on 2017/11/3.
 */

public class SelectCityActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "SelectCityActivity";
    private EditText mCityEdit;
    private ImageView mBackImage, mDelImage;
    private LinearLayout mCityScroll;
    private Context mContext;
    private StringRequest mStringRequest;

    private TextView mTvLocationInfo;
    private TextView mTvInfo;
    private TextView mTvTitle;
    private View mLocationLayout;

    //rm location listener if location timeout
    private static final long MSG_REMOVE_LOCATION_TIME = 1000 * 30;
    private static final int MSG_REMOVE_LOCATION_LISTENER = 0;
    private static final int MSG_SHOW_INPUT_METHOD = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_REMOVE_LOCATION_LISTENER:
                    releaseLocationListener();
                    Log.v(TAG, "location fail because location timeout");
                    mTvLocationInfo.setText(R.string.weather_select_city_location_fail);
                    break;
                case MSG_SHOW_INPUT_METHOD:
                    showInputMethod();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    public static final int REQUEST_PERMISSION_LOCATION = 0;


    private char[] mData = new char[]{'q', 'w', 'e', 'r', 't', 'y', 'u', 'i',
            'o', 'p', 'l', 'k', 'j', 'h', 'g', 'f', 'd', 's', 'a', 'z', 'x',
            'c', 'v', 'b', 'n', 'm', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I',
            'O', 'P', 'L', 'K', 'J', 'H', 'G', 'F', 'D', 'S', 'A', 'Z', 'X',
            'C', 'V', 'B', 'N', 'M', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '0'};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_city);
        this.getWindow().getDecorView()
                .setBackground(new ColorDrawable(0xfff4f4f4));
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mContext = getApplicationContext();
        mCityEdit = (EditText) findViewById(R.id.city_name);
        mCityScroll = (LinearLayout) findViewById(R.id.city_scroll);

        mCityEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                startRequest(s);
                if (!TextUtils.isEmpty(s)) {
                    mDelImage.setVisibility(View.VISIBLE);
                } else {
                    mDelImage.setVisibility(View.INVISIBLE);
                    mTvInfo.setVisibility(View.GONE);
                    mCityScroll.removeAllViews();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // mCityEdit.setKeyListener(new DigitsKeyListener() {//限制只能输入英文
        // @Override
        // public int getInputType() {
        // return InputType.TYPE_TEXT_VARIATION_PASSWORD;
        // }
        //
        // @Override
        // protected char[] getAcceptedChars() {
        // return mData;
        // }
        // });
        mBackImage = (ImageView) findViewById(R.id.select_city_back);
        if (Utilities.isRtl(getResources())) {
            mBackImage.setImageResource(R.mipmap.select_city_back_rtl);
        }
        mBackImage.setOnClickListener(this);
        mDelImage = (ImageView) findViewById(R.id.select_city_delect);
        mDelImage.setOnClickListener(this);
        init();
    }

    public void init() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mTvLocationInfo = (TextView) findViewById(R.id.tv_location_info);
        mLocationLayout = findViewById(R.id.rl_location_layout);
        mLocationLayout.setOnClickListener(this);

        Typeface robotab = Typeface.createFromAsset(getAssets(),
                "Roboto-Regular.ttf");
        mTvTitle.setTypeface(robotab);
        mTvLocationInfo.setTypeface(robotab);

        if (Utilities.isNetworkAvailable(this)) {
            mTvInfo.setVisibility(View.GONE);
        } else {
            mTvInfo.setVisibility(View.VISIBLE);
            mTvInfo.setText(R.string.weather_no_network);
        }

        final IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(mReceiver, filter);
        location();
        //10584 点击时间天气widget默认界面？或者N/A进入添加城市页，页面比键盘弹出的慢
        mHandler.sendEmptyMessageDelayed(MSG_SHOW_INPUT_METHOD, 300);
    }

    //10584 点击时间天气widget默认界面？或者N/A进入添加城市页，页面比键盘弹出的慢
    private void showInputMethod() {
        mCityEdit.setFocusable(true);
        mCityEdit.setFocusableInTouchMode(true);
        mCityEdit.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) mCityEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(mCityEdit, 0);
    }

    private void startRequest(final CharSequence s) {
        if (mStringRequest != null) {
            mStringRequest.cancel();
        }
        String str = WeatherConfig.CITY_URL;
        String url = String.format(str, s, WeatherConfig.API_KEY);
        mStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public void onResponse(String result) {
                        Log.d("y.wan", "" + result);
                        if (!TextUtils.isEmpty(result)) {
                            mTvInfo.setVisibility(View.GONE);
                            Gson gson = new Gson();
                            List<CityModel> citys = new ArrayList<CityModel>();
                            try {
                                citys = gson.fromJson(result, new TypeToken<List<CityModel>>() {
                                }.getType());
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                            if (citys != null && citys.size() > 0) {
                                mCityScroll.removeAllViews();
                                for (final CityModel city : citys) {
                                    TextView text = new TextView(
                                            SelectCityActivity.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            getResources()
                                                    .getDimensionPixelOffset(
                                                            R.dimen.weather_select_city_item));
                                    int padding = getResources()
                                            .getDimensionPixelOffset(
                                                    R.dimen.weather_select_city_padding);
                                    text.setPadding(padding, 0, 0, 0);
                                    text.setText(city.getLocalizedName()
                                            + "\t"
                                            + city.getCountry()
                                            .getLocalizedName()
                                            + "("
                                            + city.getAdministrativeArea()
                                            .getLocalizedName() + ")");
                                    text.setTextSize(14f);
                                    text.setTextColor(0xff505050);
                                    text.setGravity(Gravity.START
                                            | Gravity.CENTER_VERTICAL);
                                    TypedValue typedValue = new TypedValue();
                                    mContext.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
                                    text.setBackgroundResource(typedValue.resourceId);
                                    text.setLayoutParams(params);
                                    text.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent();
                                            intent.putExtra(WeatherConfig.CITY, city.getLocalizedName());
                                            intent.putExtra(WeatherConfig.KEY, city.getKey());
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    });
                                    mCityScroll.addView(text);
                                    View view = new View(
                                            SelectCityActivity.this);
                                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            1);
                                    view.setBackgroundColor(0xffe5e5e5);
                                    view.setLayoutParams(params1);
                                    mCityScroll.addView(view);
                                }
                            } else {
                                if (!TextUtils.isEmpty(s)) {

                                    mCityScroll.removeAllViews();
                                    mTvInfo.setVisibility(View.VISIBLE);
                                    mTvInfo.setText(R.string.weather_no_city);
                                }
                            }
                        } else {
                            if (!TextUtils.isEmpty(s)) {

                                mCityScroll.removeAllViews();
                                mTvInfo.setVisibility(View.VISIBLE);
                                mTvInfo.setText(R.string.weather_no_city);
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.d("y.wan", "erro:" + arg0.toString());
                // Toast.makeText(mContext, R.string.request_error,
                // Toast.LENGTH_SHORT).show();
            }
        });
        WeatherAppState app = WeatherAppState.getInstance();
        mStringRequest.setShouldCache(false);
        app.getRequestQueue().add(mStringRequest);
    }

    //request city info
    public void requestLocation(double longitude, double latitude) {

        String s = WeatherConfig.LL_URL;
        String url = String.format(s, String.valueOf(longitude) + "," + String.valueOf(latitude), WeatherConfig.API_KEY, Locale.getDefault().getLanguage());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public void onResponse(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            Gson gson = new Gson();
                            CityModel city = null;
                            try {
                                city = gson.fromJson(result, CityModel.class);
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                            if (city != null) {
                                mTvLocationInfo.setText(city.getLocalizedName());
                                Log.v(TAG, "WeatherRequest city key:" + city.getKey());
                                Log.v(TAG, "WeatherRequest city LocalizedName:" + city.getLocalizedName());
                                mLocationLayout.setTag(city);
                            } else {
                                Log.v(TAG, "location fail because  dont data to back");
                                mTvLocationInfo.setText(R.string.weather_select_city_location_fail);
                            }
                        } else {
                            Log.v(TAG, "location fail because  dont data to back");
                            mTvLocationInfo.setText(R.string.weather_select_city_location_fail);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.v(TAG, "location fail because  onErrorResponse");
                mTvLocationInfo.setText(R.string.weather_select_city_location_fail);
            }
        });
        WeatherAppState app = WeatherAppState.getInstance();
        stringRequest.setShouldCache(false);
        app.getRequestQueue().add(stringRequest);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mStringRequest != null) {
            mStringRequest.cancel();
        }
        unregisterReceiver(mReceiver);
        releaseLocationListener();
    }

    /**
     * 重载方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_city_back:
                finish();
                break;
            case R.id.select_city_delect:
                mCityEdit.setText("");
                mCityScroll.removeAllViews();
                mDelImage.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_location_layout:
                if (mLocationLayout.getTag() == null) {
                    location();
                } else {
                    CityModel city = (CityModel) mLocationLayout.getTag();
                    Intent intent = new Intent();
                    intent.putExtra(WeatherConfig.CITY, city.getLocalizedName());
                    intent.putExtra(WeatherConfig.KEY, city.getKey());
                    intent.putExtra(WeatherConfig.LOCATION, true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                if (Utilities.isNetworkAvailable(getBaseContext())) {
                    Log.v(TAG, "has network");
                    mTvInfo.setVisibility(View.GONE);
                    if (mLocationLayout.getTag() == null) {
                        location();
                    }
                } else {
                    Log.v(TAG, "network close");
                    mCityScroll.removeAllViews();
                    mTvInfo.setVisibility(View.VISIBLE);
                    mTvInfo.setText(R.string.weather_no_network);
                }
            }
        }
    };

    /**
     * @Title: isNeedLocation
     * @Description: TODO()
     * @date: 2017年6月26日 下午1:55:44
     * @param: @return
     * @return: boolean
     * @throws
     */
    public boolean isNeedLocation() {
        boolean result = false;
        if (Utilities.isNetworkAvailable(this)) {
            result = true;
        }

        return result;
    }

    private LocationManager mLocationManager;

    public void location() {
        if (!isNeedLocation()) {
            Log.v(TAG, "location fail because  dont network to location");
            mTvLocationInfo.setText(R.string.weather_select_city_location_fail);
            return;
        }
        Log.v("lmjssjj", "Launcher location start");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestLocationPermission();
        } else {
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

    //start location
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
                mHandler.sendEmptyMessageDelayed(MSG_REMOVE_LOCATION_LISTENER, MSG_REMOVE_LOCATION_TIME);
                mTvLocationInfo.setText(R.string.weather_select_city_location_ing);
            }else{
                Log.v(TAG, "location fail because GPS dont open");
                mTvLocationInfo.setText(R.string.weather_select_city_location_fail);
            }
        }catch (Exception e) {
            // TODO: handle exception
        }
    }
    //location listener
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location==null){
                return;
            }
            Log.e(TAG, "onLocationChanged:" + location.getLatitude() + ":" + location.getLongitude());
            mHandler.removeMessages(MSG_REMOVE_LOCATION_LISTENER);
            requestLocation(location.getLatitude(),
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_LOCATION){
            boolean isAllGranted = true;
            if(grantResults ==null ||grantResults.length==0){
                return;
            }
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    Log.v(TAG, "location fail because PERMISSION_DENIED");
                    mTvLocationInfo.setText(R.string.weather_select_city_location_fail);
                    break;
                }
            }
            if(isAllGranted){
                startLocation();
            }
        }
    }

    //remove the location listener
    public void releaseLocationListener(){
        if(mLocationManager!=null){
            mLocationManager.removeUpdates(locationListener);
        }
    }
}
