package com.yan.sh.sh_android.engine.managers;

import com.yan.sh.sh_android.engine.objects.Objective;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yan on 6/16/17.
 */

public class ObjectiveManager extends Manager {

    private ArrayList<Objective> userObjectives;

    public ObjectiveManager(){
        this.startup();
        userObjectives = new ArrayList<>();
    }

    public void loadObjectives(JSONObject objectives){

    }
}
