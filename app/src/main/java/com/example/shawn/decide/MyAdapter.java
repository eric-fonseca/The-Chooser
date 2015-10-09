package com.example.shawn.decide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.widget.CardView;
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
    public final static String PREFS_NAME = "DATA_PREFERENCES";
    public final static String KEY_LIST_DATA = "KEY_LIST_DATA";
    public final static String LIST_TITLE = "LIST_TITLE";
    private final Context mContext;
    private ArrayList<ListItem> mData;
    private String mListID;

    public MyAdapter(Context context, String id){
        mContext = context;

        //Save ArrayList to Json
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String arrayListAsJson = pref.getString(KEY_LIST_DATA, "");
        Gson gson = new Gson();
        mData = gson.fromJson(arrayListAsJson, new TypeToken<ArrayList<ListItem>>() {
        }.getType());

        mListID = id;

        //If this is the first time the user is using app create new list
        if(mData == null){
            mData = new ArrayList<ListItem>();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView listTitle;
        public ImageView mArrowImage;

        public ViewHolder(View itemView){
            super(itemView);

            mArrowImage = (ImageView)itemView.findViewById(R.id.arrowImage);
            listTitle = (TextView)itemView.findViewById(R.id.listTitle);

            //Remove the arrows if you aren't on the main list
            if(!(mContext instanceof DecideActivity)){
                mArrowImage.setVisibility(View.GONE);
            }
        }
    }

    public void add(ListItem item, int position){
        position = position == -1 ? getItemCount() : position;
        //Add the item to the chosen position in the ArrayList
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position){
        //Remove the item from the chosen position in the ArrayList
        if(position < getItemCount()){
            mData.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
    }

    public boolean commitChanges(Context context){
        //Save the changes to Json
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        Gson gson = new GsonBuilder().create();
        String arrayListToJson = gson.toJson(mData);
        editor.putString(KEY_LIST_DATA, arrayListToJson);

        boolean success = editor.commit();
        return success;
    }

    public ArrayList<ListItem> getData() {
        return mData;
    }

    public ListItem getItem(int position){
        return mData.get(position);
    }

    //Return an item in a specific list with a given name
    public int getPosition(String name, String id) {
        for(ListItem listItem : mData) {
            if(listItem.text.equals(name) && listItem.id.equals(id)) {
                return mData.indexOf(listItem);
            }
        }
        return 0;
    }

    public ArrayList<ListItem> getCurrentListItems(String listID, String listType){
        ArrayList<ListItem> currentList = new ArrayList<ListItem>();

        for(int i = 0; i < mData.size(); i++){
            //Get all the items in the new list activity
            if(listType == "newList"){
                if(mData.get(i).id.equals(listID) && mData.get(i).completed == false){
                    currentList.add(mData.get(i));
                }
            }
            //Get all the items in the history activity
            else if (listType == "history"){
                if(mData.get(i).id.equals(listID) && mData.get(i).completed == true){
                    currentList.add(mData.get(i));
                }
            }
            //Get all the items in a specific list
            else{
                if(mData.get(i).id.equals(listID)){
                    currentList.add(mData.get(i));
                }
            }
        }
        return currentList;
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
        //Only display items that match the list ID of the current list
        if(mData.get(position).id.equals(mListID)){
            if(mContext instanceof HistoryActivity){
                //Hide all items that aren't marked completed in the history activity
                if(mData.get(position).completed == true){
                    holder.listTitle.setText(mData.get(position).text);
                }
                else{
                    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                    layoutParams.height = 0;
                    holder.itemView.setLayoutParams(layoutParams);
                }
            }
            else if(mContext instanceof NewListActivity){
                //Hide all items that are marked completed in the new list activity
                if(mData.get(position).completed == false){
                    holder.listTitle.setText(mData.get(position).text);
                }
                else{
                    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                    layoutParams.height = 0;
                    holder.itemView.setLayoutParams(layoutParams);
                }
            }
            //Add a click listener to all items inside the main list to access sub lists
            else if(mContext instanceof DecideActivity){
                holder.listTitle.setText(mData.get(position).text);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, NewListActivity.class);
                        intent.putExtra(LIST_TITLE, mData.get(position).text);
                        mContext.startActivity(intent);
                    }
                });
            }
            holder.itemView.setPadding(0, 10, 0, 0);

            if(!(mContext instanceof HistoryActivity)) {
                //Remove items from lists with a long press unless you're on the history page
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        remove(position);
                        return true;
                    }
                });
            }
        }
        //Hide items that don't match the current list ID
        else{
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = 0;
            holder.itemView.setLayoutParams(layoutParams);
        }
    }

}
