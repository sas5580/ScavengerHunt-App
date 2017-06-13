package com.yan.sh.sh_android.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yan.sh.sh_android.R;
import com.yan.sh.sh_android.services.ScavengerHuntService;
import com.yan.sh.sh_android.util.TimberTree;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new TimberTree());

        Intent serviceIntent = new Intent(this, ScavengerHuntService.class);
        startService(serviceIntent);
    }

}
