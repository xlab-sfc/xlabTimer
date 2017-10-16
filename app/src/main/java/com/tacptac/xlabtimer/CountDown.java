package com.tacptac.xlabtimer;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by tacptac on 2017/04/27.
 */

public class CountDown extends CountDownTimer
{
    TextView timer;
    MainActivity activity;
    public CountDown(long millisInFuture, long countDownInterval, MainActivity _activity) {
        super(millisInFuture, countDownInterval);
        activity = _activity;

    }

    @Override
    public void onFinish()
    {
        activity.finishCount();
    }
    @Override
    public void onTick(long millisUntilFinished)
    {
        long mm = millisUntilFinished / 1000 / 60;
        long ss = millisUntilFinished / 1000 % 60;

        activity.tick(String.format("%1$02d:%2$02d", mm, ss));
    }
}
