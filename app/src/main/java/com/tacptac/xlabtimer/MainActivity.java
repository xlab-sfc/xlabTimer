package com.tacptac.xlabtimer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView timer;
    private int time;
    CountDown cd;
    Button plus10s,plus1m,minus10s,minus1m,start,reset;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timer = (TextView)findViewById(R.id.timer);
        plus10s = (Button)findViewById(R.id.plus10s);
        plus10s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time += 10*1000;
                updateTimerText();
            }
        });
        plus1m = (Button)findViewById(R.id.plus1m);
        plus1m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time += 60*1000;
                updateTimerText();

            }
        });
        minus10s = (Button)findViewById(R.id.minus10s);
        minus10s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time - 10*1000 < 0)time = 0;
                else time -= 10*1000;
                updateTimerText();
            }
        });
        minus1m = (Button)findViewById(R.id.minus1m);
        minus1m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time - 60*1000 < 0)time = 0;
                else time -= 60*1000;
                updateTimerText();
            }
        });
        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCount(time);
            }
        });
        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cd != null)cd.cancel();
                time = 0;
                updateTimerText();
                //playsounds
                mediaPlayer.reset();
                mediaPlayer.stop();
            }
        });
    }

    private void updateTimerText()
    {
        long mm = time / 1000 / 60;
        long ss = time / 1000 % 60;
        timer.setText(String.format("%1$02d:%2$02d", mm, ss));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void finishCount()
    {
        try{
            AssetFileDescriptor afdescripter = getAssets().openFd("hotaru.mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(), afdescripter.getStartOffset(),
                    afdescripter.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch(IOException e){
            e.printStackTrace();
        }



    }
    public void tick(String text)
    {
        timer.setText(text);
    }
    private void setCount(long millis)
    {
        cd = new CountDown(millis,1000,this);
        cd.start();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
