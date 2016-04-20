package com.yjprojects.mkbus;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by jyj on 2016-04-14.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> implements ItemTouchHelperAdapter{

    private List<ImageText> imageText;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ImageText> imageText) {
        this.imageText = imageText;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cardview, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.name.setText(imageText.get(position).string);
        holder.name.setSingleLine();
        holder.name.setEllipsize(TextUtils.TruncateAt.END);
        holder.imageView.setImageDrawable(imageText.get(position).drawable);
        holder.name1.setText(imageText.get(position).string1);
        holder.name2.setText(imageText.get(position).string2);
    }

    @Override
    public int getItemCount() {
        return this.imageText.size();
    }

    @Override
    public void onItemDismiss(final int position) {
        final ImageText deletedImageText = imageText.get(position);
        imageText.remove(position);

        Snackbar.make(((Activity)context).findViewById(R.id.layout_coordinator), "실행 취소", Snackbar.LENGTH_LONG)
                .setAction("취소", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageText.add(position, deletedImageText);
                        notifyItemInserted(position);
                    }
                })
                .show();

        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(imageText, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(imageText, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

}

class ImageText{

    String string = "";
    String string1 = "";
    String string2 = "";
    Drawable drawable;

    public ImageText(String string, String string1, String string2, Drawable drawable){
        this.string = string;
        if(Integer.parseInt(string1) > 999) this.string1 = "#" + string1;
        else this.string1 = string1 + "분 마다";

        if(string2 != null) this.string2 = "버스 수 : " + string2 + "대";
        this.drawable = drawable;
    }
}
