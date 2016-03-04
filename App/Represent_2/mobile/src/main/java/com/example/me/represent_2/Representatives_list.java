package com.example.me.represent_2;

/**
 * Created by Me on 2/27/16.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Random;

public class Representatives_list extends Activity {

    //dummy data, names and images
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

    String[] chosenNames;
    int[] chosenImages;

    String chosenRep;
    int repPic;
    boolean random;

    ListView lv;
    //for actual data, to be set by api either here in another function in this class
    Bundle extras;
    String zip;
    String[] names = {};
    int[] images = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.representatives_list);

        lv = (ListView) findViewById(R.id.listView);
        extras = getIntent().getExtras();
        zip = extras.getString("zip"); //get zip code

        //get which names and images to use
        if (random) {
            int random = (new Random()).nextInt(3 - 0 + 1) + 0;
            chosenNames = namesArray[random];
            chosenImages = imagesArray[random];
        } else {
            chosenNames = dummyNames;
            chosenImages = dummyImages;
        }
//        int random = (new Random()).nextInt(3 - 0 + 1) + 0;
//        chosenNames = namesArray[random];
//        chosenImages = imagesArray[random];

        //Create adapter object
        Adapter adapter = new Adapter(this, chosenNames, chosenImages);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                Toast.makeText(getApplicationContext(), chosenNames[pos], Toast.LENGTH_SHORT).show();

                chosenRep = chosenNames[pos];
                repPic = chosenImages[pos];
                Intent goRep = new Intent(Representatives_list.this, Representative_detailed.class);
                goRep.putExtra("rep", chosenRep); //passes in string, string (name, value)
                goRep.putExtra("pic", repPic); //passes in string, int (name, value)
                goRep.putExtra("zip", zip); //passes in zip code to avoid crash, since zip needed on back call
                startActivity(goRep);
            }
        });
    }

    public void clickBack(View view) {
        Intent goBack = new Intent(this, MainScreen.class);
        startActivity(goBack);
    }
}
