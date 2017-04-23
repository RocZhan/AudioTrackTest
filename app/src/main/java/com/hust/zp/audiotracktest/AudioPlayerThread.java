package com.hust.zp.audiotracktest;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

/**
 * Created by zhanpeng on 2017/4/14.
 */

public class AudioPlayerThread extends Thread {

    private boolean isPlaying;
    private final int freq;
    private final int playTime;
    private AudioTrack audioTrack;

    public AudioPlayerThread(int freq,int playTime){
        this.freq = freq;
        this.playTime = playTime;
    }

    public void run(){
        super.run();
        audioPlay();
    }

    public void audioPlay(){
        if (!isPlaying){
            isPlaying = true;

            int rate = AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC);


            int numSamples = playTime * rate;
            double[] pcmData = new double[numSamples];
            byte[] generatedSnd = new byte[2 * numSamples];

            for (int i = 0; i < numSamples; i++) {
                pcmData[i] = Math.sin(freq * 2 * Math.PI * i / (rate));

            }

            int i;
            int idx = 0;

            int ramp = numSamples / 20;

            for (i = 0; i < ramp; i++) { // Ramp amplitude up (to avoid clicks)

                final short val = (short) (pcmData[i] * 32767 * i / ramp);
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
                
            }

            for (i = ramp; i < numSamples - ramp; i++) {   // Max amplitude for most of the samples

                final short val = (short) (pcmData[i] * 32767);
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

            }

            for (i = numSamples - ramp;i < numSamples;i++){

                final short val = (short) (pcmData[i] * 32767 * (numSamples - i) / ramp);
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

            }

            try {

                int bufferSize = AudioTrack.getMinBufferSize(rate, AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT);
                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,rate,AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT,bufferSize,AudioTrack.MODE_STREAM);
                /*
                audioTrack.setNotificationMarkerPosition(numSamples);
                audioTrack.setPlaybackPositionUpdateListener(
                        new AudioTrack.OnPlaybackPositionUpdateListener() {
                            @Override public void onPeriodicNotification(AudioTrack track) {
                                // nothing to do
                            }

                            @Override public void onMarkerReached(AudioTrack track) {
                                //toneStoppedListener.onToneStopped();
                            }
                        });
                 */
                audioTrack.play();
                audioTrack.write(generatedSnd,0,generatedSnd.length);
            }catch(Exception e){
                e.printStackTrace();
            }
            Log.d("audioTrack", "state: "+audioTrack.getState());
            //stopPlay();
        }

    }
    public void stopPlay() {
        if (audioTrack != null && audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
            audioTrack.pause();
            //Log.d("audioTrack", "stopPlay: "+"succcess!!!");
            //audioTrack.release();
            audioTrack.flush();
            audioTrack.release();
            Log.d("audioTrack", "stopPlay: "+"succcess!!!");
            isPlaying = false;
        }
    }
}
