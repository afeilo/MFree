package com.xiefei.openmusicplayer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xiefei.openmusicplayer.service.MusicService;
import com.xiefei.openmusicplayer.utils.Common;
import com.xiefei.openmusicplayer.utils.OpenMusicPlayerUtils;


/**
 * Created by xiefei on 16/7/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String Tag = "BaseActivity";
    protected MusicServiceUtils.ServiceToken serviceToken;
    protected ProgressBar progressBar;
    protected TextView elapsedtime;
    public Runnable mUpdateCircularProgress = new Runnable() {
        @Override
        public void run() {
            if (progressBar != null) {
                long position = MusicServiceUtils.position();
                Log.d(Tag,"currentProgress->"+position);
                progressBar.setProgress((int) position);
                if (elapsedtime != null)
                    elapsedtime.setText(OpenMusicPlayerUtils.makeShortTimeString(BaseActivity.this, position / 1000));
            }
            if (MusicServiceUtils.isPlaying()&&progressBar!=null) {
                progressBar.postDelayed(mUpdateCircularProgress, 100);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceToken = MusicServiceUtils.bindService(this,sc);
    }
    ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceConnected();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceDisconnected();
        }
    };
    protected void serviceConnected(){}
    protected void serviceDisconnected(){}
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar = null;
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

    /**
     * Apply KitKat specific translucency.
     */
//    private void applyKitKatTranslucency() {
//
//        //KitKat translucent navigation/status bar.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            //Set the window background.
//            getWindow().setBackgroundDrawable(UIElementsHelper.getGeneralActionBarBackground(mContext));
//
//            int topPadding = Common.getStatusBarHeight(mContext);
//            if (mDrawerLayout!=null) {
//                mDrawerLayout.setPadding(0, topPadding, 0, 0);
//                mNavDrawerLayout.setPadding(0, topPadding, 0, 0);
//                mCurrentQueueDrawerLayout.setPadding(0, topPadding, 0, 0);
//            }
//
//            //Calculate ActionBar and navigation bar height.
//            TypedValue tv = new TypedValue();
//            int actionBarHeight = 0;
//            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
//                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
//            }
//
//            if (mDrawerParentLayout!=null) {
//                mDrawerParentLayout.setPadding(0, actionBarHeight, 0, 0);
//                mDrawerParentLayout.setClipToPadding(false);
//            }
//
//        }
//    }
    protected void freshPlay(){
        progressBar.setMax((int) MusicServiceUtils.duration());
        progressBar.post(mUpdateCircularProgress);
    }
    protected void musicPause(){

    }
    protected void musicPlay(){
        progressBar.post(mUpdateCircularProgress);
    }
    protected void musicStop(){

    }


}
