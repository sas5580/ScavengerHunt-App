package com.yan.sh.sh_android.ui.ObjectiveScrollView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yan.sh.sh_android.R;
import com.yan.sh.sh_android.engine.objects.Objective;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by yan on 7/1/17.
 */

public class ObjectiveAdapter extends RecyclerView.Adapter<ObjectiveAdapter.ObjectiveHolder> {

    private ArrayList<Objective> objectives;
    private Context mContext;

    public class ObjectiveHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView name;
        public TextView surname;

        public ObjectiveHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            name  = (TextView) v.findViewById(R.id.txtName);
            surname = (TextView) v.findViewById(R.id.txtSurname);
        }
    }

    public ObjectiveAdapter(Context context, ArrayList<Objective> objectiveList){
        if(objectiveList != null){
            objectives = objectiveList;
        }

        mContext = context;
    }

    @Override
    public void onBindViewHolder(ObjectiveHolder holder, int position) {
        Objective objective = objectives.get(position);
        Timber.i("on bind");
        holder.name.setText(objective.getName());
        holder.title.setText(objective.getDescription());
        holder.surname.setText(objective.getLocation());
    }

    @Override
    public int getItemCount() {
        return objectives.size();
    }

    @Override
    public ObjectiveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        Timber.i("on create");
        View objectiveView = inflater.inflate(R.layout.objective_card, parent, false);

        ObjectiveHolder objectiveHolder = new ObjectiveHolder(objectiveView);
        return objectiveHolder;
    }
}
