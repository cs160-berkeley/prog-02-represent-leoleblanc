package com.example.me.represent_2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {

    RadioGroup buttonGroup;
    RadioButton codeButton;
    RadioButton currButton;
    boolean zipTrue;
    boolean currTrue;
    EditText zipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonGroup = (RadioGroup) findViewById(R.id.buttonGroup);
        codeButton = (RadioButton) findViewById(R.id.codeButton);
        currButton = (RadioButton) findViewById(R.id.currButton);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void clickGo(View view) {
        //pass in zip code to next screen
        Intent intent = new Intent(this, Representatives_list.class);
        String zip;
        if (zipTrue) {
            zip = ((EditText) findViewById(R.id.zipCode)).toString();
        } else {
            //get current zipCode (for now, set to "94704")
            zip = "94704";
        }
        intent.putExtra("zip", zip);

        if (buttonGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "one button must be checked", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(intent);
        }
//        startActivity(intent);
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
}
