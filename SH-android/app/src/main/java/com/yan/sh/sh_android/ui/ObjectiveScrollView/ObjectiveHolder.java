package com.yan.sh.sh_android.ui.ObjectiveScrollView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yan.sh.sh_android.R;

/**
 * Created by yan on 7/1/17.
 */

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
