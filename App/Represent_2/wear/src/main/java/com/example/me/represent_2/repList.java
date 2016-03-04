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

    //repName for phone to watch, to get index
    String repName;

    String intentName = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rep_list);
//        final int row;
        extras = getIntent().getExtras();
//        row = extras.getInt("row");

        repName = extras.getString("rep"); //get the name
        if (repName.equals(null)) {

        }
//        if (repName.equals(null)) {
//
//        }
//        Toast.makeText(this, repName, Toast.LENGTH_SHORT).show();
        int index = java.util.Arrays.asList(dummyNames).indexOf(repName);
        final int row = index;
//        row = index;
//        int index = dummyNames.indexOf(repName);
//        row = 2;

//        if (extras.getInt("row") != 0) {
//            row =
//        } else {
//            row = 0;
//        }



        final GridViewPager pager = (GridViewPager) findViewById(R.id.GridViewPager);
        pager.setAdapter(new myGridViewPagerAdapter(this, dummyNames, dummyImages, dummyParties));
        //initially intentName is the name of first person on the adapter
//        intentName = dummyNames[0];
        //this is to set the current item if row is given
        pager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                pager.setCurrentItem(row, 1);

                pager.getAdapter().notifyDataSetChanged();

                pager.removeOnLayoutChangeListener(this);
            }
        });
//        pager.setCurrentItem(200, 200);
        pager.setOnPageChangeListener(new GridViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, int i1, float v, float v1, int i2, int i3) {

            }

            @Override
            public void onPageSelected(int i, int i1) {
                intentName = dummyNames[i];
//                String name;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
//        pager.setCurrentItem(3, 3);
//        pager.setCurrentItem(row, 1);
//        GridViewPager pager = (GridViewPager) findViewById(R.id.GridViewPager);
//        pager.setAdapter(new myGridPagerAdapter(this, getFragmentManager(), dummyNames, dummyImages));
    }

    public void clickVoteView(View view) {
        Intent voteView = new Intent(this, voteView.class);
        //send stuff to phone from watch
//        Intent watchToPhoneService = new Intent(this, WatchToPhoneService.class);
//        watchToPhoneService.putExtra("rep", intentName);
//        startService(watchToPhoneService);

        //send the name for voteView
        Toast.makeText(this, intentName, Toast.LENGTH_SHORT).show();
        Log.d("THIS IS THE NAME", intentName);
        voteView.putExtra("rep", intentName);
        startActivity(voteView);
    }
}

