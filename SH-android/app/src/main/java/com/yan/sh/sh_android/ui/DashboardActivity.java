package com.yan.sh.sh_android.ui;

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

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //TODO : get user fragment if user has not been initialized

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
        return super.onOptionsItemSelected(item);
    }

    private void calculateUI(){
        //TODO: add time calculation

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
}
