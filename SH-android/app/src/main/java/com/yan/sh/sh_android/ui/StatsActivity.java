package com.yan.sh.sh_android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yan.sh.sh_android.R;

public class StatsActivity extends AppCompatActivity {

    private TextView rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        rank = (TextView) findViewById(R.id.tv_ranking);
        getActionBar().setTitle("My Stats");
    }

    //TODO figure out what to do with this
}
