package com.example.shawn.decide;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shawn on 9/25/2015.
 */
public class NewListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private MyAdapter mAdapter;
    private ImageView arrow;
    private Button mRollButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();


        //gets message that set in pop-up from DecideActivity
        String message = intent.getStringExtra(DecideActivity.EXTRA_MESSAGE);
        //TextView textView = new TextView(this);
        //textView.setTextSize(40);
        //textView.setText(message);
        //sets content text
        //setContentView(textView);

        //sets actionbar title
        setTitle(message);


        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_list_items);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);


        mAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);


        //mAdapter.add("Play Sports", 0);
        //mAdapter.add("Run Fast", 1);
        //mAdapter.add("Crazy", 2);


        //arrow = (ImageView)findViewById(R.id.arrowImage);
        //arrow.setVisibility(View.GONE);

        mRollButton = (Button)findViewById(R.id.roll_button);
        mRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewListActivity.this, RollActivity.class);
                startActivity(i);
            }
        });

        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
        */
    }
    public void onAddItem(View view) {
        // get reference to EditText instance
        EditText editText = (EditText) findViewById(R.id.editText);
        // get the contents and cast from an Object to a String
        String text = editText.getText().toString();
        // add the text to our adapter if text isn't blank
        if (text.trim().length() > 0) {
            mAdapter.add(text, mAdapter.getItemCount());
            //Log.d("ListActivity", "text added to list");
        }

        // clear EditText so user won’t add it twice
        editText.setText("");
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
                Intent i = new Intent(NewListActivity.this, HistoryActivity.class);
                startActivity(i);
                //openSearch();
                return true;
            /*case R.id.action_about:
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    /*
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_display_message, container, false);
            return rootView;
        }
    }*/
}
