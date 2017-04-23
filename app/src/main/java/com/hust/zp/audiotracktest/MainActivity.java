package com.hust.zp.audiotracktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay;
    private Button btnStop;
    private int freq = 10000;
    private int playTime = 5;
    private boolean isPlaying = false;
    private AudioPlayer audioPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = (Button) findViewById(R.id.play);
        btnStop = (Button) findViewById(R.id.stop);

        audioPlayer = new AudioPlayer(freq,playTime);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                audioPlayer.generate();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioPlayer.stop();
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        audioPlayer.stop();
                    }
                }).start();*/
            }
        });
    }
}
