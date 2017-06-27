package com.yan.sh.sh_android.engine.managers;

import com.yan.sh.sh_android.engine.Engine;

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

    private JSONObject gameData;
    public GameManager(){
        this.startup();
    }

    public void initializeGame(String gameCode){

        Engine.network().getGameData(gameCode, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Timber.e("Error getting game data!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    gameData = new JSONObject(response.body().string());
                    Timber.i(gameData.toString());
                } catch (JSONException ex){
                    Timber.e(ex, "Json error!");
                }
            }
        });
    }
}
