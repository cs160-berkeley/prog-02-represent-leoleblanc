package com.example.me.represent_2;

/**
 * Created by Me on 2/27/16.
 */
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Representatives_list extends Activity {

    //dummy data, names and images
    String[] dummyNames = {"Dianne Feinstein", "Barbara Boxer", "Barbara Lee", "Ami Bera", "Ami Bera 2"};
    int[] dummyImages = {R.drawable.dianne_feinstein_circle, R.drawable.barbara_boxer_circle, R.drawable.barbara_lee_circle,
            R.drawable.ami_bera_circle, R.drawable.ami_bera_circle};

    String chosenRep;
    int repPic;
    boolean random;

    ListView lv;
    //for actual data, to be set by api either here in another function in this class
    Bundle extras;
//    String zip;
    String[] names = {};
    int[] images = {};

    URL url;
    HttpURLConnection urlConnection;
//    InputStream in;
    BufferedReader in;
    String lat;
    String lon;
    String zip;
    String urlArg;
    String jString;
    String[] actualNames;
    String[] actualParties;
    String[] actualWebsites;
    String[] actualEmails;
    String[] actualTwitters;
    String[] actualEndDates;
    String[] bioGuideIds;

    //these to be filled in
    int[] actualPics; //placeholder ami bera for now
    String[][] actualCommittees;
    String[][] actualBills;

    String repParty;
    String repWebsite;
    String repEmail;
    String repTwitter;
    String repEndDate;
    String[] repCommittees;
    String[] repBills;
    String[] picStrings;
    final String GapiKey = "AIzaSyD0BNCHYeHSIYH78BC7Ksh_C0UspnYcFzQ";
    String county;
    String geoCodeJstring;
    String state;
    int obamaVotes;
    int romneyVotes;
    String majority;
//    String chosenRepWebsite;



    //for actual data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.representatives_list);

        extras = getIntent().getExtras();
        zip = extras.getString("zip");
        lat = extras.getString("lat");
        lon = extras.getString("lon");
        geoCodeJstring = extras.getString("geo");
        urlArg = "http://congress.api.sunlightfoundation.com/legislators/locate?";


        //construct the URL
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy); //this is for input stream not giving exception

        if (zip != null) {
            urlArg = urlArg + "zip=" + zip + "&apikey=b6c8f2d577cf4611990a8751ac292da2";
        } else {
            urlArg = urlArg + "latitude=" + lat + "&longitude=" + lon + "&apikey=b6c8f2d577cf4611990a8751ac292da2";
        }
        //this task gets names, parties, emails, websites, term end dates, and twitter names
        RetrieveFeedTask task = (RetrieveFeedTask) new RetrieveFeedTask().execute(urlArg);
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        fillInfo(jString);

//        fillPics(actualNames); //placeholder for now

        fillPicStrings(bioGuideIds); //fill out picUrls
//        if (zip != null) {
//            urlArg = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zip + "&region=us&key=" + GapiKey;
//        } else {
//            urlArg = "https://maps.googleapis.com/maps/api/geiocode/json?latlng=" + lat + "," + lon + "&key=" + GapiKey;
//        }
//
//        task = (RetrieveFeedTask) new RetrieveFeedTask().execute(urlArg);
//
//        try {
//            task.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        getCounty(geoCodeJstring);
        if (county == null) {
            county = "N/A";
        }
//        Log.d("county is", county);

        //now, parse JSON file and send results to watch

        String countyState = county + ", " + state;

        String electionJson = loadJSONFromAsset();
        JSONObject object;
        try {
            object = new JSONObject(electionJson);
            JSONObject relevant = object.getJSONObject(countyState);
            obamaVotes = relevant.getInt("obama");
            romneyVotes = relevant.getInt("romney");
        } catch (JSONException e) {
            obamaVotes = 0;
            romneyVotes = 0;
//            e.printStackTrace();
        }

        if (obamaVotes == 0 && romneyVotes == 0) {
            majority = "N/A";
        } else if (obamaVotes > romneyVotes) {
            majority = "Obama";
        } else {
            majority = "Romney";
        }


//
        lv = (ListView) findViewById(R.id.listView);

        //Create adapter object
        //get picture urls


        Adapter adapter = new Adapter(this, actualNames, actualPics, picStrings);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                Toast.makeText(getApplicationContext(), actualNames[pos], Toast.LENGTH_SHORT).show();

                chosenRep = actualNames[pos];
                repParty = actualParties[pos];
                repWebsite = actualWebsites[pos];
                repEmail = actualEmails[pos];
                repTwitter = actualTwitters[pos];
                repEndDate = actualEndDates[pos];


                Intent goRep = new Intent(Representatives_list.this, Representative_detailed.class);
                goRep.putExtra("rep", chosenRep); //passes in string, string (name, value)
                goRep.putExtra("pic", repPic); //passes in string, int (name, value)
                goRep.putExtra("zip", zip); //passes in zip code to avoid crash, since zip needed on back call
                goRep.putExtra("lat", lat); //to avoid no info
                goRep.putExtra("lon", lon); //to avoid no info
                goRep.putExtra("party", repParty);
                goRep.putExtra("website", repWebsite);
                goRep.putExtra("email", repEmail);
                goRep.putExtra("twitter", repTwitter);
                goRep.putExtra("endDate", repEndDate);
                goRep.putExtra("bioguideId", bioGuideIds[pos]);
                goRep.putExtra("geo", geoCodeJstring);

                //now for service, to load on the watch
                Intent phonetoWatchService = new Intent(Representatives_list.this, PhoneToWatchService.class);
                //construct big array to send as message to watch
                String toSend = chosenRep + ";";
//                Log.d("array length", String.valueOf(actualNames.length));
//                //append the list of names
                for (int i = 0; i < actualNames.length; i++) {
                    toSend = toSend.concat("_" + actualNames[i]);
                }
                toSend = toSend.concat(";");
                for (int i = 0; i < picStrings.length; i++) {
                    toSend = toSend.concat("_" + picStrings[i]);
                }
                toSend = toSend.concat(";");
                for (int i = 0; i < actualParties.length; i++) {
                    toSend = toSend.concat("_" + actualParties[i]);
                }
                toSend = toSend.concat(";");
                toSend = toSend.concat(county); //add the county
                toSend = toSend.concat(";");
                toSend = toSend.concat(state); //add the state
                toSend = toSend.concat(";");
                toSend = toSend.concat(String.valueOf(obamaVotes));
                toSend = toSend.concat(";");
                toSend = toSend.concat(String.valueOf(romneyVotes));
                toSend = toSend.concat(";");
                toSend = toSend.concat(majority);
                Log.d("The string to send", toSend);
                phonetoWatchService.putExtra("toSend", toSend);
                startService(phonetoWatchService);

                startActivity(goRep);
            }
        });
    }

    protected void fillInfo(String jsonString){
        try {
            JSONObject object = (JSONObject) new JSONTokener(jsonString).nextValue();
            JSONArray array = object.getJSONArray("results"); //1 array, repNum objects
            actualNames = new String[array.length()];
            actualParties = new String[array.length()];
            actualEmails = new String[array.length()];
            actualWebsites = new String[array.length()];
            actualEndDates = new String[array.length()];
            actualTwitters = new String[array.length()];
            bioGuideIds = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONObject subObject = array.getJSONObject(i);
                actualNames[i] = subObject.getString("first_name") + " " + subObject.getString("last_name");
                String party = subObject.getString("party");
                actualParties[i] = party;
                if (party.equals("D")) {
                    actualParties[i] = party.concat("emocrat");
                } else if (party.equals("R")){
                    actualParties[i] = party.concat("epublican");
                } else if (party.equals("I")) {
                    actualParties[i] = party.concat("ndependent");
                }
                actualEmails[i] = subObject.getString("oc_email");
                actualWebsites[i] = subObject.getString("website");
                actualEndDates[i] = subObject.getString("term_end");
                actualTwitters[i] = subObject.getString("twitter_id");
                bioGuideIds[i] = subObject.getString("bioguide_id");
                state = subObject.getString("state");
            }

        } catch (Exception e) {
//            Toast.makeText(this, "Errored somewhere", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    protected void getCounty(String jString) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(jString).nextValue();
            JSONArray array = object.getJSONArray("results"); //1 array, a lot of components
            JSONObject actualObject = array.getJSONObject(0); //this is the list of stuff we need
            JSONArray array2 = actualObject.getJSONArray("address_components");
            for (int i = 0; i < array2.length(); i++) {
                JSONObject subObject = array2.getJSONObject(i);
                JSONArray insideArray = subObject.getJSONArray("types");
                for (int j = 0; j < insideArray.length(); j++) {
                    String type = insideArray.getString(0);
                    if (type.equals("administrative_area_level_2")) {
                        county = array2.getJSONObject(i).getString("long_name");
                        return;
                    }
//                    JSONObject innerObject = insideArray.get
                }
//                if (insideArray[0].equals("administrative_area_level_2"));
            }
        } catch (Exception e) {
//            Toast.makeText(this, "Errored somewhere", Toast.LENGTH_SHORT).show();
        }
    }

    protected void fillPicStrings(String[] ids) {
        picStrings = new String[ids.length];
        for (int i = 0; i < picStrings.length; i++) {
            String url = "https://theunitedstates.io/images/congress/225x275/" + ids[i] + ".jpg";
            picStrings[i] = url;
        }
    }


    public void clickBack(View view) {
        Intent goBack = new Intent(this, MainScreen.class);
        startActivity(goBack);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("voteview.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(urlArg);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                jString = stringBuilder.toString();
                urlConnection.disconnect();
                return jString;
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }
}
