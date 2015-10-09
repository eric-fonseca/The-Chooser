package com.example.shawn.decide;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
public class NewListActivity extends AppCompatActivity {

    public final static String ROLL = "ROLL";
    public final static String TITLE = "TITLE";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MyAdapter mAdapter;
    private Button mRollButton;
    private String mListID;
    private boolean mNameMatch;
    private ArrayList<ListItem> mCurrentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();

        if (intent.getStringExtra(MyAdapter.LIST_TITLE) != null) { //gets listID when user taps on a previously created list from main screen
            mListID = intent.getStringExtra(MyAdapter.LIST_TITLE);
        } else if (intent.getStringExtra(DecideActivity.NEW_LIST) != null) { //gets listID from the pop-up when a new list is created
            mListID = intent.getStringExtra(DecideActivity.NEW_LIST);
        } else { //gets listID when a user comes back from the history page
            mListID = intent.getStringExtra(HistoryActivity.LIST_ID);
        }

        //sets actionbar title
        setTitle(mListID);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list_items);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new MyAdapter(this, mListID);
        mRecyclerView.setAdapter(mAdapter);

        mRollButton = (Button) findViewById(R.id.roll_button);
        mRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.getData();
                String choice = "";

                ArrayList<ListItem> randomChoice = mAdapter.getCurrentListItems(mListID, "newList");
                if (randomChoice.size() > 1) {
                    //Randomly pick an item from the list
                    double randomNumber = Math.floor(Math.random() * randomChoice.size());
                    choice = randomChoice.get((int) randomNumber).text;

                    Intent intent = new Intent(NewListActivity.this, RollActivity.class);
                    intent.putExtra(ROLL, choice);
                    intent.putExtra(TITLE, mListID);

                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(NewListActivity.this, "You need at least 2 different choices to select", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }
        });

        mCurrentList = mAdapter.getCurrentListItems(mListID, "");
    }

    public void onAddItem(View view) {
        // get reference to EditText instance
        EditText editText = (EditText) findViewById(R.id.editText);
        // get the contents and cast from an Object to a String
        String text = editText.getText().toString();
        mNameMatch = false;

        // add the text to our adapter if text isn't blank
        if (text.trim().length() > 0) {
            for (int i = 0; i < mCurrentList.size(); i++) {
                if (text.equals(mCurrentList.get(i).text)) {
                    Toast toast = Toast.makeText(NewListActivity.this, "That option was already inserted previously", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mNameMatch = true;
                }
            }
            if (mNameMatch == false) {
                ListItem item = new ListItem(text, mListID, false);
                mAdapter.add(item, mAdapter.getItemCount());
                mCurrentList = mAdapter.getCurrentListItems(mListID, "");
            }

        } else {
            editText.setError("Nothing added because no text was inserted");
        }

        // clear EditText so user won’t add it twice
        editText.setText("");
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter.commitChanges(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.history:
                Intent intent = new Intent(NewListActivity.this, HistoryActivity.class);
                intent.putExtra(TITLE, mListID);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NewListActivity.this, DecideActivity.class);
        startActivity(intent);
    }
}
