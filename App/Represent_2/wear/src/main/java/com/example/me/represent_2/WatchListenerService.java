package com.example.me.represent_2;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by Me on 3/3/16.
 */
public class WatchListenerService extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    String repName = "";

//    private static final String FRED_FEED = "/Dianne Feinstein";
//    private static final String LEXY_FEED = "/Barbara Boxer";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());

        //test code
        //this has the message we sent, the "repName' from the PhoneToWatchService
        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        repName = value;

        Intent intent = new Intent(this, repList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //set repName
        intent.putExtra("rep", repName);
        Log.d("T", "about to start watch MainActivity with CAT_NAME: Fred");
        startActivity(intent);

//        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//        Intent intent = new Intent(this, MainScreen.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //new flag for new activity from service
//        intent.putExtra("rep", repName);
//        Log.d("T", "about to start watch MainScreen with repName: " + repName);
//        startActivity(intent);


        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

//        if( messageEvent.getPath().equalsIgnoreCase( FRED_FEED ) ) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, repList.class );
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("CAT_NAME", "Fred");
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Fred");
//            startActivity(intent);
//        } else if (messageEvent.getPath().equalsIgnoreCase( LEXY_FEED )) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, repList.class );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("CAT_NAME", "Lexy");
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Lexy");
//            startActivity(intent);
//        } else {
//            super.onMessageReceived( messageEvent );
//        }

    }
}
