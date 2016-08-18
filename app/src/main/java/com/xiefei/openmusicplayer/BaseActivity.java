package com.xiefei.openmusicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import com.xiefei.openmusicplayer.service.MusicService;
import com.xiefei.openmusicplayer.utils.Common;


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

    }
    protected void musicPause(){

    }
    protected void musicPlay(){

    }
    protected void musicStop(){

    }
}
