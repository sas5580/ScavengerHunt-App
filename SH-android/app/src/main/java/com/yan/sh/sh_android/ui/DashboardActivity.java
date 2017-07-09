package com.yan.sh.sh_android.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.yan.sh.sh_android.R;
import com.yan.sh.sh_android.engine.Engine;
import com.yan.sh.sh_android.engine.objects.Objective;
import com.yan.sh.sh_android.ui.ObjectiveScrollView.ObjectiveAdapter;

import com.cloudinary.android.*;
import com.yan.sh.sh_android.util.Utilities;

import timber.log.Timber;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_objective);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ObjectiveAdapter objectiveAdapter = new ObjectiveAdapter(this, Engine.objective().getObjectives());
        recyclerView.setAdapter(objectiveAdapter);

        calculateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item_map:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.item_stats:
                Timber.i("stats");
                break;
            default:
                break;
        }
        return true;
    }

    private void calculateUI(){
        //TODO: add time calculation
        if(Engine.objective().getObjectives() == null){
            return;
        }
        //uncompleted objectives calculation
        int uncompletedCount = 0;
        for(Objective objective : Engine.objective().getObjectives()){
            if(!objective.getCompleted()){
                uncompletedCount++;
            }
        }

        TextView objectiveCount = (TextView) findViewById(R.id.tv_objectives_remaining);
        objectiveCount.setText("Objectives Remaining: " + uncompletedCount);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Engine.socket().closeSocket();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.i("request Code " + requestCode);

        Objective captured = Engine.objective().getObjectiveByIndex(requestCode);
        if(captured == null || !data.hasExtra("data")){
            Timber.w("Activity result captured is not an objective or no image???");
            return;
        }

        Bitmap image = (Bitmap) data.getExtras().get("data");
        String url = Utilities.saveBitmap(image, this, "test1.jpg");
        Engine.cloud().uploadPicture(url);
    }
}
