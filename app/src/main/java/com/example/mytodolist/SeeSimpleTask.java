package com.example.mytodolist;

import android.app.Activity;

import android.os.Bundle;

import android.widget.TextView;

public class SeeSimpleTask extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);

        //Just display the title, the date, the hour, the tag and the description

        Bundle bundle = getIntent().getExtras();

        TextView tv1 = findViewById(R.id.editText11);
        TextView tv2 = findViewById(R.id.editText);
        TextView tv3 = findViewById(R.id.editText2);
        TextView tv4 = findViewById(R.id.editText3);
        TextView tv5 = findViewById(R.id.editText10);

        tv1.setText(bundle.getString("titre"));
        tv2.setText(bundle.getString("tag"));
        tv3.setText(bundle.getString("date"));
        tv4.setText(bundle.getString("heure"));
        tv5.setText(bundle.getString("des"));


    }

}
