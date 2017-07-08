package com.yan.sh.sh_android.engine.managers;

import com.yan.sh.sh_android.engine.objects.Objective;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import timber.log.Timber;

/**
 * Created by yan on 6/16/17.
 */

public class ObjectiveManager extends Manager {

    private ArrayList<Objective> userObjectives;

    public ObjectiveManager(){
        this.startup();
        userObjectives = new ArrayList<>();
    }

    public void loadObjectives(JSONArray objectives){
        if(userObjectives != null){
            userObjectives.clear();
        }

        for(int i = 0; i < objectives.length(); i++){
            try{
                Objective objective = new Objective(objectives.getJSONObject(i));
                if(userObjectives != null){
                    userObjectives.add(objective);
                }
            } catch (JSONException ex) {
                Timber.e(ex, "Error!");
            }
        }
    }

    public ArrayList<Objective> getObjectives(){
        return userObjectives;
    }

    private void sortObjectives(){
        //TODO: sort objective algorithm
    }
}
