package com.example.me.represent_2;

/**
 * Created by Me on 2/27/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Representative_detailed extends Activity {

    Bundle extras;
    ImageView repPic; //to change pic
    TextView repName; //to change name
    TextView repParty; //to change party
    TextView repWebsite; //to change website
    TextView repEmail; //to change email
    TextView repTweets; //to change tweets

    String party;
    String website;
    String email;
    String tweets;
    String endDate;
    String bioguideId;
    int pic;
    String repNameString = "";
    String zip;
    String lat;
    String lon;
    String geoCodeJString;
    ListView lv;
    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_representative);

        repPic = (ImageView) findViewById(R.id.repImage);
        repName = (TextView) findViewById(R.id.repName);
        repParty = (TextView) findViewById(R.id.repParty);

        lv = (ListView) findViewById(R.id.webEmailTweets);

        //get the arguments
        extras = getIntent().getExtras();
        pic = extras.getInt("pic");
        zip = extras.getString("zip");
        lat = extras.getString("lat");
        lon = extras.getString("lon");
        repNameString = extras.getString("rep");
        party = extras.getString("party");
        website = extras.getString("website");
        email = extras.getString("email");
        tweets = extras.getString("twitter");
        endDate = extras.getString("endDate");
        bioguideId = extras.getString("bioguideId");
        geoCodeJString = extras.getString("geo");

        fillInfo();
    }

    public void fillInfo() {
        repName.setText(repNameString);
        //set the image
        String url = "https://theunitedstates.io/images/congress/450x550/" + bioguideId + ".jpg";
        Picasso.with(this).load(url).into(repPic);
//        repPic.setImageResource(pic);
        repParty.setText(party);
        list.add(website);
        list.add(email);
        list.add(tweets);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.web_email_tweets_model, list);
        lv.setAdapter(adapter);

        //trying to communicate with watch
//        Intent phonetoWatchService = new Intent(this, PhoneToWatchService.class);
////        phonetoWatchService.putExtra()
//        phonetoWatchService.putExtra("rep", repNameString);
////        Toast.makeText(this, repNameString, Toast.LENGTH_SHORT).show();
//        startService(phonetoWatchService);
        return;
//        party =
    }

    public void fillInfo(String name, int image) {
        //fill info based on string, and pass in image because why not
        repName.setText(name);
        repPic.setImageResource(image);
        repNameString = name;

        //need to get party, website, email, and recent tweets
        //hard code data now, use api later
        if (name.equals("Dianne Feinstein")) {
            party = "Democrat";
            website = "www.feinstein.senate.gov";
            email = "senator@feinstein.senate.gov";
            tweets = "https://twitter.com/senfeinstein";
        } else if (name.equals("Barbara Boxer")) {
            party = "Democrat";
            website = "www.boxer.senate.gov";
            email = "senator@boxer.senate.gov";
            tweets = "https://twitter.com/senatorboxer";
        } else if (name.equals("Barbara Lee")) {
            party = "Democrat";
            website = "https://lee.house.gov/";
            email = "https://lee.house.gov/contact-the-office/email-me";
            tweets = "https://twitter.com/repbarbaralee";
        } else if (name.equals("Ami Bera")) {
            party = "Democrat";
            website = "bera.house.gov/";
            email = "https://bera.house.gov/connect-with-me/email-ami";
            tweets = "https://twitter.com/repbera";
        } else if (name.equals("Ami Bera 2")) {
            party = "Democrat";
            website = "bera.house.gov/";
            email = "https://bera.house.gov/connect-with-me/email-ami";
            tweets = "https://twitter.com/repbera";
        }
        //now set data
        repParty.setText(party);
//        repWebsite.setText(website);
//        repEmail.setText(email);
//        repTweets.setText(tweets);
        list.add(website);
        list.add(email);
        list.add(tweets);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.web_email_tweets_model, list);
        lv.setAdapter(adapter);

        //trying to communicate with watch
        Intent phonetoWatchService = new Intent(this, PhoneToWatchService.class);
        phonetoWatchService.putExtra("rep", repNameString);
//        Toast.makeText(this, repNameString, Toast.LENGTH_SHORT).show();
        startService(phonetoWatchService);
        return;
    }

    public void backButton(View view) {
        Intent goBack = new Intent(this, Representatives_list.class);
        goBack.putExtra("zip", zip);
        goBack.putExtra("lat", lat);
        goBack.putExtra("lon", lon);
        goBack.putExtra("geo", geoCodeJString);
        startActivity(goBack);
    }

    public void moreInfoButton(View view) {
        Intent moreInfo = new Intent(this, Representative_moreInfo.class);
        moreInfo.putExtra("rep", repNameString); //transfer name
        moreInfo.putExtra("pic", pic); //transfer picture
        moreInfo.putExtra("party", party); //transer party
        moreInfo.putExtra("email", email);
        moreInfo.putExtra("twitter", tweets);
        moreInfo.putExtra("website", website);
        moreInfo.putExtra("zip", zip);
        moreInfo.putExtra("lat", lat);
        moreInfo.putExtra("lon", lon);
        moreInfo.putExtra("endDate", endDate);
        moreInfo.putExtra("bioguideId", bioguideId);

        startActivity(moreInfo);
    }

}
