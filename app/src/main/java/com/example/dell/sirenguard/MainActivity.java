package com.example.dell.sirenguard;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;
import be.tarsos.dsp.pitch.PitchProcessor;
import android.widget.TextView;
import android.util.Log;
import android.os.Handler;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.SplashTheme);
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);
        dispatcher.addAudioProcessor(new PitchProcessor(PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                    AudioEvent audioEvent) {
                final float pitchInHz = pitchDetectionResult.getPitch();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //                        Log.d("pitchInHz", ""+pitchInHz);
                            if (pitchInHz >= 635 && pitchInHz <= 912 ||
                                    pitchInHz >= 770 && pitchInHz <= 960 ||
                                    pitchInHz >= 1414 && pitchInHz <= 1685) {
                                TextView text = (TextView) findViewById(R.id.textView4);
                                Log.d("Hello", "" + pitchInHz);
                                text.setText("%f" + pitchInHz);


                            }
                        }
                        catch(Exception e){

                            }
                    }
                });

            }
        }));
        new Thread(dispatcher,"Audio Dispatcher").start();
    }
}

