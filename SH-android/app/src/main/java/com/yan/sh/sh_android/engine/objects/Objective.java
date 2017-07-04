package com.yan.sh.sh_android.engine.objects;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

/**
 * Created by yan on 6/20/17.
 */

public class Objective {

    private String objectiveId;
    private String name;
    private String description;

    private Double lat;
    private Double lon;

    public Objective(JSONObject object){
        if (object == null){
            return;
        }

        try{
            objectiveId = object.getString("id");
            name = object.getString("name");
            description = object.getString("description");

            lat = object.getJSONArray("location").getDouble(0);
            lon = object.getJSONArray("location").getDouble(1);

            Timber.i(name);
        }catch (JSONException err){
            Timber.e(err, "JSON error!");
        }
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation(){
        return lat + " " + lon;
    }
}
