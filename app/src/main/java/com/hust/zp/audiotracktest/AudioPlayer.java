package com.hust.zp.audiotracktest;

import android.util.Log;

/**
 * Created by zhanpeng on 2017/4/14.
 */

public class AudioPlayer {

    private int freq;
    private int playTime;
    private AudioPlayerThread audioPlayerThread;
    private boolean isThreadRunning = false;
    //private final Handler handler;

    //private static final AudioPlayer INSTANCE = new AudioPlayer();

    public AudioPlayer(int freq, int playTime){
        this.freq = freq;
        this.playTime = playTime;
    }
    //private AudioPlayer(){
        //handler = new Handler();
    //}
    /*
    public static AudioPlayer getInstance(){
        return INSTANCE;
    }
    */

    public void generate() {
        if (!isThreadRunning) {
            stop();
            audioPlayerThread = new AudioPlayerThread(freq, playTime);
            audioPlayerThread.start();
            isThreadRunning = true;
            Log.d("audioPlayerThread", "state:"+(audioPlayerThread ==null));
            /*
            handler.postDelayed(new Runnable() {
                @Override public void run() {
                    stop();
                }
            }, playTime* 1000);
            */
        }
    }

    public void stop() {
        if (audioPlayerThread != null) {
            audioPlayerThread.stopPlay();
            audioPlayerThread.interrupt();
            audioPlayerThread = null;
            isThreadRunning = false;
        }
    }
}
