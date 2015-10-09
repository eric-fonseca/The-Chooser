package com.example.shawn.decide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DecideActivity extends AppCompatActivity {

    public final static String NEW_LIST = "NEW_LIST";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MyAdapter mAdapter;
    private boolean mNameMatch;
    private ArrayList<ListItem> mListTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new MyAdapter(this, "mainList");
        mRecyclerView.setAdapter(mAdapter);

        mListTitles = mAdapter.getCurrentListItems("mainList", "");
    }

    @Override
    public void onPause(){
        super.onPause();
        mAdapter.commitChanges(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_decide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent i = new Intent(DecideActivity.this, AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.action_home:
                Intent intent = new Intent(getApplicationContext(), DecideActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add:
                createDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New List");
        builder.setMessage("What do you want to call this list?");
        builder.setPositiveButton("Create", null);
        builder.setNegativeButton("Cancel", null);
        //create text field
        final EditText input = new EditText(DecideActivity.this);
        builder.setView(input);

        //create pop-up
        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //create a string that hold the text value
                String text = input.getText().toString();
                mNameMatch = false;

                for (int i = 0; i < mListTitles.size(); i++) {
                    if (text.equals(mListTitles.get(i).text)) {
                        Toast toast = Toast.makeText(DecideActivity.this, "You already have a list with that name!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        mNameMatch = true;
                    }
                }

                if(TextUtils.isEmpty(text)){
                   //show error message on pop-up
                   input.setError("Please insert a name for your list");
                }
                else if(mNameMatch == false) {
                    //create a new screen and pass the text value to it
                    Intent intent = new Intent(DecideActivity.this, NewListActivity.class);

                    ListItem item = new ListItem(text, "mainList", false);

                    intent.putExtra(NEW_LIST, item.text);
                    mAdapter.add(item, mAdapter.getItemCount());
                    startActivity(intent);

                    mListTitles = mAdapter.getCurrentListItems("mainList", "");
                }
            }
        });
    }


}

