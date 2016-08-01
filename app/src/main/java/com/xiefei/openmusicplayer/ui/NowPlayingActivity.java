package com.xiefei.openmusicplayer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xiefei.openmusicplayer.R;

/**
 * Created by xiefei on 16/7/31.
 */
public class NowPlayingActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing_layout);
    }
}
