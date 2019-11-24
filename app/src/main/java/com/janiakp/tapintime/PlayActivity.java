package com.janiakp.tapintime;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {

    // global variables
    double count = 0;
    double try1 = 0;
    double try2 = 0;
    double try3 = 0;
    boolean endFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // methods
        startPlaying();
        backBtnListener();
    }

    // displaying table with results and avarage
    private void backBtnListener() {
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Table: \n"+try1+"\n"+try2+"\n"+try3, Toast.LENGTH_LONG).show();
            }
        });
        btnBack.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public boolean onLongClick(View v) {
                double avarage = (try1 + try2 + try3)/3.f;
                //noinspection MalformedFormatString
                Toast.makeText(getApplicationContext(), String.format("Average: %.3f", avarage +"s"), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    // main method
    private void startPlaying() {

        // setting startup setup for main button
        final Button btnReact = findViewById(R.id.btnReaction);
        btnReact.setBackgroundColor(Color.LTGRAY);
        btnReact.setClickable(false);

        // start counter button listener
        final Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                double r = counter();
                btnReact.setBackgroundColor(Color.RED);
                btnReact.setText("Wait for green..");
                btnStart.setClickable(false);

                // timer
                new CountDownTimer((long) r, 10) {

                    public void onTick(long millisUntilFinished) {}

                    // stopping timer and saving the result
                    public void onFinish() {
                        btnReact.setClickable(true);
                        btnReact.setBackgroundColor(Color.GREEN);
                        btnReact.setText("STOP!");
                        count = 0;
                        final Timer T = new Timer();
                        T.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                count++;
                            }
                        }, 1, 1);

                        btnReact.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                T.cancel();
                                Toast.makeText(getApplicationContext(), count / 1000 + "s", Toast.LENGTH_LONG).show();

                                // saving the results
                                if (try1!=0){
                                    if (try2!=0){
                                        try3 = count / 1000;
                                        //stopping playing
                                        endFlag = true;
                                        btnStart.setClickable(false);
                                    }
                                    else {
                                        try2 = count / 1000;
                                        btnStart.setClickable(true);
                                    }
                                } else {
                                    try1 = count / 1000;
                                    btnStart.setClickable(true);
                                }

                                //clearing counter
                                count = 0;
                                btnReact.setBackgroundColor(Color.LTGRAY);
                                btnReact.setClickable(false);

                            }
                        });
                    }
                }.start();
            }
        });
    }

    // generate random value for countdown timer
    private double counter() {

        Random r = new Random();
        return (r.nextDouble()+1)*(r.nextInt(4)+1)*1000;

    }
}
