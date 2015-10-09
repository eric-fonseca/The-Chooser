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
    private String mListID;
    private String mCompletedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();

        if(intent.getStringExtra(NewListActivity.TITLE) != null){ //if user came from NewListActivity pass in the list ID so we can filter history
            mListID = intent.getStringExtra(NewListActivity.TITLE);
        }
        else{
            mCompletedItem = intent.getStringExtra(RollActivity.ROLL_TEXT); //get the completed item's text so we can set it to completed
            mListID = intent.getStringExtra(RollActivity.ROLL_ID); //if user came from HistoryActivity pass in the list ID so we can filter history
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.history_list_items);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new MyAdapter(this, mListID);
        mRecyclerView.setAdapter(mAdapter);

        if(mCompletedItem != null){ //set the item to completed if the user came from RollActivity
            mAdapter.getData().get(mAdapter.getPosition(mCompletedItem, mListID)).completed = true;
        }

        Intent data = new Intent();
        data.putExtra(LIST_ID, mListID);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onPause(){
        super.onPause();
        mAdapter.commitChanges(this);
    }


    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(HistoryActivity.this, NewListActivity.class);
        intent.putExtra(LIST_ID, mListID);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //if back arrow is pressed
            onBackPressed();
            return true;
        }
        return false;
    }


}
