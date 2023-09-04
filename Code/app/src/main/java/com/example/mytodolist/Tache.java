package com.example.mytodolist;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Tache implements Serializable {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int idTask;

    private String titre;
    private String date;
    private String heure;
    private String tag;
    private String description;

    public Tache(String titre, String date, String heure, String tag, String description){

        this.idTask = count.incrementAndGet();

        this.titre = titre;
        this.date = date;
        this.heure = heure;
        this.tag = tag;
        this.description = description;
    }

    public String getTitre(){return this.titre;}

    public String getDate(){return this .date;}

    public String getHeure(){ return this.heure ;}

    public String getTag(){return this.tag; }

    public String getDescription(){ return this.description;}

    public String getId(){return String.valueOf(this.idTask);}

    public void setTitre(String titre){
        this.titre = titre;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setHeure(String heure){
        this.heure = heure;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Used to compare two task thanks there date and hour
     * @param t - The compared task
     * @return 1 if the compared task is biggest, -1 for the reverse and 0 if there are equals
     */
    public int compareTo(Tache t){
        int res = 0;

        String date1 = this.date;
        String date2 = t.getDate();

        String heure1 = this.heure;
        String heure2 = t.getHeure();

        String[] date11 = date1.split("\\/");
        String[] date22 = date2.split("\\/");

        String[] heure11 = heure1.split(":");
        String[] heure22 = heure2.split(":");

        int j1 = Integer.parseInt(date11[0]);
        int m1 = Integer.parseInt(date11[1]);
        int a1 = Integer.parseInt(date11[2]);

        int j2 = Integer.parseInt(date22[0]);
        int m2 = Integer.parseInt(date22[1]);
        int a2 = Integer.parseInt(date22[2]);

        if(!t.getHeure().equals("") && !this.heure.equals("")){

            int min2 = Integer.parseInt(heure22[1]);
            int h2 = Integer.parseInt(heure22[0]);

            int min1 = Integer.parseInt(heure11[1]);
            int h1 = Integer.parseInt(heure11[0]);

            if(a1 < a2)
                res = -1;
            else if(a2 < a1)
                res = 1;
            else{
                if(m1 < m2)
                    res = -1;
                else if(m1 > m2)
                    res = 1;
                else{
                    if(j1 < j2)
                        res = -1;
                    else if(j2 < j1)
                        res = 1;
                    else{
                        if(h1 < h2)
                            res = -1;
                        else if(h2 < h1)
                            res = 1;
                        else{
                            if(min1 < min2)
                                res = -1;
                            else if(min2 < min1)
                                res = 1;
                        }
                    }
                }
            }

        }else{

            if(a1 < a2)
                res = -1;
            else if(a2 < a1)
                res = 1;
            else{
                if(m1 < m2)
                    res = -1;
                else if(m1 > m2)
                    res = 1;
                else{
                    if(j1 < j2)
                        res = -1;
                    else if(j2 < j1)
                        res = 1;
                    else
                        res = 0;
        }}}

        return res;
    }
}
