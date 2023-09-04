package com.example.mytodolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateActivity extends Activity {

    EditText titre;
    EditText tag;
    EditText des;

    String titreS;
    String tagS;
    String heureS;
    String dateS;
    String desS;

    boolean vf;

    EditText dateE;
    DatePickerDialog datePickerDialog;

    EditText heureE;
    TimePickerDialog picker;

    // Activity called to create a new task

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activite);

        TextView tv1 = findViewById(R.id.textView);
        tv1.setText(R.string.new_t);

        TextView tv2 = findViewById(R.id.textView5);
        tv2.setText(R.string.titre);

        TextView tv3 = findViewById(R.id.textView6);
        tv3.setText(R.string.tag);

        TextView tv4 = findViewById(R.id.textView7);
        tv4.setText(R.string.date_hour);

        TextView tv5 = findViewById(R.id.textView8);
        tv5.setText(R.string.description);

        Button val = findViewById(R.id.button);
        val.setText(R.string.validate);

        //--- To make a date picker -------------------------------------------------------------------------

        dateE = findViewById(R.id.editText7);

        dateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CreateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String m ;
                                String y ;
                                if(monthOfYear <= 9){y = "0"+(monthOfYear+1);}else{y = ""+(monthOfYear+1);}
                                if(dayOfMonth <= 9){m = "0"+dayOfMonth;}else{m = ""+dayOfMonth;}
                                dateE.setText(m + "/" + (y) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //--- To make a hour picker -------------------------------------------------------------------------

        heureE = findViewById(R.id.editText8);

        heureE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(CreateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String h ;
                                String min;
                                if(sHour <= 9){h = "0"+sHour;}else{h = ""+sHour;}
                                if(sMinute <= 9){min = "0"+sMinute;}else{min = ""+sMinute;}
                                heureE.setText(h + ":" + min);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        //-----------------------------------------------------------------------------------------


        titre = findViewById(R.id.editText4);
        tag = findViewById(R.id.editText6);
        des = findViewById(R.id.editText9);
        vf = false;

        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titreS = titre.getText().toString();
                tagS = tag.getText().toString();
                heureS = heureE.getText().toString();
                dateS = dateE.getText().toString();
                desS = des.getText().toString();

                if(!titreS.equals("") && !dateS.equals("")){
                    vf = true;
                    finish();
                }else{
                    TextView tv = findViewById(R.id.textView3);
                    tv.setText("Veuillez Renseigner au minimum le titre et la date");
                }

            }
        });

    }

    public void finish(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("vf",vf);
        returnIntent.putExtra("titre", titreS);
        returnIntent.putExtra("date", dateS);
        returnIntent.putExtra("tag", tagS);
        returnIntent.putExtra("heure", heureS);
        returnIntent.putExtra("des", desS);
        setResult(RESULT_OK, returnIntent);
        super.finish();
    }
}


