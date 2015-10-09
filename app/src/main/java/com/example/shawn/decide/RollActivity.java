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
    public final static String ROLL_TEXT = "ROLL_TEXT";
    public final static String ROLL_ID = "ROLL_ID";
    private Button mCompleteButton;
    private Button mIncompleteButton;
    private Button mShareButton;
    private TextView mChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);

        Intent intent = getIntent();
        final String choice = intent.getStringExtra(NewListActivity.ROLL);
        final String id = intent.getStringExtra(NewListActivity.TITLE);

        mChoice = (TextView)findViewById(R.id.roll_choice);
        mChoice.setText(choice);

        mCompleteButton = (Button)findViewById(R.id.complete_button);
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RollActivity.this, HistoryActivity.class);
                intent.putExtra(ROLL_TEXT, choice);
                intent.putExtra(ROLL_ID, id);
                startActivity(intent);
            }
        });
        mIncompleteButton = (Button)findViewById(R.id.incomplete_button);
        mIncompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mShareButton = (Button)findViewById(R.id.share_button);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "I just used The Chooser!");
                intent.putExtra(Intent.EXTRA_TEXT, "The Chooser selected an option for me from my list called " + id + ". My result was " + choice + "!");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // create a list of apps to choose from
                Intent chooser = Intent.createChooser(intent, "Share your result!");
                // Verify the original intent will resolve to at least one
                // activity
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { return true; }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { return super.onOptionsItemSelected(item); }
}