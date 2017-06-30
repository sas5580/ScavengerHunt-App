package com.yan.sh.sh_android.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
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
            returnToLastGame.setVisibility(View.INVISIBLE);
        } else {
            returnToLastGame.setVisibility(View.VISIBLE);
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
    }

    public void onJoinGameClick(){
        onJoinGame(gameCode.getText().toString());
    }

    public void onJoinLastGameClick(){
        onJoinGame(Engine.data().getGameData());
    }

    public void onJoinGame(String gameCode){
        Engine.game().initializeGame(gameCode);
    }
}
