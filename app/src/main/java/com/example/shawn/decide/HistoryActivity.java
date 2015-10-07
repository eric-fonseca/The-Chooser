package com.example.shawn.decide;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shawn on 9/25/2015.
 */
public class HistoryActivity extends AppCompatActivity {

    public final static String LIST_ID = "LIST_ID";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MyAdapter mAdapter;
    private String listID;
    private String completedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();

        if(intent.getStringExtra(NewListActivity.TITLE) != null){
            listID = intent.getStringExtra(NewListActivity.TITLE);
        }
        else{
            completedItem = intent.getStringExtra(RollActivity.ROLL_TEXT);
            listID = intent.getStringExtra(RollActivity.ROLL_ID);
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.history_list_items);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new MyAdapter(this, listID);
        mRecyclerView.setAdapter(mAdapter);

        if(completedItem != null){
            mAdapter.getData().get(mAdapter.getPosition(completedItem, listID)).completed = true;
            Log.d("DataStore", "completed = " + mAdapter.getData());
        }

        Intent data = new Intent();
        data.putExtra(LIST_ID, listID);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onPause(){
        super.onPause();
        mAdapter.commitChanges(this);
    }


    public void onBackPressed(){
        super.onBackPressed();
        Log.d("DataStore", "back Pressed" + listID);
        Intent intent = new Intent(HistoryActivity.this, NewListActivity.class);
        intent.putExtra(LIST_ID, listID);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }


}
