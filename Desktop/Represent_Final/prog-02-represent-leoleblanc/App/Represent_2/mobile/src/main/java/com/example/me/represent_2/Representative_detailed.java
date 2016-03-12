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

import java.util.ArrayList;

public class Representative_detailed extends Activity {

    //dummy name, image (passed in from earlier intent)
    //get arguments passed in from previous intent that led to this screen
    //Intent intent = getIntent();

    Bundle extras;
    ImageView repPic; //to change pic
    TextView repName; //to change name
    TextView repParty; //to change party
    TextView repWebsite; //to change website
    TextView repEmail; //to change email
    TextView repTweets; //to change tweets
    String party = "";
    String website = "";
    String email = "";
    String tweets = "";
    int pic = 0;
    String repNameString = "";
    String zip;
    ListView lv;
    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_representative);

        repPic = (ImageView) findViewById(R.id.repImage);
        repName = (TextView) findViewById(R.id.repName);
        repParty = (TextView) findViewById(R.id.repParty);
//        repWebsite = (TextView) findViewById(R.id.repWebsite);
//        repEmail = (TextView) findViewById(R.id.repEmail);
//        repTweets = (TextView) findViewById(R.id.repTweets);
        lv = (ListView) findViewById(R.id.webEmailTweets);

        //get the arguments
        extras = getIntent().getExtras();
        pic = extras.getInt("pic");
        fillInfo(extras.getString("rep"), pic);
        zip = extras.getString("zip");
        //String name = intent.getStringExtra("rep");
        //int image = extras.getInt("pic");
        //repPic.setImageResource(extras.getInt("pic"));
        //repName.setText(extras.getString("rep"));
        //repName = extras.getString("rep");
        //repPic = extras.getInt("pic");
        //fillInfo(name);

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
        startActivity(goBack);
    }

    public void moreInfoButton(View view) {
        Intent moreInfo = new Intent(this, Representative_moreInfo.class);
        moreInfo.putExtra("rep", repNameString); //transfer name
        moreInfo.putExtra("pic", pic); //transfer picture
        moreInfo.putExtra("party", party); //transer party

        startActivity(moreInfo);
    }

}
