package com.example.me.represent_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        fillBasicInfo();
        fillCommittees();
        fillBills();
    }

    public void fillBasicInfo() {
        //right now values are hardcoded, but later api will give actual data
        name = extras.getString("rep");
        pic = extras.getInt("pic");
        repName.setText(name);
        repPic.setImageResource(pic);
        repParty.setText(extras.getString("party"));
        if (name.equals("Dianne Feinstein")) {
            repEndTerm.setText("2019");
        } else if (name.equals("Barbara Boxer")) {
            repEndTerm.setText("2016");
        } else if (name.equals("Barbara Lee")) {
            repEndTerm.setText("2015");
        } else if (name.equals("Ami Bera")) {
            repEndTerm.setText("2016");
        } else if (name.equals("Ami Bera 2")) {
            repEndTerm.setText("2016");
        }
        return;
    }

    public void clickBack(View view) {
        Intent goBack =  new Intent(this, Representative_detailed.class);
        goBack.putExtra("rep", name);
        goBack.putExtra("pic", pic);
        startActivity(goBack);
    }

    public void fillCommittees() {
        List<String> committees = new ArrayList<>();
        if (name.equals("Dianne Feinstein")) {
            committees.add("Intelligence");
            committees.add("Appropriations");
        } else if (name.equals("Barbara Boxer")) {
            committees.add("Ethics");
            committees.add("Environment and Public Works");
        } else if (name.equals("Barbara Lee")) {
            committees.add("Appropriations");
            committees.add("The Budget");
        } else if (name.equals("Ami Bera")) {
            committees.add("Foreign Affairs");
            committees.add("Science, Space, and Technology");
        } else if (name.equals("Ami Bera 2")) {
            committees.add("Foreign Affairs");
            committees.add("Science, Space, and Technology");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_model, committees);
        repCommitteeList.setAdapter(adapter);
        return;
    }

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

}
