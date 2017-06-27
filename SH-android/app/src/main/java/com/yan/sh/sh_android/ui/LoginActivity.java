package com.yan.sh.sh_android.ui;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yan.sh.sh_android.R;
import com.yan.sh.sh_android.engine.Engine;

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

        gameCode = (EditText) findViewById(R.id.et_game_code);
        joinGame = (Button) findViewById(R.id.bt_join_game);
        returnToLastGame = (Button) findViewById(R.id.bt_join_last_game);
        wifi = (ImageView) findViewById(R.id.iv_wifi);
        location = (ImageView) findViewById(R.id.iv_location);

        if(Engine.data().getGameData().equals("")) {
            returnToLastGame.setVisibility(View.INVISIBLE);
        }

        if(!Engine.hardware().hasNetworkAccess()) {
            wifi.setVisibility(View.INVISIBLE);
        }

        if(!Engine.hardware().isLocationEnabled()) {
            location.setVisibility(View.INVISIBLE);
        }

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
