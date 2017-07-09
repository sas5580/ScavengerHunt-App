package com.yan.sh.sh_android.engine.managers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.yan.sh.sh_android.engine.objects.Objective;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import timber.log.Timber;

/**
 * Created by yan on 6/16/17.
 */

public class ObjectiveManager extends Manager {

    private ArrayList<Objective> userObjectives;
    private Context mContext;

    public ObjectiveManager(Context context){
        this.startup();
        userObjectives = new ArrayList<>();
        mContext = context;
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

    public Objective getObjectiveById(String id){
        if(userObjectives == null){
            return null;
        }

        for(Objective objective : userObjectives){
            if(objective.getObjectiveId().equals(id)){
                return objective;
            }
        }

        return null;
    }

    public Objective getObjectiveByIndex(int index){
        if(userObjectives == null || index < 0 || index >= userObjectives.size()){
            return null;
        }

        return userObjectives.get(index);
    }

    public void completingObjective(String objectiveId, String url){
        Objective completedObjective = getObjectiveById(objectiveId);

        if(completedObjective == null){
            Timber.w("Null objective?");
            return;
        }

        completedObjective.setPictureUrl(url);
        completedObjective.setAsCompleted(System.currentTimeMillis()/1000);

        for(int i = 0; i < userObjectives.size() ; i++){
            if(userObjectives.get(i).getObjectiveId().equals(objectiveId)){
                userObjectives.remove(i);
                userObjectives.add(completedObjective);
                onObjectiveUpdate();
                break;
            }
        }

        //TODO: send socket message to server
    }

    private void onObjectiveUpdate(){
        Intent objectiveChange = new Intent();
        objectiveChange.setAction("OBJECTIVE_CHANGE");

        //send local broadcast
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        localBroadcastManager.sendBroadcast(objectiveChange);
    }
}
