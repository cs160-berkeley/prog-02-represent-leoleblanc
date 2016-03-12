package com.example.me.represent_2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Me on 3/2/16.
 */
public class repList extends Activity {

    String[] dummyNames = {"Dianne Feinstein", "Barbara Boxer", "Barbara Lee", "Ami Bera", "Ami Bera 2"};
    int[] dummyImages = {R.drawable.dianne_feinstein_circle, R.drawable.barbara_boxer_circle, R.drawable.barbara_lee_circle,
            R.drawable.ami_bera_circle, R.drawable.ami_bera_circle};
    String[] randNames1 = {"Dianne Feinstein", "Barbara Boxer", "Barbara Lee"};
    String[] randNames2 = {"Barbara Lee", "Dianne Feinstein", "Ami Bera"};
    String[] randNames3 = {"Ami Bera", "Barbara Boxer", "Barbara Lee"};
    String[] randNames4 = {"Barbara Boxer", "Ami Bera", "Dianne Feinstein"};
    String[][] namesArray = {randNames1, randNames2, randNames3, randNames4};

    int[] randImages1 = {R.drawable.dianne_feinstein_circle, R.drawable.barbara_boxer_circle, R.drawable.barbara_lee_circle};
    int[] randImages2 = {R.drawable.barbara_lee_circle, R.drawable.dianne_feinstein_circle, R.drawable.ami_bera_circle};
    int[] randImages3 = {R.drawable.ami_bera_circle, R.drawable.barbara_boxer_circle, R.drawable.barbara_lee_circle};
    int[] randImages4 = {R.drawable.barbara_boxer_circle, R.drawable.ami_bera_circle, R.drawable.dianne_feinstein_circle};
    int[][] imagesArray = {randImages1, randImages2, randImages3, randImages4};

    String[] dummyParties = {"Democrat", "Democrat", "Democrat", "Democrat", "Democrat"};
    String[][] partiesArray = {dummyParties};

    String[] chosenNames;
    int[] chosenImages;
    Bundle extras;
    String[] repNames;

    //repName for phone to watch, to get index
    String repName;
    String toParse;

    String state;
    String county;
    float obamaVotes;
    float romneyVotes;
    String vote;

    String intentName = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rep_list);
        extras = getIntent().getExtras();
        toParse = extras.getString("toParse"); //get the string
        Log.d("To parse", toParse);
        String[] data = toParse.split(";"); //data successfully sent

        repName = data[0]; //repName in first block

        repNames = new String[data[1].split("_").length - 1]; //parse through this

        String[] parseReps = data[1].split("_");


        for (int i = 0; i < repNames.length; i++) { //parse through list of names
            repNames[i] = parseReps[i+1];
        }

        String[] picStrings = new String[data[2].split("_").length - 1];
        String[] parsePicStrings = data[2].split("_"); //parse through this

        for (int i = 0; i < picStrings.length; i++) {
            picStrings[i] = parsePicStrings[i+1];
        }

        String[] actualParties = new String[data[3].split("_").length - 1];
        String[] parseParties = data[3].split("_"); //parse through this

        for (int i = 0; i < actualParties.length; i++) {
            actualParties[i] = parseParties[i+1];
        }

        county = data[4];
        state = data[5];
        obamaVotes = Float.parseFloat(data[6]);
        romneyVotes = Float.parseFloat(data[7]);
        vote = data[8];
        int index = java.util.Arrays.asList(repNames).indexOf(repName);
        final int row = index;



        final GridViewPager pager = (GridViewPager) findViewById(R.id.GridViewPager);
        pager.setAdapter(new myGridViewPagerAdapter(this, repNames, dummyImages, actualParties, picStrings));
        //initially intentName is the name of first person on the adapter
        intentName = repNames[0];
        //this is to set the current item if row is given
        pager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                pager.setCurrentItem(row, 1);

                pager.getAdapter().notifyDataSetChanged();

                pager.removeOnLayoutChangeListener(this);
            }
        });
        pager.setOnPageChangeListener(new GridViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, int i1, float v, float v1, int i2, int i3) {

            }

            @Override
            public void onPageSelected(int i, int i1) {
                intentName = repNames[i];
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void clickVoteView(View view) {
        Intent voteView = new Intent(this, voteView.class);

        //send the name for voteView
        voteView.putExtra("rep", intentName);
        voteView.putExtra("county", county);
        voteView.putExtra("state", state);
        voteView.putExtra("obamaVotes", obamaVotes);
        voteView.putExtra("romneyVotes", romneyVotes);
        voteView.putExtra("vote", vote);
        startActivity(voteView);
    }
}

