package com.tech.twe_tutorside.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.tech.twe_tutorside.GPSTracker;
import com.tech.twe_tutorside.Preference;
import com.tech.twe_tutorside.R;
import com.tech.twe_tutorside.utils.RetrofitClients;
import com.tech.twe_tutorside.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GooglePlacesAutocompleteActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private final Integer THRESHOLD = 2;
    public String order_landmarkadd;
 //   public AppointmentFragment fragment;
    int PERMISSION_ID = 44;
    private TextView gettypedlocation;
    private int count = 0;

    private String order_landmarkadd1;
    private TextView cancle_text;
    private GooglePlacesAutocompleteActivity mContext;
    private GPSTracker gps;
    private String address;
    private GoogleMap mMap;
    private LatLng locationLatLong;
    private Animation myAnim;
    private ImageView image_pin;
    private TextView done_text;
  //  private MyListener inter;
    private Object MyListener;
    private GPSTracker gpsTracker;
    private String address1;
    private double lat;
    private String get_address;
    private EditText edittext_location;
    private String edittext_location_get;
    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private FusedLocationProviderClient fusedLocationClient;
    private double lattitutte_last = 31.205753;
    private double logigiute_last = 29.924526;
//harsh
    private double latiute;
    private double logitiute;
    int increment = 4;

    SessionManager sessionManager;
    ProgressBar progressBar;
    LinearLayout LL_save;

    String FinalAddress ="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place_address);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        progressBar=findViewById(R.id.progressBar);
        LL_save=findViewById(R.id.LL_save);
        sessionManager = new SessionManager(GooglePlacesAutocompleteActivity.this);

        LL_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // Toast.makeText(GooglePlacesAutocompleteActivity.this, ""+FinalAddress, Toast.LENGTH_SHORT).show();

                Toast.makeText(GooglePlacesAutocompleteActivity.this, "lat : "+latiute+"long : "+logitiute, Toast.LENGTH_SHORT).show();

              if (sessionManager.isNetworkAvailable()) {
                    progressBar.setVisibility(View.VISIBLE);
                    AddAddress(FinalAddress,latiute,logitiute);
                }else {
                    Toast.makeText(GooglePlacesAutocompleteActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }

            }
        });

        fusedLocationClient.getLastLocation().addOnSuccessListener(this,
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lattitutte_last = location.getLatitude();
                            logigiute_last = location.getLongitude();

                            latiute= location.getLatitude();
                            logitiute = location.getLongitude();

                            Log.e(">MyLastLocation>>", "MyLastLocation coordinat :" + lattitutte_last);
                        }
                    }
                });

        GetLocation();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                get_address = null;
            } else {
                get_address = extras.getString("get_address");
            }
        } else {
            get_address = (String) savedInstanceState.getSerializable("get_address");
        }

        image_pin = findViewById(R.id.image_pin);
        gettypedlocation = findViewById(R.id.gettypedlocation);
        edittext_location = findViewById(R.id.edittext_location);
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        done_text = findViewById(R.id.done_text);

        /*gettypedlocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE) {
                    //do Whatever you Want to do
                    Toast.makeText(mContext, ""+"hide", Toast.LENGTH_SHORT).show();
                    hideKeyboardFrom(GooglePlacesAutocompleteActivity.this,gettypedlocation);
                }
                return true;
            }
        });*/

        done_text.setOnClickListener(v -> {
            edittext_location.getText().toString().trim();
           // AppointmentFragment.address.setText(edittext_location.getText().toString().trim());
            //finish();
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(GooglePlacesAutocompleteActivity.this);


       /* if (get_address.equals("map")) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(GooglePlacesAutocompleteActivity.this);
        } else {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getView().setVisibility(View.INVISIBLE);
            image_pin.setVisibility(View.GONE);
            edittext_location.setVisibility(View.VISIBLE);
            gettypedlocation.setVisibility(View.GONE);
        }*/
        autocompleteView();
    }

    private void GetLocation() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(GooglePlacesAutocompleteActivity.this).
                    addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                }

                @Override
                public void onConnectionSuspended(int i) {
                    googleApiClient.connect();
                }
            }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                }
            }).build();

            googleApiClient.connect();

            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10 * 1000);
            mLocationRequest.setFastestInterval(1 * 1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationSettingsRequest.Builder builder1 = new
                    LocationSettingsRequest.Builder();
            builder1.addLocationRequest(mLocationRequest);
            builder1.setAlwaysShow(true);

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
            try {
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();


                latiute= location.getLatitude();
                logitiute = location.getLongitude();

                Log.e("longitude_l>>", "" + longitude);
                Log.e("latitude_l>>", "" + latitude);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this).checkLocationSettings(builder1.build());
            result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                @Override
                public void onComplete(Task<LocationSettingsResponse> task) {
                    try {
                        LocationSettingsResponse response = task.getResult(ApiException.class);
                    } catch (ApiException exception) {
                        switch (exception.getStatusCode()) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                try {
                                    // Cast to a resolvable exception.
                                    ResolvableApiException resolvable = (ResolvableApiException) exception;
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    resolvable.startResolutionForResult(GooglePlacesAutocompleteActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                } catch (ClassCastException e) {
                                    // Ignore, should be an impossible error.
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                                break;
                        }
                    }
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        setCurrentLoc();

                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

  /*  private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            Log.e("Locatoij>>>",""+longitude);
        }
    };*/

    private void autocompleteView() {
        gettypedlocation = findViewById(R.id.gettypedlocation);
        edittext_location_get = edittext_location.getText().toString().trim();
        FinalAddress =edittext_location_get;
        gettypedlocation.requestFocus();
      //  gettypedlocation.setThreshold(THRESHOLD);
       gettypedlocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                    FinalAddress = gettypedlocation.getText().toString();

                    loadData(gettypedlocation.getText().toString());

                } else {
                }
            }
        });

        
    }

    private void loadData(String s) {
        try {
            if (count == 0) {
                List<String> l1 = new ArrayList<>();
                if (s == null) {

                } else {
                    l1.add(s);
                    GeoAutoCompleteAdapter ga = new GeoAutoCompleteAdapter(GooglePlacesAutocompleteActivity.this, l1, "" + latiute, "" + logitiute);
                 //   gettypedlocation.setAdapter(ga);
                    ga.notifyDataSetChanged();
                }
            }
            count++;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // gpsTracker = new GPSTracker(getApplicationContext());
        mMap = googleMap;

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                setCurrentLoc();
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(16).build();
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setCurrentLoc();
            }
        }
    }

    private void setCurrentLoc() {
        gpsTracker = new GPSTracker(GooglePlacesAutocompleteActivity.this);
        double lat = gpsTracker.getLatitude();
        double lon = gpsTracker.getLatitude();

        latiute= gpsTracker.getLatitude();
        logitiute = gpsTracker.getLongitude();

        Log.e("latitutee", "" + lat);
        Log.e("long", "" + lon);

        Log.e("latitutee_last", "" + lattitutte_last);
        Log.e("long", "" + logigiute_last);

        if (lat == 0.0 && lon == 0.0) {
            latiute = lattitutte_last;
            logitiute = logigiute_last;


        } else {
            latiute = gpsTracker.getLatitude();
            logitiute = gpsTracker.getLongitude();
        }

        try {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(new LatLng(latiute, logitiute))));
            Log.e("latiutessssa>", "" + latiute);
            Log.e("logitiutessssa>", "" + logitiute);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latiute, logitiute, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                double lat = mMap.getCameraPosition().target.latitude;
                double lng = mMap.getCameraPosition().target.longitude;
                locationLatLong = new LatLng(lat, lng);

                latiute =  mMap.getCameraPosition().target.latitude;
                logitiute =  mMap.getCameraPosition().target.longitude;

                Geocoder geocoder;
                List<Address> addresses = null;

                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    latiute =  lat;
                    logitiute =  lng;

                    addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                } catch (Exception e) {
                    e.printStackTrace();
                }
                image_pin.startAnimation(myAnim);
                String FinalAddress =""+address1;
                gettypedlocation.setText("" + address1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    public class GeoAutoCompleteAdapter extends BaseAdapter implements Filterable {

        private final LayoutInflater layoutInflater;
      //  private final WebOperations wo;
        private final String lat;
        private final String lon;
        private final Context context;
        private List<String> l2 = new ArrayList<>();
      //  private MyListener inter;

        public GeoAutoCompleteAdapter(Activity context, List<String> l2, String lat, String lon) {
            this.context = context;
            this.l2 = l2;
            this.lat = lat;
            this.lon = lon;
            layoutInflater = LayoutInflater.from(context);
      //      wo = new WebOperations(context);
        }

        @Override
        public int getCount() {

            return l2 == null ? 0 : l2.size();
        }

        @Override
        public Object getItem(int i) {
            return l2.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            view = layoutInflater.inflate(R.layout.geo_search_result, viewGroup, false);
            TextView geo_search_result_text = view.findViewById(R.id.geo_search_result_text);

            Toast.makeText(context,""+ l2.get(i), Toast.LENGTH_SHORT).show();

            try {

                geo_search_result_text.setText(l2.get(i));

                done_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        InputMethodManager inputManager = (InputMethodManager)
                                getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                        if (l2 == null || l2.isEmpty()) {

                            FinalAddress =edittext_location_get;

                           // AppointmentFragment.address.setText(edittext_location_get);
                           // finish();
                            //  Toast.makeText(GooglePlacesAutocompleteActivity.this, "please enter your address", Toast.LENGTH_SHORT).show();

                        } else {
                            gettypedlocation.setText("" + l2.get(i));
                         //   gettypedlocation.dismissDropDown();
                            order_landmarkadd = gettypedlocation.getText().toString();
                          //  AppointmentFragment.address.setText(order_landmarkadd);
                           // finish();
                        //    PreferenceConnector.writeString(GooglePlacesAutocompleteActivity.this, PreferenceConnector.Address_Save1, order_landmarkadd);
                        }
                    }
                });

                geo_search_result_text.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        InputMethodManager inputManager = (InputMethodManager)
                                getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                        if (l2 == null || l2.isEmpty()) {

                        } else {
                                 FinalAddress ="" + l2.get(i);

                            gettypedlocation.setText("" + l2.get(i));
                        //    gettypedlocation.dismissDropDown();
                            order_landmarkadd = gettypedlocation.getText().toString();
                            FinalAddress =order_landmarkadd;
                          //  AppointmentFragment.address.setText(order_landmarkadd);
                           // finish();
                         //   PreferenceConnector.writeString(GooglePlacesAutocompleteActivity.this, PreferenceConnector.Address_Save1, order_landmarkadd);
                        }
                    }
                });

            } catch (Exception e) {

            }

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                      //  wo.setUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyDQhXBxYiOPm-aGspwuKueT3CfBOIY3SJs&input=" + constraint.toString().trim().replaceAll(" ", "+") + "&location=" + lat + "," + lon + "+&radius=20000&types=geocode&sensor=true");
                        String result = null;

                        Log.e("latitute>>up",lat +"latitute>>up"+lon);


                        /*try {
                            result = new MyTask(wo, 3).execute().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }*/
                        parseJson(result);

                        filterResults.values = l2;
                        filterResults.count = l2.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    if (results != null && results.count != 0) {
                        l2 = (List) results.values;
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

        private void parseJson(String result) {
            try {
                l2 = new ArrayList<>();
                JSONObject jk = new JSONObject(result);

                JSONArray predictions = jk.getJSONArray("predictions");
                for (int i = 0; i < predictions.length(); i++) {
                    JSONObject js = predictions.getJSONObject(i);
                    l2.add(js.getString("description"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void AddAddress(String finalAddress, double latiute, double logitiute) {

        String UserId = Preference.get(GooglePlacesAutocompleteActivity.this, Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_address(UserId,"Home",finalAddress,String.valueOf(latiute),String.valueOf(logitiute));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    if (status.equalsIgnoreCase("1")) {

                        progressBar.setVisibility(View.GONE);

                        finish();

                    } else {
                        Toast.makeText(GooglePlacesAutocompleteActivity.this, message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GooglePlacesAutocompleteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}