package com.yan.sh.sh_android.ui.ObjectiveScrollView;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yan.sh.sh_android.engine.objects.Objective;

import java.util.ArrayList;

/**
 * Created by yan on 7/1/17.
 */

public class ObjectiveAdapter extends RecyclerView.Adapter<ObjectiveHolder> {

    private ArrayList<Objective> objectives;

    public ObjectiveAdapter(ArrayList<Objective> objectiveList){
        if(objectiveList != null){
            objectives = objectiveList;
        }
    }

    @Override
    public void onBindViewHolder(ObjectiveHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return objectives.size();
    }

    @Override
    public ObjectiveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
