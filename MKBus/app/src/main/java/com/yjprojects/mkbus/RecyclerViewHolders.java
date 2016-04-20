package com.yjprojects.mkbus;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

;

/**
 * Created by jyj on 2016-04-14.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView name;
    public ImageView imageView;
    public TextView name1;
    public TextView name2;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        name = (TextView)itemView.findViewById(R.id.cardview_text);
        name1 = (TextView)itemView.findViewById(R.id.cardview_text1);
        name2 = (TextView)itemView.findViewById(R.id.cardview_text2);
        imageView = (ImageView)itemView.findViewById(R.id.cardview_imageView);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }

}
