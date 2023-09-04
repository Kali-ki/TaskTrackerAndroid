package com.example.mytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//This adapteur is used to set the List of tasks

public class AdapteurListeTache extends BaseAdapter {

    private List<Tache> list;
    private LayoutInflater inflater;

    public AdapteurListeTache(Context context, List<Tache> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int possition){
        return possition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view;

        if(convertView == null){
            view = inflater.inflate(R.layout.elem_activite, parent, false);
        }else{
            view = convertView;
        }

        TextView titre = view.findViewById(R.id.titreAct);
        TextView date = view.findViewById(R.id.dateAct);
        TextView heure = view.findViewById(R.id.heureAct);
        TextView tag = view.findViewById(R.id.textView2);

        titre.setText(list.get(position).getTitre());
        date.setText(list.get(position).getDate());
        heure.setText(list.get(position).getHeure());
        tag.setText(list.get(position).getTag());

        return view;

    }

}
