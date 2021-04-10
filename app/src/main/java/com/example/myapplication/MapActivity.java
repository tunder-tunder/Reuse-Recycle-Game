package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MapActivity extends AppCompatActivity {

    LocationManager locationManager;
    Location location;

    private boolean granted = false;
    private final int LOCATION_PERMISSION = 1001;

    private MapView mapview;
    private IconStyle iconstyles;
    private ImageProvider imageProvider;

    private static final String TAG = "DebugMap";

    private final String MAPKIT_API_KEY = "a33be2e7-56d3-45dd-8f46-d620fac09ffb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_map);
        mapview = (MapView)findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(52.271389, 104.284958), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        // Значок мусорки
        iconstyles = new IconStyle();
        imageProvider = ImageProvider.fromResource(this, R.mipmap.ic_recycle_bin_image_foreground);
        iconstyles.setScale(0.5f);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        // Вывести маркет на карту
        mapview.getMap().getMapObjects().addPlacemark(new Point(52.280593, 104.277671), imageProvider, iconstyles).addTapListener(new MapObjectTapListener() {
            @Override
            public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(52.280593, 104.277671, 1);

                    Address returnedAddress = addresses.get(0);

                    Intent intent = new Intent(MapActivity.this, DumpsterActivity.class);
                    intent.putExtra("address", returnedAddress.getAddressLine(0));

                    Log.d(TAG, "Intent → Intent");

                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Log.d(TAG, "Жизненный цикл → Вызвался метод onCreate()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();

        Log.d(TAG, "Жизненный цикл → Вызвался метод onStop()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        Log.d(TAG, String.valueOf(mapview.getMap().getMapObjects()));

        if (granted || checkPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 30, listener);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    showLocation(location);
                }
            }
        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location == null) {
                return;
            } else {
                showLocation(location);
            }
        }
    };

    private void showLocation(Location location) {

        mapview.getMap().getMapObjects().addPlacemark(new Point(location.getLatitude(), location.getLongitude()), imageProvider, iconstyles);
        // Широта
        Log.d(TAG, "Широта → " + String.valueOf(location.getLatitude()));

        // latText.setText(String.valueOf(location.getLatitude()));

        // Долгота
        Log.d(TAG, "Долгота → " + String.valueOf(location.getLongitude()));

        // lonText.setText(String.valueOf(location.getLongitude()));

        // Время
        // timeText.setText(new Date(location.getTime()).toString());
    }

    protected boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION) {
            granted = true;

            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_DENIED) {
                        granted = false;
                    }
                }
            } else {
                granted = false;
            }
        }
    }
}