package com.example.shawn.decide;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Fonseca on 9/27/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private final Context mContext;
    private ArrayList<ListItem> mData;
    public static final String PREFS_NAME = "DATA_PREFERENCES";
    public static final String KEY_LIST_DATA = "KEY_LIST_DATA";
    private String listID;

    public MyAdapter(Context context, String id){
        mContext = context;

        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String arrayListAsJson = pref.getString(KEY_LIST_DATA, "");
        Gson gson = new Gson();
        mData = gson.fromJson(arrayListAsJson, new TypeToken<ArrayList<ListItem>>() {
        }.getType());
        Log.d("DataStore", "reading string - arrayListToJson=" + arrayListAsJson);

        listID = id;

        if(mData == null){
            mData = new ArrayList<ListItem>();
        }

        Log.d("DataStore", "mData=" + mData);
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

    public void add(ListItem item, int position){
        position = position == -1 ? getItemCount() : position;
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if(position < getItemCount()){
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean commitChanges(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        Gson gson = new GsonBuilder().create();
        String arrayListToJson = gson.toJson(mData);
        Log.d("DataStore", "Saving string - arrayListToJson=" + arrayListToJson);
        editor.putString(KEY_LIST_DATA, arrayListToJson);

        boolean success = editor.commit();
        return success;
    }

    public ArrayList<ListItem> getData() {
        return mData;
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
        Log.d("DataStore", "mData.get(position).id == listID --> " + mData.get(position).id + " == " + listID);
        if(mData.get(position).id.equals(listID)){
            holder.listTitle.setText(mData.get(position).text);
            holder.listTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Text = " + mData.get(position) + " Position = " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
