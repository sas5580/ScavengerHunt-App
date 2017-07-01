package com.yan.sh.sh_android.engine.managers;

import com.yan.sh.sh_android.engine.Engine;
import com.yan.sh.sh_android.ui.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by yan on 6/11/17.
 */

public class GameManager extends Manager {

    private boolean initalized;
    private String startTime;
    private Integer gameId;

    public GameManager(){
        this.startup();
    }

    public void storeGameData(JSONObject data){
        if(data == null){
            return;
        }

        try{
            gameId = data.getInt("gameId");
            startTime = data.getString("startTime");
            JSONArray objectives = data.getJSONArray("objectives");
            Engine.objective().loadObjectives(objectives);

            initalized = true;

        }catch(JSONException ex){
            Timber.e(ex, "Json error");
        }
    }

    public boolean initialized(){
        return initalized;
    }
}
