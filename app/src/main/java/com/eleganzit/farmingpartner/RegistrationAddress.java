package com.eleganzit.farmingpartner;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eleganzit.farmingpartner.activity.SignUpFarmDetails;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class RegistrationAddress extends AppCompatActivity implements OnMapReadyCallback {
    MapView mapView;
    LocationManager locationmanager;
    private static final int PLACE_PICKER_REQUEST2 = 1001;

    GoogleMap map;
    Criteria cri;
    Button btn_submit;
    EditText ed_address;
    private String result="";
    private String latitude="",longitude="";
    private String TAG="RegistrationAddressData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_selection);
        mapView= (MapView) findViewById(R.id.map);
        btn_submit=  findViewById(R.id.btn_submit);
        ed_address=  findViewById(R.id.ed_address);
        mapView.getMapAsync(this);
        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        cri = new Criteria();
        if(mapView != null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ed_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(RegistrationAddress.this);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, PLACE_PICKER_REQUEST2);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_address.getText().toString()!=null && !(ed_address.getText().toString().isEmpty()))
                {

                    startActivity(new Intent(RegistrationAddress.this, SignUpFarmDetails.class)
                            .putExtra("add",""+ed_address.getText().toString())
                            .putExtra("redirect","yes")
                    .putExtra("lat",""+latitude)
                    .putExtra("lng",""+longitude)

                    );

                    finish();
                }
                else
                {
                    Toast.makeText(RegistrationAddress.this, "Select location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST2) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("%s", place.getName());
                LatLng latLng=place.getLatLng();


                Log.d(TAG,""+latLng.latitude);
                Log.d(TAG,""+latLng.longitude);
                CameraUpdate location2 = CameraUpdateFactory.newLatLngZoom(
                        new LatLng(latLng.latitude,latLng.longitude), 15);
                map.animateCamera(location2);

                if (toastMsg.equalsIgnoreCase(""))
                {

                }
                else {

                    ed_address.setText(""+toastMsg);


                }



            }
            else {

                //    Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,SignUpFarmDetails.class).putExtra("redirect","yes"));
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getApplicationContext());
        googleMap.getUiSettings().setAllGesturesEnabled(true);

        map = googleMap;
        boolean success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getApplicationContext(), R.raw.style_json));

        if (!success) {
            Log.e("MainAct", "Style parsing failed.");
        }
        Log.e("ddddddd", "Style parsing failed.");
        map.getUiSettings().isMyLocationButtonEnabled();
        map.setMyLocationEnabled(true);
        getLatLong();
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                ed_address.setText("");
                //  getAddress(cameraPosition.target.latitude,cameraPosition.target.longitude);
                latitude=""+cameraPosition.target.latitude;
                longitude=""+cameraPosition.target.longitude;
                new RegistrationAddress.DataLongOperationAsynchTask().execute();

                Log.i("centerLat",""+cameraPosition.target.latitude);

                Log.i("centerLong",""+cameraPosition.target.longitude);
            }
        });
       /* map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

                map.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });

//Don't forget to Set draggable(true) to marker, if this not set marker does not drag.

        map.addMarker(new MarkerOptions()
                .position(new LatLng(23.0225,72.5714))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location))

                .draggable(true));

*/
    }
    public void getAddress(final double latitude, final double longitude)
    {

        Log.d("hkhkh","called");
        try {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        String city="",country="",state="",postalCode="",knownName="",address="";
                        if(geocoder.isPresent()) {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            if (addresses.size()>0) {

                                //  address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                city = addresses.get(0).getLocality();
                                state = addresses.get(0).getAdminArea();
                                country = addresses.get(0).getCountryName();
                                postalCode = addresses.get(0).getPostalCode();
                                knownName = addresses.get(0).getFeatureName(); // Only if a


                                result = address;


                                StringBuffer str = new StringBuffer();
                              //  str.setLength(0);
                                if (addresses.get(0).getLocality()!=null && !(addresses.get(0).getLocality().isEmpty()))
                                {
                                    str.append(addresses.get(0).getLocality() + ",");

                                }
                                if (addresses.get(0).getSubAdminArea()!=null && !(addresses.get(0).getSubAdminArea().isEmpty()))
                                {
                                    str.append(addresses.get(0).getSubAdminArea() + ",");

                                }
                                if (addresses.get(0).getAdminArea()!=null && !(addresses.get(0).getAdminArea().isEmpty()))
                                {
                                    str.append(  addresses.get(0).getAdminArea() + ",");

                                }
                                if (addresses.get(0).getCountryName()!=null && !(addresses.get(0).getCountryName().isEmpty()))
                                {
                                    str.append( addresses.get(0).getCountryName() + ",");

                                }
                                if (addresses.get(0).getCountryCode()!=null && !(addresses.get(0).getCountryCode().isEmpty()))
                                {
                                    str.append(  addresses.get(0).getCountryCode() + "");

                                }
                                Log.d("Addressss", "" + str);
                                String superString = str.toString();
                                Log.d("superString", "--" + superString);

                                ed_address.setText(""+superString);

                                Log.d("Addressss", "" + city);
                                Log.d("Addressss", "" + state);
                                Log.d("Addressss", "" + country);
                                Log.d("Addressss", "" + postalCode);
                                Log.d("Addressss", "" + knownName);
                            }
                        }
                        else
                        {
                            new RegistrationAddress.DataLongOperationAsynchTask().execute();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        new RegistrationAddress.DataLongOperationAsynchTask().execute();

                    }

                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
            new RegistrationAddress.DataLongOperationAsynchTask().execute();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class DataLongOperationAsynchTask extends AsyncTask<String,Void,String[]> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog   =new ProgressDialog(RegistrationAddress.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.show();



        }

        @Override
        protected String[] doInBackground(String... strings) {
            String response;
            try {
                response = getLatLongByURL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=AIzaSyCZJDD1Osg2A3bYeAQG6UtTD9fll8t5-IU");
                Log.d("response", "" + response);
                return new String[]{response};
            } catch (Exception e) {
                return new String[]{"error"};
            }
        }

        @Override
        protected void onPostExecute(String... strings) {
            try {
                JSONObject jsonObject = new JSONObject(strings[0]);

                JSONArray Results = jsonObject.getJSONArray("results");

                Log.d("ddddddddd", "" + jsonObject);
                Log.d("ddddddddd", "" + result.length());

                JSONObject zero = Results.getJSONObject(0);
                JSONArray address_components = zero.getJSONArray("address_components");
                String superString = "";
                String city="",country="",state="",postalCode="",knownName="",address="";

                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject zero2 = address_components.getJSONObject(i);
                    String long_name = zero2.getString("long_name");
                    JSONArray mtypes = zero2.getJSONArray("types");
                    String Type = mtypes.getString(0);

                    if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {
                        if (Type.equalsIgnoreCase("street_number")) {
                            address = long_name + " ";
                        } else if (Type.equalsIgnoreCase("route")) {
                            address = address + long_name;
                        } else if (Type.equalsIgnoreCase("sublocality")) {
                            address = long_name;
                        } else if (Type.equalsIgnoreCase("locality")) {
                            // Address2 = Address2 + long_name + ", ";
                            city = long_name;
                        } else if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                            country = long_name;
                        } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                            state = long_name;
                        } else if (Type.equalsIgnoreCase("country")) {
                            country = long_name;
                        } else if (Type.equalsIgnoreCase("postal_code")) {
                            postalCode = long_name;
                        }
                    }

                    // JSONArray mtypes = zero2.getJSONArray("types");
                    // String Type = mtypes.getString(0);
                    // Log.e(Type,long_name);



                    Log.d("backkkkk", "called is present");
                    Log.d("Addressssapist", "" + state);
                    Log.d("Addressssapic", "" + country);
                    Log.d("Addressssapiadd", "" + address);
                    Log.d("Addressssapi", "" + knownName);


                    StringBuffer str = new StringBuffer();
                    //str.setLength(0);
                    if (knownName!=null && !knownName.isEmpty())
                    {
                        str.append(knownName + ",");

                    }
                    if (address!=null && !address.isEmpty())
                    {
                        str.append(address + ",");

                    }
                    if (city!=null && !city.isEmpty())
                    {
                        str.append(  city + ",");

                    }
                    if (state!=null && !state.isEmpty())
                    {
                        str.append( state + ",");

                    }
                    if (country!=null && !country.isEmpty())
                    {
                        str.append(  country + "");

                    }
                    superString= str.toString();

                    Log.d("superString", "--->" + superString);



                }
                ed_address.setText("" + superString);
                progressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }


        }

    }
    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    public void getLatLong()
    {
        if (checkLocationPermission()) {
            // Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(RegistrationAddress.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                String provider = locationmanager.getBestProvider(cri, false);
                if(locationmanager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


                    locationmanager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,  new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                            CameraUpdate location2 = CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(location.getLatitude(),location.getLongitude()), 15);
                            map.animateCamera(location2);



                            Log.d("fgfgfgdg","sdrgdgdg");
                            Log.e("LOGINlat", "----"+String.valueOf(location.getLatitude()));
                            Log.e("LOGINlat", String.valueOf(location.getLongitude()));
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
                    },null);
                }
                getLastKnownLocation();



            }
        }
    }
    private void getLastKnownLocation() {
        List<String> providers = locationmanager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(RegistrationAddress.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                    checkSelfPermission(RegistrationAddress.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return ;
            }
            Location l = locationmanager.getLastKnownLocation(provider);

//            Log.e("last known location, provider: %s, location: %s", provider,
//                    l);

//            Toast.makeText(this, ""+l.getLatitude()+","+l.getLongitude(), Toast.LENGTH_SHORT).show();

            if (l == null) {
                continue;
            }
            else {
                Log.d("gxgd",l.getLatitude()+"xfgxdg"+l.getLongitude());



            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                //   ALog.d("found best last known location: %s", l);
                bestLocation = l;





            }
        }
        if (bestLocation == null) {

        }
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(RegistrationAddress.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationAddress.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.support.v7.app.AlertDialog.Builder(RegistrationAddress.this)
                        .setTitle("Location Permission")
                        .setMessage("Allow app to use your current location")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(RegistrationAddress.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(RegistrationAddress.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            return false;
        } else {
            return true;
        }
    }
}
