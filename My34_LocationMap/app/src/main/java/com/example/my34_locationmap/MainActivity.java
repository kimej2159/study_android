package com.example.my34_locationmap;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main:MainActivity";
    SupportMapFragment mapFragment;
    GoogleMap map;
    EditText etAddress;

    MarkerOptions myMarker;

    Location myLoc, markerLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();        // μν κΆν

        etAddress = findViewById(R.id.etAddress);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        //mapμ΄ μ€λΉκ° λλ©΄ onMapReadyλ₯Ό μ½λ°±νλ€
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Log.d(TAG, "onMapReady: Google Map is Ready...");

                map = googleMap;
                try{
                    //λ΄ μμΉλ₯Ό λ³Ό μ μκ² ν΄μ€λ€
                    map.setMyLocationEnabled(true);
                }catch (SecurityException e){
                    e.getMessage();
                }

                //νλμ
                map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Nullable
                    @Override
                    public View getInfoContents(@NonNull Marker marker) {
                        LinearLayout info = new LinearLayout((getApplicationContext()));
                        info.setOrientation(LinearLayout.VERTICAL);

                        //  Marker title
                        TextView title = new TextView(getApplicationContext());
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        // Marker snippet
                        TextView snippet = new TextView(getApplicationContext());
                        snippet.setTextColor(Color.RED);
                        snippet.setGravity(Gravity.LEFT);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }

                    @Nullable
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        return null;
                    }
                });


            }
        });

        //κ΅¬κΈ λ§΅ μ΄κΈ°ν
        MapsInitializer.initialize(this);

        //λ΄ μμΉ μ°ΎκΈ°
        findViewById(R.id.btnLoc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMyLocation();
            }
        });

        //νκΈ μ£Όμλ₯Ό μλμ κ²½λλ‘ νμνλ€
        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAddress.getText().toString().length() > 0){
                    Location location = getLocationFromAddress
                            (MainActivity.this, etAddress.getText().toString());

                    // νκΈμ£Όμμμ locationμΌλ‘ λ³νν κ²μ μ§λμμ λ³΄μ¬μ€λ€
                    showCurrentLocation(location);

                }
            }
        });



    }
    //νκΈ μ£Όμλ₯Ό λ°μμ Location ννλ‘ λ³κ²½μν¨μ λ³΄λ΄μ£Όλ λ§€μλ
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");

        // νκΈ μ£Όμ, μ΅λλ°νμ£Όμ κ°―μ
        try {
            addresses = geocoder.getFromLocationName(address, 5);
            if((addresses == null) || (addresses.size() == 0)){
                return null;
            }

            //λκ²¨λ°μ μ£Όμλ¦¬μ€νΈμμ κ°μ₯ μ£Όμμ κ°κΉμ΄ 0λ²μ§Έ κ°μ μ¬μ©νλ€
            Address addressLoc = addresses.get(0);
            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return resLocation;
    }

    private void requestMyLocation() {
        LocationManager manager =
                (LocationManager) getSystemService(LOCATION_SERVICE);
        try{
        long minTime =  20000;
        float minDistance = 0;

        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        //μμΉλ₯Ό μ§λμ νμ
                        showCurrentLocation(location);
                    }
                }

        );
            // λ§μ½ ν°λ κ°μ κ³³μ λ€μ΄κ°λ©΄ μ νΈλ₯Ό λ°μ§ λͺ»νλ―λ‘
            // λ§μ§λ§ μμ λ κ³³μ μλ €μ£Όκ² λλ€
            Location lastLocation =
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation !=null){
                //λ§μ§λ§μ μμ λ μ₯μλ₯Ό μ§λμ νμνλ€
               showCurrentLocation(lastLocation);
            }

        }catch (SecurityException e){
            e.getMessage();
        }


    }

    private void showCurrentLocation(Location location) {
        // νμ¬ λ΄ μμΉ μ μ­ λ³μμ λ£μ
        myLoc = location;

        //μ§λμ μμΉλ₯Ό μ°μ λλ LatLngνμμ μ¬μ© => Locationμ LatLng νμμΌλ‘ λ³νμμΌμ€
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

        String msg = "Latitude : " + curPoint.latitude
                    + "\nLongitude : " + curPoint.longitude;
        Log.d(TAG, "showCurrentLocation: " + msg);

        //μ§λμ νμ¬μμΉ νμνκΈ°
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 18));

        //λ§μ»€ μ°κΈ° : Location μμ± : λμ€μλ DBλ APIμμ κ°μ Έμ΄
        Location location1 = new Location("");
        location1.setLatitude(35.153817);
        location1.setLongitude(126.8889);
        String name = "λ§μ½κΎΈλ¬κΈ°";
        String phone = "010-5096-0686";
        showMyLocationMarker(location1, name, phone);


        Location location2 = new Location("");
        location2.setLatitude(35.153825);
        location2.setLongitude(126.8885);
        showMyLocationMarker(location2, "κ²Έλ₯μ΄", "010-4176-2159");

    }

    // location λ°μμ λ§μ»€ μμ±νμ¬ μ§λμ νμνκΈ°
    private void showMyLocationMarker(Location location,
                                      String name, String phone){
       // λ§μ»€ μμΉλ₯Ό μ μ­λ³μμ λ΄μ κ²κ²
       markerLoc = location;
       // λ§μ»€μ λ΄ μμΉκΉμ§μ κ±°λ¦¬λ₯Ό κ΅¬νλ€
        int distance = getDistance(myLoc, markerLoc);

        if(myMarker == null){
            myMarker = new MarkerOptions();
            myMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myMarker.title("β " + name);
            myMarker.snippet(phone + "\nκ±°λ¦¬ => " + distance + " M");
            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myMarker);
            myMarker = null;


        }
    }

    private int getDistance(Location myLoc, Location markerLoc) {
        double distance = 0;
        //κ±°λ¦¬λ₯Ό κ΅¬ν  λλ Location νμμ μ¬μ©
        distance = myLoc.distanceTo(markerLoc);

        return (int)distance;       // λλΈμ intνμΌλ‘ κ°μ  μΊμ€νν
    }
    // μνκΆν : μ€νμ νμ©μ¬λΆλ₯Ό λ€μ λ¬Όμ΄λ΄
    private void checkDangerousPermissions() {
        String[] permissions = {
                // μνκΆν λ΄μ© : λ©λνμ€νΈμ κΆνμ μ¬κΈ°μ μ μ
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "κΆν μμ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "κΆν μμ", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "κΆν μ€λͺ νμν¨.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " κΆνμ΄ μΉμΈλ¨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " κΆνμ΄ μΉμΈλμ§ μμ.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}