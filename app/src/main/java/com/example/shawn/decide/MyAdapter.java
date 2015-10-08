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
    private final Context mContext;
    private ArrayList<ListItem> mData;
    public final static String PREFS_NAME = "DATA_PREFERENCES";
    public final static String KEY_LIST_DATA = "KEY_LIST_DATA";
    public final static String LIST_TITLE = "LIST_TITLE";
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
            listTitle = (TextView)itemView.findViewById(R.id.listTitle);

            if(!(mContext instanceof DecideActivity)){
                mArrowImage.setVisibility(View.GONE);
            }
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
            notifyDataSetChanged();
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

    public ListItem getItem(int position){
        return mData.get(position);
    }

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
<<<<<<< HEAD
            if(listType == "newList"){
                if(mData.get(i).id.equals(listID) && mData.get(i).completed == false){
                    currentList.add(mData.get(i));
                }
=======
            if(mData.get(i).id.equals(listID) && mData.get(i).completed == false){
                currentList.add(mData.get(i));
>>>>>>> 1e2131828d35f607be1d9d00c3f9f3f36af18e10
            }

            else if (listType == "history"){
                if(mData.get(i).id.equals(listID) && mData.get(i).completed == true){
                    currentList.add(mData.get(i));
                }
            }
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
        if(mData.get(position).id.equals(listID)){
            if(mContext instanceof HistoryActivity){
                if(mData.get(position).completed == true){
                    holder.listTitle.setText(mData.get(position).text);
<<<<<<< HEAD
                    holder.listTitle.setTextColor(Color.parseColor("#6E6E6E"));
                    holder.itemView.setPadding(0, 10, 0, 0);
                    Log.d("MyAdapter", "Its true from history");
=======
                    holder.itemView.setPadding(0, 10, 0, 0);
>>>>>>> 1e2131828d35f607be1d9d00c3f9f3f36af18e10
                }
                else{
                    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                    layoutParams.height = 0;
                    holder.itemView.setLayoutParams(layoutParams);
                }
            }
            else if(mContext instanceof NewListActivity){
                if(mData.get(position).completed == false){
                    holder.listTitle.setText(mData.get(position).text);
<<<<<<< HEAD
                    holder.listTitle.setTextColor(Color.parseColor("#6E6E6E"));
=======
>>>>>>> 1e2131828d35f607be1d9d00c3f9f3f36af18e10
                    holder.itemView.setPadding(0, 10, 0, 0);
                }
                else{
                    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                    layoutParams.height = 0;
                    holder.itemView.setLayoutParams(layoutParams);
                }
            }
            else if(mContext instanceof DecideActivity){
                holder.listTitle.setText(mData.get(position).text);
<<<<<<< HEAD
                //holder.listTitle.setTypeface(holder.listTitle.getTypeface(), Typeface.BOLD);
                holder.itemView.setPadding(0, 10, 0, 0);

=======
                holder.itemView.setPadding(0, 10, 0, 0);
>>>>>>> 1e2131828d35f607be1d9d00c3f9f3f36af18e10

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, NewListActivity.class);
                        intent.putExtra(LIST_TITLE, mData.get(position).text);
                        mContext.startActivity(intent);
                        Log.d("DataStore", "position=" + position);
                    }
                });
            }
            if(!(mContext instanceof HistoryActivity)) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        remove(position);
                        return true;
                    }
                });
            }

        }
        else{
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = 0;
            holder.itemView.setLayoutParams(layoutParams);
        }
    }

}
