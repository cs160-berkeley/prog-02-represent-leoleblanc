package com.example.me.represent_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Me on 3/3/16.
 */
public class voteView extends Activity {

    Bundle extras;
    TextView whichVote;
    TextView whichState;
    TextView whichCounty;
    TextView obamaPercent;
    TextView romneyPercent;
    String name;
    String county;
    String state;
    float obamaVotes;
    float romneyVotes;
    String vote;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_view);

        whichVote = (TextView) findViewById(R.id.whichVote);
        whichState = (TextView) findViewById(R.id.whichState);
        whichCounty = (TextView) findViewById(R.id.whichCounty);
        obamaPercent = (TextView) findViewById(R.id.obamaPercent);
        romneyPercent = (TextView) findViewById(R.id.romneyPercent);

        extras = getIntent().getExtras();
        name = extras.getString("rep");
        county = extras.getString("county");
        state = extras.getString("state");
        obamaVotes = extras.getFloat("obamaVotes");
        romneyVotes = extras.getFloat("romneyVotes");
        vote = extras.getString("vote");

//        name = getIntent().getExtras().getString("rep");
        fillInfo(name);
        Intent watchToPhoneService = new Intent(this, WatchToPhoneService.class);
        watchToPhoneService.putExtra("rep", name);
        startService(watchToPhoneService);

    }

    public void fillInfo(String name) {
//        String vote = "";
//        String state = "";
//        String county = "";
//        String obamaperc = "";
//        String romneyperc = "";
//        if (name.equals("Dianne Feinstein")) {
//            vote = "Obama";
//            state = "CA";
//            county = "Antelope";
//            obamaperc = "64";
//            romneyperc = "36";
//            //do stuff
//        } else if (name.equals("Barbara Boxer")) {
//            vote = "Obama";
//            state = "CA";
//            county = "Marin";
//            obamaperc = "55";
//            romneyperc = "45";
//            //do stuff
//        } else if (name.equals("Barbara Lee")) {
//            vote = "Obama";
//            state = "CA";
//            county = "Oakland";
//            obamaperc = "70";
//            romneyperc = "30";
//            //do stuff
//        } else if (name.equals("Ami Bera")) {
//            vote = "Obama";
//            state = "CA";
//            county = "Richmond";
//            obamaperc = "80";
//            romneyperc = "20";
//            //do stuff
//        } else if (name.equals("Ami Bera 2")) {
//            vote = "Obama";
//            state = "CA";
//            county = "Richmond";
//            obamaperc = "80";
//            romneyperc = "20";
//            //do stuff
//        }
        whichVote.setText(vote);
        whichState.setText(state);
        whichCounty.setText(county);
        obamaPercent.setText(String.valueOf(obamaVotes));
        romneyPercent.setText(String.valueOf(romneyVotes));
    }

}
