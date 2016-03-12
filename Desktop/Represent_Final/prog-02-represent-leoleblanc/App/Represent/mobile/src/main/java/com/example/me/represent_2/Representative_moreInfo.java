package com.example.me.represent_2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Me on 2/29/16.
 */

public class Representative_moreInfo extends Activity {

    Bundle extras;
    TextView repName;
    ImageView repPic;
    TextView repParty;
    TextView repEndTerm;
    ListView repCommitteeList;
    ListView repBillList;
    String name;
    int pic;

    String email;
    String twitter;
    String website;
    String party;
    String endDate;
    String zip;
    String lat;
    String lon;
    String bioguideId;
    String committeesURLString;
    String billsURLString;
    String jString;
    //ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info_representative);

        extras = getIntent().getExtras();
        repName = (TextView) findViewById(R.id.repName);
        repPic = (ImageView) findViewById(R.id.repImage);
        repParty = (TextView) findViewById(R.id.repParty);
        repEndTerm = (TextView) findViewById(R.id.repEndTerm);
        repCommitteeList = (ListView) findViewById(R.id.repCommitteeList);
        repBillList = (ListView) findViewById(R.id.repBillList);

        email = extras.getString("email");
        twitter = extras.getString("twitter");
        website = extras.getString("website");
        party = extras.getString("party");
        zip = extras.getString("zip");
        lat = extras.getString("lat");
        lon = extras.getString("lon");
        endDate = extras.getString("endDate");
//        repEndTerm = extras.getString("endDate");
        bioguideId = extras.getString("bioguideId");

        fillBasicInfo();

        committeesURLString = "http://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguideId + "&apikey=b6c8f2d577cf4611990a8751ac292da2";

        RetrieveFeedTask task = (RetrieveFeedTask) new RetrieveFeedTask().execute(committeesURLString);
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        fillCommittees(jString);

        billsURLString = "http://congress.api.sunlightfoundation.com/bills/search?sponsor_id=" + bioguideId + "&apikey=b6c8f2d577cf4611990a8751ac292da2";
        task = (RetrieveFeedTask) new RetrieveFeedTask().execute(billsURLString);
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        fillBills(jString);
//        fillCommittees();
//        Representatives_list.RetrieveFeedTask task = new Representatives_list.RetrieveFeedTask().execute(committeesURLString);







//        fillBasicInfo();
//        fillCommittees();
//        fillBills();
    }

    public void fillBasicInfo() {
        String url = "https://theunitedstates.io/images/congress/450x550/" + bioguideId + ".jpg";
        Picasso.with(this).load(url).into(repPic);
        name = extras.getString("rep");
        pic = extras.getInt("pic");
        repName.setText(name);
//        repPic.setImageResource(pic);
        repParty.setText(extras.getString("party"));
        repEndTerm.setText(endDate);
        return;
    }

    public void fillCommittees(String jString) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(jString).nextValue();
            JSONArray array = object.getJSONArray("results"); //1 array, repNum objects
            List<String> committees = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject subObject = array.getJSONObject(i);
                committees.add(subObject.getString("name"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_model, committees);
            repCommitteeList.setAdapter(adapter);
            return;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

    }

    public void fillBills(String jString) {
        try {
            JSONObject object = (JSONObject) new JSONTokener(jString).nextValue();
            JSONArray array = object.getJSONArray("results"); //1 array, repNum objects
            List<String> bills = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject subObject = array.getJSONObject(i);
                String billName = subObject.getString("short_title");
                Log.d("billName is:", billName);
                if (!billName.equals("null")) {
                    bills.add(billName);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_model, bills);
            repBillList.setAdapter(adapter);
            return;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
//
    }

    public void clickBack(View view) {
        Intent goBack =  new Intent(this, Representative_detailed.class);
        goBack.putExtra("rep", name);
        goBack.putExtra("pic", pic);
        goBack.putExtra("email", email);
        goBack.putExtra("twitter", twitter);
        goBack.putExtra("website", website);
        goBack.putExtra("party", party);
        goBack.putExtra("zip", zip);
        goBack.putExtra("lat", lat);
        goBack.putExtra("lon", lon);
        goBack.putExtra("endDate", endDate);
        goBack.putExtra("bioguideId", bioguideId);
        startActivity(goBack);
    }

//    public void fillCommittees() {
//        List<String> committees = new ArrayList<>();
//        if (name.equals("Dianne Feinstein")) {
//            committees.add("Intelligence");
//            committees.add("Appropriations");
//        } else if (name.equals("Barbara Boxer")) {
//            committees.add("Ethics");
//            committees.add("Environment and Public Works");
//        } else if (name.equals("Barbara Lee")) {
//            committees.add("Appropriations");
//            committees.add("The Budget");
//        } else if (name.equals("Ami Bera")) {
//            committees.add("Foreign Affairs");
//            committees.add("Science, Space, and Technology");
//        } else if (name.equals("Ami Bera 2")) {
//            committees.add("Foreign Affairs");
//            committees.add("Science, Space, and Technology");
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_model, committees);
//        repCommitteeList.setAdapter(adapter);
//        return;
//    }

    public void fillBills() {
        List<String> bills = new ArrayList<>();
        if (name.equals("Dianne Feinstein")) {
            bills.add("S.2568");
            bills.add("S.2552");
        } else if (name.equals("Barbara Boxer")) {
            bills.add("S.2487");
            bills.add("S.2412");
        } else if (name.equals("Barbara Lee")) {
            bills.add("H.R.257");
            bills.add("H.R.258");
        } else if (name.equals("Ami Bera")) {
            bills.add("H.R.2457");
            bills.add("H.R.2484");
        } else if (name.equals("Ami Bera 2")) {
            bills.add("H.R.2457");
            bills.add("H.R.2484");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_model, bills);
        repBillList.setAdapter(adapter);
        return;
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
