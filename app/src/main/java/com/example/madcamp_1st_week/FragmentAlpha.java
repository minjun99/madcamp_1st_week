package com.example.madcamp_1st_week;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.madcamp_1st_week.WeatherConnection.WeatherConnection;
import com.example.madcamp_1st_week.WeatherInfo.WeatherInfo;
import com.example.madcamp_1st_week.WeatherModel.OpenWeatherMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

public class FragmentAlpha extends Fragment implements LocationListener {
    View view;
    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    ImageView weatherImage;

    LocationManager locationManager;
    String provider;
    static double lat, lng;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    int MY_PERMISSION = 0;

    public FragmentAlpha() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.alpha_fragment, container, false);

        txtCity = (TextView) view.findViewById(R.id.txtcity_id);
        txtLastUpdate = (TextView) view.findViewById(R.id.txtLastUpdate_id);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription_id);
        txtHumidity = (TextView) view.findViewById(R.id.txtHumidity_id);
        txtTime = (TextView) view.findViewById(R.id.txtTime_id);
        txtCelsius = (TextView) view.findViewById(R.id.txtCelsius_id);
        weatherImage = (ImageView) view.findViewById(R.id.weatherImage_id);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET,
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_NETWORK_STATE,
//                    Manifest.permission.SYSTEM_ALERT_WINDOW,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION);
//            getActivity().finish();
        }

        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            Log.e("TAG", "No Location");
            txtTime.setText("location Pointer is null\nGPS caanot catch the SIGNAL");

        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET,
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_NETWORK_STATE,
//                    Manifest.permission.SYSTEM_ALERT_WINDOW,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET,
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_NETWORK_STATE,
//                    Manifest.permission.SYSTEM_ALERT_WINDOW,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        new getWeather().execute(WeatherInfo.apiRequest(String.valueOf(lat), String.valueOf(lng)));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class getWeather extends AsyncTask<String, Void, String> {

        Dialog pd = new Dialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            WeatherConnection http = new WeatherConnection();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.contains("Error: Not found city")){
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s, mType);
            pd.dismiss();

            txtCity.setText(String.format("%s, %s", openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
            txtLastUpdate.setText(String.format("Last Updated: %s", WeatherInfo.getDateNow()));
            txtDescription.setText(String.format("Weather: %s", openWeatherMap.getWeather().get(0).getDescription()));
            txtHumidity.setText(String.format("Humidity: %d%%", openWeatherMap.getMain().getHumidity()));
            txtTime.setText(String.format("SunRise: %s / SunSet: %s", WeatherInfo.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()), WeatherInfo.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
            txtCelsius.setText(String.format("Temperature: %.2f °C", openWeatherMap.getMain().getTemp()));
            Picasso.get().load(WeatherInfo.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                    .into(weatherImage);
        }
    }
}
