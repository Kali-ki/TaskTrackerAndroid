package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_CODE = 1;
    private ArrayList<Tache> listTask; // The ArrayList with all the tasks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--- Set texte about the differents TextView ----------------------------------------------

        TextView tv1 = findViewById(R.id.textView10);
        tv1.setText(R.string.research);

        TextView tv2 = findViewById(R.id.textView4);
        tv2.setText(R.string.title);

        Button buttonPlus = findViewById(R.id.button2);
        buttonPlus.setText(R.string.new_t);

        //--- Load the save if it exists -----------------------------------------------------------

        ArrayList<Tache> t = (ArrayList<Tache>) Serializer.deSerialize("save", this);

        if(t != null){
            listTask = t;
            showTask();
        }else{
            listTask = new ArrayList<>();
        }


        //--- Recherche le Tag ----------------------------------------------------------------------------

        EditText recherche = findViewById(R.id.editText5);
        recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String res = s.toString();
                ArrayList<Tache> taches = getTaskWithTag(res);
                showTask(taches);
            }
        });

        //--- Pour créer une activitée -------------------------------------------------------------

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNew = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(intentNew, REQUEST_CODE);
            }
        });

    }

    /**
     * Get the index of Task in the ArrayList thanks to it "id"
     * @param id - The id
     * @return - The index
     */
    public int getIndex(String id){
        int res = -1;
        for(int j = 0 ; j < listTask.size() ; j++){
            Tache t = listTask.get(j);
            if((t.getId()).equals(id)){
                res = j;
                j = listTask.size();
            }
        }
        return res;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            if(data.hasExtra("modif")){

                //--- End of SeeTask - Save --------------------------------------------------------

                if(data.getExtras().getBoolean("modif")){

                    String id = data.getExtras().getString("id");
                    String titre = data.getExtras().getString("titre");
                    String tag = data.getExtras().getString("tag");
                    String date = data.getExtras().getString("date");
                    String heure = data.getExtras().getString("heure");
                    String des = data.getExtras().getString("des");

                    Tache modifiedTask = new Tache(titre, date, heure, tag, des);
                    listTask.set(getIndex(id), modifiedTask);
                    sort();

                }else if(data.getExtras().getBoolean("supp")){

                    //--- End of SeeTask - Supp --------------------------------------------------------

                    String id = data.getExtras().getString("id");
                    listTask.remove(getIndex(id));

                }

            }else if(data.hasExtra("vf")){

                // End of CreateActivity

                boolean vf = data.getExtras().getBoolean("vf");
                String titre = data.getExtras().getString("titre");
                String date = data.getExtras().getString("date");
                String tags = "";
                String heur = "";
                String des = "";

                if(data.hasExtra("tag")){tags = data.getExtras().getString("tag");}
                if(data.hasExtra("heure")){heur = data.getExtras().getString("heure");}
                if(data.hasExtra("des")){des = data.getExtras().getString("des");}

                if(vf){
                    Tache nt = new Tache(titre, date, heur, tags,des);
                    listTask.add(nt);
                    sort();
                }
            }

            showTask();

        }else if(resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Recherche interrompue", Toast.LENGTH_LONG).show();
        }

        Serializer.serialize("save", listTask, this);

    }

    /**
     * This function allow actualise the displayed activity and to set the associed tasks
     */
    public void showTask(){
        AdapteurListeTache adapteur = new AdapteurListeTache(this, listTask);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapteur);

        // For a long click -> Open modification
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Tache selectedTask = (Tache) parent.getItemAtPosition(position);

                Intent seeTask = new Intent(MainActivity.this, SeeTask.class);
                seeTask.putExtra("titre", selectedTask.getTitre());
                seeTask.putExtra("tag", selectedTask.getTag());
                seeTask.putExtra("date", selectedTask.getDate());
                seeTask.putExtra("heure", selectedTask.getHeure());
                seeTask.putExtra("des", selectedTask.getDescription());
                seeTask.putExtra("id", selectedTask.getId());
                startActivityForResult(seeTask, REQUEST_CODE);
                return true;
            }
        });

        // For a short click -> Open task
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tache selectedTask = (Tache) parent.getItemAtPosition(position);
                Intent seeSimpleTask = new Intent(MainActivity.this, SeeSimpleTask.class);

                seeSimpleTask.putExtra("titre", selectedTask.getTitre());
                seeSimpleTask.putExtra("tag", selectedTask.getTag());
                seeSimpleTask.putExtra("date", selectedTask.getDate());
                seeSimpleTask.putExtra("heure", selectedTask.getHeure());
                seeSimpleTask.putExtra("des", selectedTask.getDescription());

                startActivity(seeSimpleTask);
            }
        });

    }

    /**
     * The same that above but here a ArrayList specific is use
     * This method is use for the tag research
     * @param t - The ArrayList
     */
    public void showTask(ArrayList<Tache> t){
        AdapteurListeTache adapteur = new AdapteurListeTache(this, t);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapteur);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Tache selectedTask = (Tache) parent.getItemAtPosition(position);

                Intent seeTask = new Intent(MainActivity.this, SeeTask.class);
                seeTask.putExtra("titre", selectedTask.getTitre());
                seeTask.putExtra("tag", selectedTask.getTag());
                seeTask.putExtra("date", selectedTask.getDate());
                seeTask.putExtra("heure", selectedTask.getHeure());
                seeTask.putExtra("des", selectedTask.getDescription());
                seeTask.putExtra("id", selectedTask.getId());
                startActivityForResult(seeTask, REQUEST_CODE);
                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tache selectedTask = (Tache) parent.getItemAtPosition(position);
                Intent seeSimpleTask = new Intent(MainActivity.this, SeeSimpleTask.class);

                seeSimpleTask.putExtra("titre", selectedTask.getTitre());
                seeSimpleTask.putExtra("tag", selectedTask.getTag());
                seeSimpleTask.putExtra("date", selectedTask.getDate());
                seeSimpleTask.putExtra("heure", selectedTask.getHeure());
                seeSimpleTask.putExtra("des", selectedTask.getDescription());

                startActivity(seeSimpleTask);
            }
        });

    }

    /**
     * Allow to sort listTask (the ArrayList with all the tasks)
     */
    public void sort(){
        for(int i = 0 ; i < listTask.size()-1 ; i++){
            int index = getMin(i);
            if(index != -1){
                Tache save = listTask.get(index);
                listTask.set(index, listTask.get(i));
                listTask.set(i, save);
            }
        }
    }

    /**
     * Get the minimal date and hour value from minIndex in ArrayList of tasks
     * @param minIndex - The minIndex - Where the research begin
     * @return the index of the task
     */
    public int getMin(int minIndex){
        int minI = -1;
        if(minIndex+1 < listTask.size()){
            Tache min = listTask.get(minIndex);
            for(int i = minIndex+1 ; i < listTask.size() ; i++){
                if(min.compareTo(listTask.get(i)) == 1){
                    min = listTask.get(i);
                    minI = i;
                }
            }
        }
        return minI;
    }

    /**
     * Return an ArrayList with the tasks of the ArrayList which contains the tag
     * @param tag - The tag
     * @return - The ArrayList
     */
    public ArrayList<Tache> getTaskWithTag(String tag){
        ArrayList<Tache> res = new ArrayList<>();
        for(Tache t : listTask){
            if((t.getTag()).contains(tag))
                res.add(t);
        }
        return res;
    }

}
