package com.example.me.represent_2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.fabric.sdk.android.Fabric;

public class MainScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "vdCphOuue2YWbRoKbREN1qbX9";
    private static final String TWITTER_SECRET = "EKBmuSv1ILnwx2hZGAdIuUFW7fjwU7zoWqx6zSU0892dvmEPLU";
    private GoogleApiClient mGoogleApiClient;


    RadioGroup buttonGroup;
    RadioButton zipButton;
    RadioButton currButton;
    String currLocation;
    boolean zipTrue;
    boolean currTrue;
    EditText zipCode;
    Location mLocation;
    String latitude;
    String longitude;
    String county;
    String jString;
    String geoCodeJstring;
    String latt;
    String lonn;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)  // used for data layer API
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

//        Log.d("HERE", String.valueOf(mGoogleApiClient));
//
//        mGoogleApiClient.connect();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        Log.d("latitude", String.valueOf(mLocation.getLatitude()));
//        Log.d("longitude", String.valueOf(mLocation.getLongitude()));


        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonGroup = (RadioGroup) findViewById(R.id.buttonGroup);
        zipButton = (RadioButton) findViewById(R.id.zipButton);
        currButton = (RadioButton) findViewById(R.id.currButton);
        zipCode = (EditText) findViewById(R.id.zipCode);

    }

    public void clickGo(View view) {
        //pass in zip code to next screen
        Intent intent = new Intent(this, Representatives_list.class);
        String query;
        String zip;
//        String latt;
//        String lonn;


        if (zipButton.isChecked()) {
            zip = ((EditText) findViewById(R.id.zipCode)).getText().toString();
            if (isValid(zip)) { //need more comprehensive check

                getCountyByZip(zip);
                intent.putExtra("zip", zip);
//                convertZiptoLatLon(zip);
                intent.putExtra("geo", geoCodeJstring);
//                intent.putExtra("lat", latt);
//                intent.putExtra("lon", lonn);
                //now parse the string
//                setLatLon(jString);
//                intent.putExtra("lat", latt);

                startActivity(intent);
            } else {
                Toast.makeText(this, "invalid zip", Toast.LENGTH_SHORT).show();
            }
        } else if (currButton.isChecked()) {
            getCountyByLatLon(latitude, longitude);

            mGoogleApiClient.disconnect();

            intent.putExtra("lat", latitude);
            intent.putExtra("lon", longitude);
            intent.putExtra("geo", geoCodeJstring);

            startActivity(intent);
        } else {
            Toast.makeText(this, "one button must be checked", Toast.LENGTH_SHORT).show();
        }
    }

    private void convertZiptoLatLon(String zip) {
        String apiKey = "TtwtfVZVp4wc8a9J7zLbQOXglITVoBvi42MkXLfmPMzYWJI3itf9icpDVjzkU5DY";
        String appKey = "FHiFS4y3VGSQO6czDzokqaBlKDbbR2JwJ2cB6KF1ryRgF4PiDdD4oeq9SCkKE3hn";
        String requestURL = "https://www.zipcodeapi.com/rest/" + appKey + "/info.json/" + zip + "/degrees";
        RetrieveFeedTask task = (RetrieveFeedTask) new RetrieveFeedTask().execute(requestURL);
        try {
            task.get();
        } catch (Exception e) {
            return;
        }
        try {
            JSONObject object = (JSONObject) new JSONTokener(jString).nextValue();
            latt = object.getString("lat");
            lonn = object.getString("lng");
        } catch (Exception e) {
            return;
        }

        return;
    }

    private void getCountyByZip(String zip) {
        String apiKey = "AIzaSyD0BNCHYeHSIYH78BC7Ksh_C0UspnYcFzQ";
        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zip + "&region=us&key=" + apiKey;
        RetrieveFeedTask task = (RetrieveFeedTask) new RetrieveFeedTask().execute(urlString);
        try {
            task.get();
        } catch (Exception e) {
            return;
        }
        return;
    }

    private void getCountyByLatLon(String latitude, String longitude) {
        String apiKey = "AIzaSyD0BNCHYeHSIYH78BC7Ksh_C0UspnYcFzQ";
        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=" + apiKey;
        RetrieveFeedTask task = (RetrieveFeedTask) new RetrieveFeedTask().execute(urlString);
        try {
            task.get();
        } catch (Exception e) {
            return;
        }
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
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
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            latitude = String.valueOf(mLocation.getLatitude());
            longitude = String.valueOf(mLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private boolean isValid(String s) {
        if (s.length() != 5) {
            return false;
        }
        for (char c: s.toCharArray()) {
//            Toast.makeText(this, c, Toast.LENGTH_SHORT).show();
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                geoCodeJstring = stringBuilder.toString();
                urlConnection.disconnect();
                return geoCodeJstring;
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }
}