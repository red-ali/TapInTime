package com.janiakp.tapintime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPlaying();

    }

    private void startPlaying() {
        Button mBtnStart = findViewById(R.id.btnStart);
        final Intent playActivity = new Intent(this, PlayActivity.class);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(playActivity);
            }
        });
    }
}
