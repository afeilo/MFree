package com.xiefei.openmusicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xiefei.openmusicplayer.service.MusicService;


/**
 * Created by xiefei on 16/7/23.
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected MusicServiceUtils.ServiceToken serviceToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceToken = MusicServiceUtils.bindService(this,null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.PLAYSTATE_CHANGED);
        registerReceiver(musicBroadCast,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(musicBroadCast);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicServiceUtils.unBindService(serviceToken);
    }

    protected BroadcastReceiver musicBroadCast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String actionKey = intent.getStringExtra(MusicService.ACTION_KEY);
            switch (actionKey){
//                case MusicService.NEXT:
//                    freshPlay();
//                    break;
//                case MusicService.PREV:
//                    freshPlay();
//                    break;
                case MusicService.PLAY_CHANGE:
                    freshPlay();
                    break;

                case MusicService.PAUSE:
                    musicPause();
                    break;
                case MusicService.PLAY:
                    musicPlay();
                    break;
                case MusicService.STOP:
                    musicStop();
                    break;
            }
        }
    };
    protected void freshPlay(){

    }
    protected void musicPause(){

    }
    protected void musicPlay(){

    }
    protected void musicStop(){

    }
}
