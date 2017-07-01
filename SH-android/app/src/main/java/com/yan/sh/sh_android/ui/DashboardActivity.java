package com.yan.sh.sh_android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yan.sh.sh_android.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //TODO : get user fragment if user has not been initialized
    }
}
