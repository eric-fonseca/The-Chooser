package com.example.shawn.decide;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Fonseca on 9/27/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private final Context mContext;
    private List<String> mData;


    public MyAdapter(Context context){
        mContext = context;
        mData = new ArrayList<String>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView listTitle;
        public ImageView mArrowImage;

        public ViewHolder(View itemView){
            super(itemView);
            mArrowImage = (ImageView)itemView.findViewById(R.id.arrowImage);
            //hides arrows
            mArrowImage.setVisibility(View.GONE);
            listTitle = (TextView)itemView.findViewById(R.id.listTitle);
        }
    }

    public void add(String text, int position){
        position = position == -1 ? getItemCount() : position;
        mData.add(position, text);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if(position < getItemCount()){
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        holder.listTitle.setText(mData.get(position));
        holder.listTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Text = " + mData.get(position) + " Position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
