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

        checkDangerousPermissions();        // 위험 권한

        etAddress = findViewById(R.id.etAddress);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        //map이 준비가 되면 onMapReady를 콜백한다
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Log.d(TAG, "onMapReady: Google Map is Ready...");

                map = googleMap;
                try{
                    //내 위치를 볼 수 있게 해준다
                    map.setMyLocationEnabled(true);
                }catch (SecurityException e){
                    e.getMessage();
                }

                //하나의
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

        //구글 맵 초기화
        MapsInitializer.initialize(this);

        //내 위치 찾기
        findViewById(R.id.btnLoc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMyLocation();
            }
        });

        //한글 주소를 위도와 경도로 표시한다
        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAddress.getText().toString().length() > 0){
                    Location location = getLocationFromAddress
                            (MainActivity.this, etAddress.getText().toString());

                    // 한글주소에서 location으로 변환한 것을 지도에서 보여준다
                    showCurrentLocation(location);

                }
            }
        });



    }
    //한글 주소를 받아서 Location 형태로 변경시텨서 보내주는 매소드
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");

        // 한글 주소, 최대반환주소 갯수
        try {
            addresses = geocoder.getFromLocationName(address, 5);
            if((addresses == null) || (addresses.size() == 0)){
                return null;
            }

            //넘겨받은 주소리스트에서 가장 주소에 가까운 0번째 값을 사용한다
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
                        //위치를 지도에 표시
                        showCurrentLocation(location);
                    }
                }

        );
            // 만약 터널 같은 곳에 들어가면 신호를 받지 못하므로
            // 마지막 수신된 곳을 알려주게 된다
            Location lastLocation =
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation !=null){
                //마지막에 수신된 장소를 지도에 표시한다
               showCurrentLocation(lastLocation);
            }

        }catch (SecurityException e){
            e.getMessage();
        }


    }

    private void showCurrentLocation(Location location) {
        // 현재 내 위치 전역 변수에 넣음
        myLoc = location;

        //지도에 위치를 찍을 때는 LatLng타입을 사용 => Location을 LatLng 타입으로 변환시켜줌
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

        String msg = "Latitude : " + curPoint.latitude
                    + "\nLongitude : " + curPoint.longitude;
        Log.d(TAG, "showCurrentLocation: " + msg);

        //지도에 현재위치 표시하기
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 18));

        //마커 찍기 : Location 생성 : 나중에는 DB나 API에서 가져옴
        Location location1 = new Location("");
        location1.setLatitude(35.153817);
        location1.setLongitude(126.8889);
        String name = "말썽꾸러기";
        String phone = "010-5096-0686";
        showMyLocationMarker(location1, name, phone);


        Location location2 = new Location("");
        location2.setLatitude(35.153825);
        location2.setLongitude(126.8885);
        showMyLocationMarker(location2, "겸둥이", "010-4176-2159");

    }

    // location 받아서 마커 생성하여 지도에 표시하기
    private void showMyLocationMarker(Location location,
                                      String name, String phone){
       // 마커 위치를 전역변수에 담은 것것
       markerLoc = location;
       // 마커와 내 위치까지의 거리를 구한다
        int distance = getDistance(myLoc, markerLoc);

        if(myMarker == null){
            myMarker = new MarkerOptions();
            myMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myMarker.title("◎ " + name);
            myMarker.snippet(phone + "\n거리 => " + distance + " M");
            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myMarker);
            myMarker = null;


        }
    }

    private int getDistance(Location myLoc, Location markerLoc) {
        double distance = 0;
        //거리를 구할 때는 Location 타입을 사용
        distance = myLoc.distanceTo(markerLoc);

        return (int)distance;       // 더블을 int형으로 강제 캐스팅팅
    }
    // 위험권한 : 실행시 허용여부를 다시 물어봄
    private void checkDangerousPermissions() {
        String[] permissions = {
                // 위험권한 내용 : 메니페스트에 권한을 여기에 적음
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
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}