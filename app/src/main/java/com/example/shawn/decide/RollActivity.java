package com.example.shawn.decide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Shawn on 9/27/2015.
 */
public class RollActivity extends AppCompatActivity {
    private Button mCompleteButton;
    private Button mIncompleteButton;
    private TextView mChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);

        //getActionBar().hide();
        Intent intent = getIntent();
        String choice = intent.getStringExtra(NewListActivity.ROLL);

        mChoice = (TextView)findViewById(R.id.roll_choice);
        mChoice.setText(choice);

        mCompleteButton = (Button)findViewById(R.id.complete_button);
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RollActivity.this, DecideActivity.class);
                startActivity(i);
            }
        });
        mIncompleteButton = (Button)findViewById(R.id.incomplete_button);
        mIncompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RollActivity.this, DecideActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        /*switch (item.getItemId()) {
            case R.id.action_home:
                Intent i = new Intent(AboutActivity.this, DecideActivity.class);
                startActivity(i);
                //openSearch();
                return true;
            default:*/
                return super.onOptionsItemSelected(item);

    }
}