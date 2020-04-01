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
import android.widget.TimePicker;

import java.util.Calendar;

public class SeeTask extends Activity {

    String titre;
    String tag;
    String date;
    String heure;
    String des;
    String id;

    EditText editTitre;
    EditText editTag;
    EditText editDate;
    EditText editHeure;
    EditText editDes;

    DatePickerDialog datePickerDialog;
    TimePickerDialog picker;

    boolean save = false;
    boolean supp = false;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.une_activite);

        this.save = false;
        this.supp = false;

        //--- Set texte about the differents TextView and Buttons ----------------------------------

        Button save = findViewById(R.id.button3);
        save.setText(R.string.save);

        Button supp = findViewById(R.id.button4);
        supp.setText(R.string.supp);

        Bundle bundle = getIntent().getExtras();
        titre = bundle.getString("titre");
        date = bundle.getString("date");
        heure = bundle.getString("heure");
        des = bundle.getString("des");
        tag = bundle.getString("tag");
        id = bundle.getString("id");

        //--- Pour modifier l'heure et la date ----------------------------------------------------

        editDate = findViewById(R.id.editText2);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(SeeTask.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String m ;
                                String y ;
                                if(monthOfYear <= 9){y = "0"+(monthOfYear+1);}else{y = ""+(monthOfYear+1);}
                                if(dayOfMonth <= 9){m = "0"+dayOfMonth;}else{m = ""+dayOfMonth;}
                                editDate.setText(m + "/" + (y) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editHeure = findViewById(R.id.editText3);
        editHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(SeeTask.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String h ;
                                String min;
                                if(sHour <= 9){h = "0"+sHour;}else{h = ""+sHour;}
                                if(sMinute <= 9){min = "0"+sMinute;}else{min = ""+sMinute;}
                                editHeure.setText(h + ":" + min);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        //------------------------------------------------------------------------------------------

        editTitre = findViewById(R.id.editText11);
        editTag = findViewById(R.id.editText);
        editDes = findViewById(R.id.editText10);

        editTitre.setText(titre);
        editTag.setText(tag);
        editDate.setText(date);
        editHeure.setText(heure);
        editDes.setText(des);

        // Called finished with save at true if the user click on "Save"
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeeTask.this.save = true;
                finish();
            }
        });

        // Called finished with supp at true if the user click on "Remove"
        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeeTask.this.supp = true;
                finish();
            }
        });
    }

    @Override
    public void finish(){

        Intent rIntent = new Intent();
        rIntent.putExtra("id", id);
        rIntent.putExtra("modif",save);
        rIntent.putExtra("supp", supp);

        titre = editTitre.getText().toString();
        tag = editTag.getText().toString();
        date = editDate.getText().toString();
        heure = editHeure.getText().toString();
        des = editDes.getText().toString();

        rIntent.putExtra("titre",titre);
        rIntent.putExtra("tag",tag);
        rIntent.putExtra("date",date);
        rIntent.putExtra("heure",heure);
        rIntent.putExtra("des",des);

        setResult(RESULT_OK, rIntent);

        super.finish();

    }

}
