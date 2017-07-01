package com.yan.sh.sh_android.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yan.sh.sh_android.R;
import com.yan.sh.sh_android.engine.Engine;
import com.yan.sh.sh_android.services.ScavengerHuntService;
import com.yan.sh.sh_android.util.TimberTree;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    private EditText gameCode;
    private Button joinGame;
    private Button returnToLastGame;
    private ImageView wifi;
    private ImageView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("LoginActivity", "onCreate");

        Timber.plant(new TimberTree());

        Intent serviceIntent = new Intent(this, ScavengerHuntService.class);
        startService(serviceIntent);

        LocalBroadcastManager.getInstance(this).registerReceiver(onNetworkChange, new IntentFilter("NETWORK_CHANGE"));
        LocalBroadcastManager.getInstance(this).registerReceiver(onLocationChange, new IntentFilter("LOCATION_CHANGE"));

        gameCode = (EditText) findViewById(R.id.et_game_code);
        joinGame = (Button) findViewById(R.id.bt_join_game);
        returnToLastGame = (Button) findViewById(R.id.bt_join_last_game);
        wifi = (ImageView) findViewById(R.id.iv_wifi);
        location = (ImageView) findViewById(R.id.iv_location);

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJoinGameClick();
            }
        });

        returnToLastGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onJoinLastGameClick();
            }
        });

        checkEngineInitialization();
    }

    BroadcastReceiver onNetworkChange = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLoginUI();
        }
    };

    BroadcastReceiver onLocationChange = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLoginUI();
        }
    };

    private void checkEngineInitialization(){
        Log.i("LoginActivity", "check init");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(Engine.managersInitialized()){
                    refreshLoginUI();
                } else {
                    checkEngineInitialization();
                }
            }
        });
    }

    private void refreshLoginUI(){
        if(Engine.data().getGameData().equals("")) {
            returnToLastGame.setEnabled(false);
        } else {
            returnToLastGame.setEnabled(true);
        }

        if(!Engine.hardware().hasNetworkAccess()) {
            wifi.setVisibility(View.INVISIBLE);
        } else {
            wifi.setVisibility(View.VISIBLE);
        }

        if(!Engine.hardware().isLocationEnabled()) {
            location.setVisibility(View.INVISIBLE);
        } else {
            location.setVisibility(View.VISIBLE);
        }

        if(!Engine.hardware().isLocationEnabled() || !Engine.hardware().hasNetworkAccess()){
            joinGame.setEnabled(false);
        } else {
            joinGame.setEnabled(true);
        }
    }

    public void onJoinGameClick(){
        onJoinGame(gameCode.getText().toString());
    }

    public void onJoinLastGameClick(){
        onJoinGame(Engine.data().getGameData());
    }

    public void onJoinGame(String gameCode){
        Engine.network().getGameData(gameCode, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Timber.e("Error getting game data!");
                onInitializationError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    //do some validation, make sure a valid game data gets sent
                    JSONObject gameData = new JSONObject();//response.body().string());
                    gameData.put("data", "test data");

                    //TODO : add more fake data

                    //do validation here
                    if(gameData.has("data")){
                        Engine.game().storeGameData(gameData);
                        onInitializationSuccess();
                    } else {
                        onInitializationError();
                    }
                } catch (JSONException ex){
                    Timber.e(ex, "Json error!");
                    onInitializationError();
                }
            }
        });
    }

    public void onInitializationError(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Timber.i("on error");
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Error");
                builder.setMessage("Game does not exist!");
                builder.create().show();
            }
        });

    }

    public void onInitializationSuccess(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
