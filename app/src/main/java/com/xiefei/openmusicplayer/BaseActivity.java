package com.xiefei.openmusicplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


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
    protected void onDestroy() {
        super.onDestroy();
        MusicServiceUtils.unBindService(serviceToken);
    }
}
